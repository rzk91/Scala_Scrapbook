package scrapbook

import scrapbook.TestDataAggregate.MathOperation.MathOperation
import scrapbook.TestDataAggregate.DataAggregate.modifySequence

object TestDataAggregate {

  def main(args: Array[String]): Unit = {

    val test = 1

    if (test == 1) {
      val x = List(1, 1, 1, 2, 3, 4, 4, 4, 0, 1, 1, 1, 2, 2, 3)
      val t = List.range(1, x.length + 1).map(_ * 2)
      val pts = (t, x).zipped.map(TYPair(_, _))

      println(s"Input: $pts")

      var output = DataAggregate(0, 20, t.head, x.head, MathOperation.INCREMENT_COUNT)

      pts.tail.foreach {
        case TYPair(t, v) =>
          output = output.addToAccumulatorSequence(t, v)
          println(s"After ($t, $v): $output")
      }

      println(s"Aggregate: ${output.aggregate}")

      // Add late event at t = 0 with value 0; should create a new entry
      output = output.addToAccumulatorSequence(0, 0)
      println(s"After (0, 0): $output")
      // Add another late event at t = 1 with value = 0; should just update latest
      output = output.addToAccumulatorSequence(1, 0)
      println(s"After (1, 0): $output")
      // Add another late event at t = 17 with value 5; should add a new entry
      output = output.addToAccumulatorSequence(17, 5)
      println(s"After (17, 5): $output")
      // Add another late event at t = 25 with value 2.5; should add a new entry
      output = output.addToAccumulatorSequence(29, 2.5)
      println(s"After (29, 2.5): $output")
      // Add another late event at t = 25 with value 1; should remain as is
      output = output.addToAccumulatorSequence(25, 1)
      println(s"After (25, 1): $output")
      // Add new event at t = 32 with value 0; should create new entry at the end
      output = output.addToAccumulatorSequence(32, 0)
      println(s"After (32, 0): $output")
      // Add new event at t = 31 with value 4; should create new entry
      output = output.addToAccumulatorSequence(31, 4)
      println(s"After (31, 4): $output")

      println(s"Aggregate: ${output.aggregate}")
    } else {
      val x = List(1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3)
      val t = List.range(1, x.length + 1).map(_ * 2)
      val pts = (t, x).zipped.map(TYPair(_, _))

      println(s"Input: $pts")

      var output = DataAggregate(0, 20, t.head, x.head, MathOperation.INCREMENT_COUNT)

      pts.tail.foreach {
        case TYPair(t, v) =>
          output = output.addToAccumulatorSequence(t, v)
          println(s"After ($t, $v): $output")
      }

      println(s"Aggregate: ${output.aggregate}")

      // Add late event at t = 0 with value 0; should create a new entry
      output = output.addToAccumulatorSequence(0, 0)
      println(s"After (0, 0): $output")
      // Add another late event at t = 1 with value = 0; should just update latest
      output = output.addToAccumulatorSequence(1, 0)
      println(s"After (1, 0): $output")
      // Add another late event at t = 32 with value 5; should add a new entry
      output = output.addToAccumulatorSequence(32, 5)
      println(s"After (32, 5): $output")
      // Add another late event at t = 15 with value 1; should add a new entry
      output = output.addToAccumulatorSequence(15, 1)
      println(s"After (15, 1): $output")

      println(s"Aggregate: ${output.aggregate}")
    }
  }

  case class DataAggregate(
    start: Long,
    end: Long,
    latest: IndexedSeq[Long], // Latest timestamp for each TYPair in acc
    acc: IndexedSeq[TYPair],  // First timestamp and (accumulated) value
    op: MathOperation,
    n: Int
  ) {

    def addToAccumulatorSequence(t: Long, y: Double): DataAggregate =
      if (t >= latest.last) {
        if (y == acc.last.v) {
          copy(latest = latest.init :+ t)
        } else {
          copy(latest = latest :+ t, acc = acc :+ TYPair(t, y))
        }
      } else {
        // Late event
        val indexMinLatest = latest.indexWhere(_ >= t)
        if (indexMinLatest <= 0 && y != acc.head.v) {
          // Very late event; insert new entry at the beginning of `acc` and `latest`
          copy(latest = t +: latest, acc = TYPair(t, y) +: acc)
        } else if (t >= acc(indexMinLatest).t) {
          // Value already present; keep current version
          this
        } else {
          if (acc(indexMinLatest - 1).v == y) {
            // Value already exists in previous index; update latest timestamp
            copy(latest = modifySequence(latest, t, indexMinLatest - 1, insert = false))
          } else {
            // New entry between existing entries
            copy(
              latest = modifySequence(latest, t, indexMinLatest, insert = true),
              acc = modifySequence(acc, TYPair(t, y), indexMinLatest, insert = true)
            )
          }
        }
      }

    def aggregate: Double = op match {
      case MathOperation.SUM     => acc.head.v
      case MathOperation.AVERAGE => acc.head.v / n
      case MathOperation.INCREMENT_COUNT =>
        acc.map(_.v).sliding(2).collect { case IndexedSeq(a, b) if b > a => b - a }.sum
    }
  }

  object DataAggregate {

    def apply(start: Long, end: Long, latest: Long, acc: Double, op: MathOperation, n: Int = 1): DataAggregate =
      new DataAggregate(start, end, IndexedSeq(latest), IndexedSeq(TYPair(latest, acc)), op, n)

    def modifySequence[A](seq: IndexedSeq[A], v: A, at: Int, insert: Boolean): IndexedSeq[A] = {
      val len = seq.length
      (seq.take(at) :+ v) ++ seq.takeRight(len - at - (if (insert) 0 else 1))
    }
  }

  object MathOperation extends Enumeration {
    type MathOperation = Value
    val SUM: Value = Value("operation.sum")
    val AVERAGE: Value = Value("operation.average")
    val INCREMENT_COUNT: Value = Value("operation.increment-count")
  }

  final case class TYPair(t: Long, v: Double)

}
