package scrapbook

import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.time.{Instant, LocalDateTime, ZoneId, ZoneOffset, ZonedDateTime}
import fpinscala._

import java.time.format.DateTimeFormatter
import scala.annotation.tailrec
import scala.collection.mutable
import scala.io.StdIn.readInt
import scala.reflect.ClassTag
import scala.util.{Success, Try}
import scala.collection.JavaConverters._
import scala.util.control.NonFatal
import scala.util.matching.Regex

object TestCode {

  //  val x = "BB"
  //  val y = 2334

  def main(args: Array[String]): Unit = {

    //    case class UnderscoreString(str: String)
    //
    //    object UnderscoreString {
    //      def apply(str: String): UnderscoreString =
    //        new UnderscoreString(str = "ABC")
    //    }

    //    val x = new GregorianCalendar
    //    val ts = 1577228400000L
    //    x.setTimeInMillis(ts)
    //
    //    println(x.getTime)
    //    x.add(Calendar.DAY_OF_YEAR, 10)
    //
    //    println(x.getTime)
    //
    //    x.setTimeInMillis(ts)
    //
    //    println(x.getTime)
    //    x.add(Calendar.DAY_OF_MONTH, 10)
    //
    //    println(x.getTime)
    //
    //    x.set(Calendar.MONTH, 1)
    //
    //    println(x.getTime)
    //
    //    x.set(Calendar.DAY_OF_MONTH, x.getActualMaximum(Calendar.DAY_OF_MONTH) + 1)
    //
    //    println(x.getTime)
    //
    //    x.add(Calendar.MILLISECOND, 9*60*60*1000)
    //
    //    println(x.getTime)

    //    val x, y = Array.ofDim[Int](5)
    //    x.indices.foreach(i => {
    //      x(i) = 2 * i
    //      y(i) = x(i) + 2
    //    })
    //
    //    println(x.toList)
    //    println(y.toList)

    //    case class Something(str1: String, str2: String, str3: String)
    //
    //    val a = IndexedSeq((1, "a"), (2, "b"), (3, "c"))
    //    val b = IndexedSeq((1, "x"), (2, "y"), (3, "z"))
    //    val c = IndexedSeq((1, "d"), (2, "e"), (3, "f"))
    //
    //    val merge = ListMap((a ++ b ++ c).groupBy(_._1).toSeq.sortBy(_._1):_*)
    //
    //    val clean = merge.mapValues(_.map(_._2))
    //
    //    val finalMap = clean.map(k => k._1 -> Something(k._2.head, k._2(1), k._2.last))
    //
    //    println(finalMap)

    //    val x = Array.range(5, 10 + 5 + 1)
    //    println(x.mkString(","))
    //

    //    val timeZoneStr = "America/New_York"
    //
    //    val sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    //    sdf.setTimeZone(TimeZone.getTimeZone("UTC"))
    //
    //    val timestamp = 1562954315000L
    //    println("Before: " + timestamp)
    //    val x = new GregorianCalendar
    //    println(s"Current time: ${x.getTime} (${x.getTimeZone.getID})")
    //
    //    x.set(Calendar.DAY_OF_MONTH, x.getActualMaximum(Calendar.DAY_OF_MONTH) + 1)
    //
    //    println(s"New time: ${x.getTime}")

    //    val x = "120d, 06:47:42"
    //    val p = "ddd HH:mm:ss"
    //    val sdf = new SimpleDateFormat(p)
    //    sdf.setTimeZone(TimeZone.getTimeZone("UTC"))
    //    val s = x.split("d,").map(_.trim).toList
    //
    //    println(s)
    //    val t = 120 * 86400 + 6 * 3600 + 47 * 60 + 42
    //    println(t)
    //    println(sdf.parse(s.mkString(" ")).getTime)
    //    println({
    //      val pattern = """(\d\d):(\d\d):(\d\d)""".r
    //      val p = pattern.findAllIn(x)
    //      if (p.hasNext) {
    //        ((p.group(1).toInt * 60 + p.group(2).toInt) * 60 + p.group(3).toInt) * 1000L
    //      } else {
    //        0L
    //      }
    //    })

    //    val folderSensorPattern: Regex = "(Sektion\\d+\\.Falz\\d+).*Betriebsstunden".r
    //    val tagName = "Daten.Sektion01.Falz01.Antrieb.HA1.Zaehler.Betriebsstunden.Absolut"
    //
    //    folderSensorPattern.findFirstMatchIn(tagName) match {
    //      case Some(loc) => println(loc)
    //    }

    //    val dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").withZone(ZoneId.of("UTC"))
    //
    //    println(Instant.from(dtf.parse("21-11-2019 12:30:45")))
    //
    //    val y = new GregorianCalendar
    //    y.setTimeZone(TimeZone.getTimeZone(timeZoneStr))
    //    y.setTimeInMillis(timestamp)
    //    y.set(Calendar.HOUR, 0)
    //    y.set(Calendar.MINUTE, 0)
    //    y.set(Calendar.SECOND, 0)
    //    y.set(Calendar.MILLISECOND, 0)
    //    y.set(Calendar.AM_PM, 0)
    //
    //    println("After (by hand): " + y.getTimeInMillis)
    //    println(sdf.format(y.getTimeInMillis))
    //
    //    x.roll(Calendar.MONTH, 1)
    //    x.set(Calendar.DAY_OF_MONTH, 2)
    //    y.roll(Calendar.MONTH, 1)
    //    y.set(Calendar.DAY_OF_MONTH, 2)
    //
    //    println("Day change (startOfDay): " + x.getTimeInMillis)
    //    println(sdf.format(x.getTimeInMillis))
    //    println("Day change (by hand): " + y.getTimeInMillis)
    //    println(sdf.format(y.getTimeInMillis))

    //    var xDate = new SimpleDateFormat("dd-MM-yyyy").format(x.getTime)
    //    println(xDate)
    //
    //    x.roll(Calendar.MONTH, true)
    //    xDate = new SimpleDateFormat("dd-MM-yyyy").format(x.getTime)
    //    println(xDate)

    //    val x: Long = 1540159066000L
    ////    val y = Instant.ofEpochSecond(x/1000)
    ////
    ////    val zz = ZoneId.of("UTC")
    ////    val z = TimeZone.getTimeZone(zz)
    ////
    ////    val sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm")
    ////    sdf.setTimeZone(z)
    ////
    ////    println(y)
    ////    println(sdf.format(x))

    //    val x = " 123 "
    //
    //    println(x, x.length)
    //    println(x.trim, x.trim.length)
    //
    //    println(x.trim.toInt)

    //    val s1 = ListBuffer[Int](10, 11)
    ////    val s1 = ListBuffer[Int](2, 3, 4, 5)
    //    val t = 0
    //    val n = 2
    //    var k = ListBuffer.range(0, 12)
    //
    //    println(s1)
    ////    if(t > n) println(s2) else println(s1)
    ////    println(k)
    //    if (t > n) {
    //      k = k.filterNot(List.range(t - n, t).contains(_))
    ////      k.foreach(e => if (s2.contains(e)) s2 -= e)
    //    } else {
    //      k = k.filterNot(List.range(0, t).contains(_))
    //      k = k.filterNot(List.range(11, 11 - n + t, -1).contains(_))
    ////      k.foreach(e => if (s1.contains(e)) s1 -= e)
    //    }
    ////    println(k)
    ////    if (t > n) {
    ////      s1 -= (t - n)
    ////      s1 += t
    ////    } else if (t != 0) {
    //      s1 -= (t + n + (6 - n) * 2) % 12
    //      s1 += t
    ////    } else {
    ////      s1 -= (12 - n)
    ////      s1 += t
    ////    }
    //
    //    println(s1)
    //    if(t > n) println(s2) else println(s1)
    //
    //    val s = "2019-Mär-01 00:00:04.339"
    //    val r = "2019-Mar-01 00:00:04.339"
    //    val sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss.SSS", Locale.GERMANY)

    //    println(sdf.parse(s).getTime)
    //    checkType(x)
    //    checkType(y)
    //    compare()
    //  }
    //
    //  def checkType(cls: Any) {
    //    cls match {
    //      case i: String => println(s"String: $i")
    //      case i: Int => println(s"Integer: $i")
    //      case _ => println(s"Something else: $cls")
    //    }
    //  }
    //
    //  def compare(): Unit = {
    //    if (x != y) {
    //      println(s"Different types or values!")
    //    } else {
    //      println(s"They are the same")
    //    }

    //    val sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    ////    sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"))
    ////    println(sdf.parse("2019-06-23T12:45:00.000-0400").getTime)
    ////
    ////    val list1 = List("a", "b", "c", "d")
    ////
    ////    println(list1.mkString("\n"))

    //    val sMap = Set(("feeder_pile_chng", Set("1010", "1020")), ("delivery_pile_chng", Set("1111", "3333")))
    //      .flatMap(e => e._2.map(m => (m, e._1)))
    //      .toMap
    //
    //    println(sMap)
    //
    //    val map = Set(("feeder_pile_chng", Set("1010", "1020")), ("delivery_pile_chng", Set("1111", "3333")))
    //        .map(_.swap).flatMap(e => e._1.map((_, e._2))).toMap
    //
    //    println(map)

    //    val timestamp = 1562954315000L
    //
    //    println("Current time")
    //    println(ZonedDateTime.now)
    //    println(ZonedDateTime.now.toLocalDate)
    //    println(ZonedDateTime.now.toLocalDate.atStartOfDay.toInstant(ZoneOffset.UTC).toEpochMilli)
    //
    //    println("Random time in the past...")
    //    val (instant, zone) = (Instant.ofEpochMilli(timestamp), ZoneId.of("America/New_York"))
    //    println(ZonedDateTime.ofInstant(instant, zone))
    //    println(ZonedDateTime.ofInstant(instant, zone).toLocalDate)
    //    println(ZonedDateTime.ofInstant(instant, zone).toLocalDate.atStartOfDay(ZoneId.of("UTC")).toEpochSecond * 1000)
    //
    //    println("Using Instant...")
    //    println(Instant.ofEpochMilli(timestamp).truncatedTo(ChronoUnit.DAYS).toEpochMilli)

    //    val s: Map[String, mutable.LinkedHashMap[Int, Int]] = Map(
    //      "a" -> mutable.LinkedHashMap(1 -> 1, 2 -> 2, 3 -> 3),
    //      "b" -> mutable.LinkedHashMap(1 -> 2, 2 -> 4, 3 -> 6),
    //      "c" -> mutable.LinkedHashMap(1 -> 3, 2 -> 6, 3 -> 9),
    //      "d" -> mutable.LinkedHashMap(1 -> 4, 2 -> 8, 3 -> 12)
    //    )
    //
    //    val s2: Map[String, Map[Int, Int]] = Map(
    //      "a" -> Map(1 -> 1, 2 -> 2, 3 -> 3),
    //      "b" -> Map(1 -> 2, 2 -> 4, 3 -> 6),
    //      "c" -> Map(1 -> 3, 2 -> 6, 3 -> 9),
    //      "d" -> Map(1 -> 4, 2 -> 8, 3 -> 12)
    //    )
    //
    //    println(s2)

    //    val s = Random.shuffle((1 to 10).toList)
    //
    //    println(s)
    //
    //    val z = List(2, 4, 6)
    //
    //    val s1 = s.zipWithIndex.collect{case(v, i) if z.contains(i) => v}
    //    println(s1)
    //
    //    val z1 = z.sortWith((l, h) => s(l) > s(h))
    //    println(z1)

    //    val b = new ListBuffer[List[Int]]
    //
    //    var q = s
    //    var i = 0
    //
    //    z.foreach(p => {
    //      val t = q.splitAt(p - i)
    //      b += t._1
    //      q = t._2
    //      i = p
    //    })
    //
    //    b += s.splitAt(z.last)._2
    //
    //    println(b)
    //
    //    var b_ = split(s, z.toArray)
    //
    //    println(b_)
    //
    //    b_ = b_.dropRight(1)
    //
    //    println(b_)
    //
    //    val a = Array[Int](1, 2, 3, 4)
    //    (Array(0) ++ a).zipWithIndex.foreach(println(_))

    //    val x = Array[Int](0, 4, 5, 2, 7, 3, 8, 9, 1, 6)
    //    println(x.toList)
    //
    //    val y = (Array(0) ++ x).distinct
    //    println(y.toList)

    //    println(x.sortBy(-_).toList)
    //    val r = Array.ofDim[Boolean](x.length)
    //
    //    r(2) = true
    //    r(5) = true
    //    r(7) = true
    //
    //    println(r.toList)
    //    val p = r.indices.filter(r(_))
    //    println(p)
    //    println(p.map(x(_)).toList)

    //    val someList = List.empty
    //
    //    val sortedList = someList.sortWith((s, t) => {
    //      val s1 = convertToTimestamp(s.split("_").head)
    //      val t1 = convertToTimestamp(t.split("_").head)
    //      s1 < t1
    //    })
    //
    //    println(someList)
    //    var t0 = System.nanoTime()
    //    println(someList.toSet.toList)
    //    println(s"Time spent: ${System.nanoTime() - t0}")
    //    t0 = System.nanoTime()
    //    println(someList.distinct)
    //    println(s"Time spent: ${System.nanoTime() - t0}")
    //    println(sortedList.init)

    //    val pattern = Pattern.compile("^homag-.*")
    //
    //    println("homag-0202290564-3932".matches("^homag-.*"))

    //    val ts = List(1572127200000L, 1572166800000L)
    //    val zone = ZoneId.of("UTC")
    //
    //    println(zonedStartOfDay(ts.last, zone))
    //    println(Instant.ofEpochMilli(ts.last).atZone(zone).truncatedTo(ChronoUnit.DAYS))

    //    var t0 = System.nanoTime()
    //    val m: Option[Map[Int, Int]] = None
    //    println(s"Time required for None map: ${System.nanoTime() - t0}")
    //
    //    t0 = System.nanoTime()
    //    val m_ = Map.empty[Int, Int]
    //    println(s"Time required for empty map: ${System.nanoTime() - t0}")

    //    val s = "Sektion01.De01"
    //    val Pattern = "(.*De.*|.*Turm.*)".r
    //
    //    s match {
    //      case Pattern(e) => println(e)
    //      case _ => println("no match")
    //    }

    //    val c = CaseClass(2, 4, 7, 9)
    //    if (c.productIterator.exists(_.asInstanceOf[Double] % 2 == 0)) {
    //      println(true)
    //    }

    //    val s1 = "1572127200000"
    //    val s2 = "2019-03-22T18:00:12+01:00"
    //
    //    println(convertDateToTimestamp(s1))
    //    println(convertDateToTimestamp(s2))

    //    val c = List(1, 3, 5, 7)
    //    val e = c.flatMap(v => if (v % 2 == 0) Some(v) else None)
    //    val o = c.flatMap(v => if (v % 2 == 1) Some(v) else None)
    //
    //    o.union(e).foreach(println)

    //    val t = "2019-05-11T07:16:12-04:00"
    //    println(LocalDateTime.parse(t, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")).atZone(ZoneId.of("America/New_York")).toEpochSecond * 1000)
    //    println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(t).getTime)
    //    println(LocalDateTime.parse(t, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")).atZone(ZoneId.of("UTC")).toEpochSecond * 1000)

    //    val file = "something.txt"
    //    val writer = new BufferedWriter(new FileWriter(file))
    //    c.foreach(s => writer.write(s"$s\n"))
    //    writer.close()

    //    val v = Seq(CaseClass(1, 2), CaseClass(2, 4), CaseClass(3, 6), CaseClass(4, 8))
    //
    //    println(mean(v.map(_.v1)), stddev(v.map(_.v1)), variance(v.map(_.v1)))

    //    val err = new Random
    //    val t = List.range(1, 10)
    //    val a = Array.fill[Double](t.size)(1)
    //    val x = t.map(_ * 3 + err.nextGaussian())
    //
    //    val t_ = DenseMatrix(t.map(_.toDouble).zip(a).toArray:_*)
    //    val x_ = DenseVector(x.toArray)
    //
    ////    println(t_)
    ////    println(x_)
    //    val r = leastSquares(t_, x_)
    //
    //    println(s"slope = ${r.coefficients.data.headOption}; intercept = ${r.coefficients.data.lastOption}")
    //
    //    val y = (1 to 20 by 2).map(_.toDouble)
    //    val b = Array.fill[Double](y.length)(1)
    //
    //    val y_ = r.apply(DenseMatrix(y.zip(b).toArray: _*))
    //
    //    println(y)
    //    println(y_.toScalaVector)

//    val s = Set("")
//
//    println(s.headOption.flatMap(_.headOption).map(_ => s))

//    val x = List(15, 15, 15, 16, 16, 0, 0, 1, 1, 1, 2)
//    val x = List(1, 1, 1, 2, 3, 4, 4, 4, 0, 1, 1, 1, 2, 2, 3)
//    val y = x.distinct
//
//    val z = List(1, 2, 3, 4, 0)
//
//    println(z.splitAt(0))
//
//    val vs = Seq(1, 14, 16, 18, 3)
//    val vs = List(1, 3, 5, 7, 8)

//    vs.foreach { v =>
//      if (y.contains(v)) {
//        println(s"Skipping $v")
//      } else if (monotonicallyIncreasing(y)) {
//        println(s"$v, ${y.indexWhere(_ < v)}")
//      } else {
//        val y1 :: y2 = splitWith(y.reverse)(_ > _).takeWhile(_.nonEmpty)
//        println(y1, y2)
//      }
//    }

//    println("Enter a number: ")
//    val n = readInt()
//
//    (1 to n).foreach(_ => println("Hello world"))

//    val squares = Map(1 -> 1, 2 -> 4, 3 -> 9, 4 -> 16)
//
//    println(Try(squares(5)).toOption)

//    val m: mutable.HashMap[String, Int] = mutable.HashMap("a" -> 1, "b" -> 2, "c" -> 3, "d" -> 4)
//
//    m.withFilter(_._2 % 2 == 0).foreach { e =>
//      val k = e._1
//      val v = e._2
//      println(v)
//      m.remove(k)
//    }
//
//    println(m)

//    val m: Map[Int, String] = Map(1 -> "2", 2 -> "4", 3 -> "6", 4 -> "==", 5 -> "!=")
//
//    println(RandomEnum.values)
//
////    println(RandomEnum.withName(m(4)))
//
//    println(m(4))

//    val r = scala.util.Random
//
//    val l = (1 to 10).map(r.nextInt).toList
//
//    println(timeIt(l.distinct).time)
//    println(timeIt(l.toSet).time)

    //
    //    val s: String = "2021-04-28T16:43:20+01:00"
    //
    //    val sTry = Try {
    //      if (s.matches("^\\d+$")) {
    //        s.toLong
    //      } else {
    //        (
    //          Instant
    //            .from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX").parse(s))
    //            .atZone(ZoneId.of("Europe/Berlin"))
    //            .toEpochSecond * 1000L,
    //          LocalDateTime
    //            .parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"))
    //            .atZone(ZoneId.of("Europe/Berlin"))
    //            .toEpochSecond * 1000L
    //        )
    //      }
    //    }.toOption

//    val m1 = Map(1 -> 2)
//    val m2 = Map(2 -> 4)
//    val m3 = Map(3 -> 6)
//    val m4 = Map(4 -> 8)
//
//    val m = m1 ++ m2 ++ m3 ++ m4
//
//    val int1 = m.get(3)
//    val int2 = m.get(5)
//
//    println(for {
//      i <- int1
//      j <- int2
//    } yield math.min(i, j))
//
//    println((int1 ++ int2).reduceLeftOption(math.min))

//    val s = "/d/ZySgsTDGz/threshold-monitor?" +
//      "&from=1587377598152" +
//      "&to=1604266649527" +
//      "&var-influxdb=InfluxDB" +
//      "&var-botinstance_id=1234" +
//      "&var-device_id=test" +
//      "&var-selector=Temperature" +
//      "&var-upper_thresholds=30,33,35,40" +
//      "&var-timezone=UTC" +
//      "&theme=light"
//
//    println(
//      s.split('&')
//        .tail
//        .withFilter(s => List("theme", "var-influxdb").forall(!s.contains(_)))
//        .map(_.split('=').toList)
//        .collect {
//          case head :: next :: Nil => head -> next
//        }
//        .toMap
//    )

//    val m = Map(1 -> 2, 2 -> 4, 3 -> 6, 4 -> 7)
//    val l = List(CaseClass(1, 2, 3), CaseClass(2, 3, 4), CaseClass(4, 5, 6))
//
////    println(m.updated(4, 8))
//
//    println(l.head.sort(true))

//    val s = Set(1, 2, 3, 4)
//
//    println(s(1))
//    println(s(5))

//    val x = 3.24564d
//
//    def roundTo(x: Double)(n: Int): Double = {
//      val m = math.pow(10, n)
//      math.round(x * m) / m
//    }
//
//    def truncateTo(x: Double)(n: Int): Double = {
//      val m = math.pow(10, n)
//      math.floor(x * m) / m
//    }
//
//    println(truncateTo(x)(2))
//    println(roundTo(x)(2))
//
//    println(math.ulp(x))

//    val m = new java.util.HashMap[String, Long](java.util.Map.of("1", 2L, "2", 4L, "3", 6L, "4", 8L))
//
//    val v = m.get(5)
//    println(v)
//    val d = v - 10
//    if (d > 0L) {
//      println("Late")
//    } else {
//      println("Not late")
//    }

//    val x = 10f
//    val y = 10.0
//
//    println(math.ulp(x))
//    println(math.ulp(y))

//    val x: Option[Int] = None
//    val l = List(1, 2, 3, 4, 5)
//
//    // Runtime match error! Guard is applied to entire pattern, not just latter half
//    (x, l) match {
//      case (None, _) | (_, _) if l.lengthCompare(4) < 0 => println(l)
//      case (Some(x), l)                                 => println(x, l)
//    }

//    val t = 1500L
//    val i = Instant.ofEpochMilli(t).atZone(ZoneOffset.UTC)

//    println(i)
//    println(i.plusDays(2))
//    println(i.truncatedTo(ChronoUnit.DAYS).plusDays(2).getDayOfMonth)

//    object Something {
//      val s = "something"
//    }
//
//    val s = "something"
//
//    println {
//      s match {
//        case Something.s => s"$s " * 4
//        case _           => s
//      }
//    }

    val tz = "Europe/Berlin"

    println(ZoneId.of(tz))
    println(ZoneId.of(tz).getId)

  }

