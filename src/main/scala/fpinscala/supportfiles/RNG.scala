package fpinscala.supportfiles

import scala.annotation.tailrec
import State._

trait RNG {
  def nextInt: Output[RNG, Int]
}

object RNG {

  case class SimpleRNG(seed: Long) extends RNG {
    override def nextInt: Output[RNG, Int] = {
      val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
      val nextRNG = SimpleRNG(newSeed)
      val n = (newSeed >>> 16).toInt
      Output(n, nextRNG)
    }
  }

  type Rand[A] = RNG => Output[RNG, A]

  val int: Rand[Int] = _.nextInt

  def unit[A](a: A): Rand[A] = rng => Output(a, rng)

  def map[A, B](r: Rand[A])(op: A => B): Rand[B] =
    rng => {
      val next = r(rng)
      Output(op(next.value), next.state)
    }

  def _map[A, B](r: Rand[A])(op: A => B): Rand[B] = flatMap(r)(a => unit(op(a)))

  def map2[A, B, C](ra: Rand[A], rb: Rand[B])(op: (A, B) => C): Rand[C] =
    rng => {
      val nextA = ra(rng)
      val nextB = rb(nextA.state)
      Output(op(nextA.value, nextB.value), nextB.state)
    }

  def _map2[A, B, C](ra: Rand[A], rb: Rand[B])(op: (A, B) => C): Rand[C] = flatMap(ra)(a => map(rb)(b => op(a, b)))

  def pair[A, B](ra: Rand[A], rb: Rand[B]): Rand[(A, B)] = map2(ra, rb)((_, _))

  def sequence[A](l: List[Rand[A]]): Rand[List[A]] =
    l.reverse.foldLeft(unit(Nil: List[A]))((acc, a) => map2(a, acc)(_ :: _))

  def flatMap[A, B](r: Rand[A])(op: A => Rand[B]): Rand[B] = rng => {
    val next = r(rng)
    op(next.value)(next.state)
  }

  def nonNegativeInt(rng: RNG): Output[RNG, Int] =
    rng.nextInt match {
      case r: Output[RNG, Int] if r.value < 0 => Output(-(r.value + 1), r.state)
      case r: Output[RNG, Int] => r
    }

  def nonNegativeEven: Rand[Int] = map(nonNegativeInt)(i => i - i % 2)

  def nonNegativeLessThan(n: Int): Rand[Int] =
    flatMap(nonNegativeInt) { i =>
      val mod = i % n
      if (i + (n - 1) - mod >= 0) unit(mod) else nonNegativeLessThan(n)
    }

  def double: Rand[Double] = map(nonNegativeInt)(_/(Int.MaxValue.toDouble + 1))

  def intDouble: Rand[(Int, Double)] = pair(int, double)

  def doubleInt: Rand[(Double, Int)] = pair(double, int)

  def double3(rng: RNG): Output[RNG, (Double, Double, Double)] = {
    val doubleVal1 = double(rng)
    val doubleVal2 = double(doubleVal1.state)
    val doubleVal3 = double(doubleVal2.state)
    Output((doubleVal1.value, doubleVal2.value, doubleVal3.value), doubleVal3.state)
  }

  def ints(count: Int)(rng: RNG): Output[RNG, List[Int]] = {
    @tailrec
    def loop(n: Int, rng0: RNG, acc: List[Int]): Output[RNG, List[Int]] =
      if (n <= 0) Output(acc, rng0) else {
        val next = int(rng0)
        loop(n - 1, next.state, acc :+ next.value)
      }
    loop(count, rng, Nil)
  }

  def _ints(count: Int): Rand[List[Int]] = sequence(List.fill(count)(int))
}

case class Output[S, +A](value: A, state: S)

case class State[S, +A](run: S => Output[S, A]) {
  def map[B](op: A => B): State[S, B] = flatMap(a => unit(op(a)))

  def map2[B, C](sb: State[S, B])(op: (A, B) => C): State[S, C] = flatMap(a => sb.map(b => op(a, b)))

  def flatMap[B](op: A => State[S, B]): State[S, B] = State {(s: S) =>
    val next = run(s)
    op(next.value).run(next.state)
  }
}

object State {
  type Rand[A] = State[RNG, A]

  def unit[S, A](a: A): State[S, A] = State(s => Output(a, s))

  def sequence[S, A](l: List[State[S, A]]): State[S, List[A]] =
    l.reverse.foldLeft(unit[S, List[A]](Nil: List[A]))((acc, a) => a.map2(acc)(_ :: _))

  def get[S]: State[S, S] = State(s => Output(s, s))

  def set[S](s: S): State[S, Unit] = State(_ => Output((), s))

  def modify[S](op: S => S): State[S, Unit] = for {
    s <- get
    _ <- set(op(s))
  } yield ()

  def simulateMachine(inputs: List[Input]): State[Machine, (Int, Int)] =
    inputs.foldLeft(get[Machine]) {
      (acc, input) =>
        acc.flatMap {
          case Machine(isLocked, candies, coins) =>
              input match {
                case Coin if isLocked && candies > 0 => acc.map(_ => Machine(locked = false, candies, coins + 1))
                case Turn if !isLocked && candies > 0 => acc.map(_ => Machine(locked = true, candies - 1, coins))
                case _ => acc
              }
        }
    }.map {
      case Machine(_, candies, coins) => (coins, candies)
    }
}

sealed trait Input
case object Coin extends Input
case object Turn extends Input
case class Machine(locked: Boolean, candies: Int, coins: Int)
