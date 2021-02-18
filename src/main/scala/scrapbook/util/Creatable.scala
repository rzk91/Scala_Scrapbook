package scrapbook.util

import java.lang.reflect.Constructor

trait Creatable[A <: Creatable[A]] {
  val cs: Array[Constructor[_]] = this.getClass.getConstructors

  protected def createFromList(params: Seq[AnyRef]): A = cs(0).newInstance(params: _*).asInstanceOf[A]
}