  case class CaseClass(v1: Double, v2: Double, v3: Double) {

    def sort(ascending: Boolean): CaseClass =
      ((if (ascending) toList.sorted else toList.sorted.reverse): @unchecked) match {
        case v1 :: v2 :: v3 :: Nil => new CaseClass(v1, v2, v3)
      }
    def toList: List[Double] = List(v1, v2, v3)
  }

  object CaseClass {
    private val numValues: Int = 3
    def apply(v1: Double, v2: Double, v3: Double): CaseClass = CaseClass(List(v1, v2, v3))

    def apply(values: List[Double]): CaseClass = {
      println("Inside the list-based apply function")
      (values.take(numValues): @unchecked) match {
        case v1 :: v2 :: v3 :: Nil => new CaseClass(v1, v2, v3)
      }
    }
  }

  private def monotonicallyIncreasing[A](values: List[A])(implicit num: Numeric[A]): Boolean =
    (values, values.drop(1)).zipped.forall(num.lteq)

  def split(x: List[Int], idx: Array[Int]): List[List[Int]] =
    if (idx.nonEmpty) {
      val (h, t) = x.splitAt(idx.head)
      List(h) ++ split(t, findFirstDerivatives(idx))
    } else {
      List(x)
    }

  def splitWith[A](list: List[A])(op: (A, A) => Boolean): List[List[A]] = {
    @tailrec
    def loop(list: List[A], prev: A, acc: List[List[A]]): List[List[A]] =
      list match {
        case Nil                   => acc
        case h :: t if op(prev, h) => loop(t, h, (h :: acc.head) :: acc.tail)
        case h :: t                => loop(t, h, List(h) :: acc)
      }

    loop(list, list.head, List(List.empty[A]))
  }

