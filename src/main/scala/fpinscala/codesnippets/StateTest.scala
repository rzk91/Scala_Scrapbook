package fpinscala.codesnippets

import fpinscala.supportfiles.{Coin, Machine, Turn}
import fpinscala.supportfiles.RNG.SimpleRNG
import fpinscala.supportfiles.State._

object StateTest {
  def main(args: Array[String]): Unit = {

    val n = SimpleRNG(Int.MaxValue).nextInt
    val m = Machine(locked = true, 5, 10)
    val inputs1 = List(Coin, Turn, Coin, Coin, Turn, Turn, Coin, Turn)
    val inputs2 = List.empty
    val inputs3 = List(Coin, Turn, Coin, Turn, Coin, Turn, Coin, Turn, Coin, Turn, Coin, Turn)

    println(simulateMachine(inputs1).run(m))
    println(simulateMachine(inputs2).run(m))
    println(simulateMachine(inputs3).run(m))

  }
}
