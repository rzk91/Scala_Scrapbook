package scrapbook.util

trait TestHelper { self: BareBonesTestSpec =>

  def multiply(a: Option[Int], b: Option[Int]): Int =
    (for {
      _a <- a
      _b <- b
    } yield _a * _b).getOrElse(self.x * self.y)
}