  def toPrettyString(s: String): String =
    s.split("[-_\\s]").withFilter(_.nonEmpty).map(_.capitalize).mkString(" ")

  def convertDateToTimestamp(input: String): Option[Long] =
    try {
      if (input == null || input == "None") {
        None
      } else if (input.matches("^\\d+$")) {
        Some(input.toLong)
      } else {
        val date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(input)
        Some(date.getTime)
      }
    } catch { case _: Throwable => None }

  def convertToTimestamp(timeAsString: String): Long = {
    val sdf = new SimpleDateFormat
    val splitTime = timeAsString.split("\\s")
    if (splitTime.lengthCompare(2) == 0) {
      if (splitTime.last.split(":").lengthCompare(3) == 0) {
        // format: 31.12.2019 21:30:31
        sdf.applyPattern("dd.MM.yyyy HH:mm:ss")
      } else {
        // format: 31.12.2019 21:30
        sdf.applyPattern("dd.MM.yyyy HH:mm")
      }
    } else {
      // format: 31.12.2019 (without time)
      sdf.applyPattern("dd.MM.yyyy")
    }
    sdf.parse(timeAsString).getTime
  }

  def findFirstDerivatives[T: ClassTag](x: Array[T])(implicit num: Numeric[T]): Array[T] = {
    val y = x.takeRight(x.length - 1)
    val dx = Array.ofDim[T](y.length)
    x.zip(y.zipWithIndex).foreach(v => dx(v._2._2) = num.minus(v._2._1, v._1))
    dx
  }

