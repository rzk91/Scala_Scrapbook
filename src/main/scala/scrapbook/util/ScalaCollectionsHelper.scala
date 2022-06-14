package scrapbook.util

import com.fasterxml.jackson.databind.ObjectMapper

import scala.reflect.ClassTag
import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

object ScalaCollectionsHelper {

  implicit def traversableToRichTraversable[A](l: Traversable[A]): RichTraversable[A] =
    new RichTraversable(l)

  class RichTraversable[A](val target: Traversable[A]) {
    def sumBy[B](f: A => B)(implicit num: Numeric[B]): B = target.map(f).sum

    def averageBy[B](f: A => B)(implicit frac: Fractional[B]): Option[B] =
      Try(frac.div(target.sumBy(f), frac.fromInt(target.size))).toOption

    def nonEmptyAnd[B](p: Traversable[A] => Boolean): Boolean = target.nonEmpty && p(target)

    // To overcome the fact that `TraversableLike` does not have a `contains` method (wonder why!)
    // Cannot name it `contains` since IntelliJ then suggests changing `.exists(_ == element)` to `.contains(element)`,
    // which would cause an endless recursive loop!
    def containsElement[B >: A](element: B): Boolean = target.exists(_ == element)
  }

  implicit class MoreStringOps(val str: String) extends AnyVal {
    def elseIfEmpty(default: => String): String = if (str.isEmpty) default else str

    def elseIfContains(substr: String, default: => String): String =
      if (str.contains(substr)) default else str

    def splitInTwo(by: String): (String, String) = {
      val split = str.split(by)
      (split.head, split.last)
    }

    def hasValue: Boolean = str != null && !str.isBlank
    def removeQuotes(): String = str.replaceAll("\"", "")
    def replaceSpacesWithHyphens(): String = str.replaceAll(" ", "-")
    def replaceHyphensWithSpaces(): String = str.replaceAll("-", " ")
    def encodeSpacesInUrl(): String = str.replaceAll(" ", "%20")

    // Json utils
    def readJson[A](clazz: Class[A])(implicit mapper: ObjectMapper): A =
      mapper.readValue(str, clazz)
  }

  implicit class MapOps[A, B](val target: Map[A, B]) extends AnyVal {
    def getValueAs[C](key: => A): Try[C] = Try(target(key).asInstanceOf[C])
    def getValueAsString(key: => A): Try[String] = Try(target(key).toString.trim)
    def getValueAsInt(key: => A): Try[Int] = getValueAsString(key).map(_.toInt)
    def getValueAsLong(key: => A): Try[Long] = getValueAsString(key).map(_.toLong)
    def getValueAsDouble(key: => A): Try[Double] = getValueAsString(key).map(_.toDouble)

    def getValueAsBoolean(key: => A): Try[Boolean] = getValueAsString(key).collect {
      case v: String if v.toLowerCase == "true" || v == "1"  => true
      case v: String if v.toLowerCase == "false" || v == "0" => false
    }
  }

  implicit class TryOps[A](val target: Try[A]) extends AnyVal {
    def exists(f: A => Boolean): Boolean = target.isSuccess && f(target.get)
    def contains[B >: A](b: B): Boolean = target.isSuccess && b == target.get
  }

  /**
    * Suppresses all errors of type `E` alongside `NonFatal` errors.
    *
    * <b>NOTE</b>: Use with care (suppressing `StackoverflowError` can cause very adverse effects!!!)
    * @param r Code to be executed inside a try-block
    * @tparam A Output type parameter
    * @tparam E Exception type parameter (that needs to be suppressed)
    * @return a `Try` object of type `A`
    */
  def tryWith[A, E <: Throwable: ClassTag](r: => A): Try[A] =
    try {
      Success(r)
    } catch {
      case e: E        => Failure(e)
      case NonFatal(e) => Failure(e)
    }

  /**
    * Suppresses only errors of type `E`
    *
    * <b>NOTE</b>: Use with care (suppressing some errors can cause very adverse effects!!!)
    * @param r Code to be executed inside a try-block
    * @tparam A Output type parameter
    * @tparam E Exception type parameter (that needs to be suppressed)
    * @return a `Try` object of type `A`
    */
  def tryAndCatchOnly[A, E <: Throwable: ClassTag](r: => A): Try[A] =
    try {
      Success(r)
    } catch {
      case e: E => Failure(e)
    }
}