  def firstDerivativeOf[T](x: List[T])(implicit num: Numeric[T]): List[T] =
    (x, x.tail).zipped.map((x1, x2) => num.minus(x2, x1))

  def invertMap[A, B, C](nestedMap: Map[A, Map[B, C]]): Map[B, Map[A, C]] =
    nestedMap.foldLeft(Map.empty[B, Map[A, C]]) {
      case (acc, (a, innerMap)) =>
        innerMap.foldLeft(acc) {
          case (innerAcc, (b, c)) =>
            innerAcc.updated(b, innerAcc.getOrElse(b, Map.empty).updated(a, c))
        }
    }

  def startOfDay(timestamp: Long, zone: ZoneId): Long =
    Instant
      .ofEpochMilli(timestamp)
      .atZone(zone)
      .truncatedTo(ChronoUnit.DAYS)
      .withZoneSameInstant(ZoneOffset.UTC)
      .toInstant
      .toEpochMilli

  def zonedStartOfDay(timestamp: Long, zone: ZoneId, ofNextDay: Boolean = false): ZonedDateTime = {
    val newTime = ZonedDateTime
      .ofInstant(Instant.ofEpochMilli(timestamp), zone)
      .truncatedTo(ChronoUnit.DAYS)
    if (ofNextDay) newTime.plusDays(1) else newTime
  }

  def startOfDayAtUTC(timestamp: Long): Long =
    Instant
      .ofEpochMilli(timestamp)
      .truncatedTo(ChronoUnit.DAYS)
      .toEpochMilli
}
