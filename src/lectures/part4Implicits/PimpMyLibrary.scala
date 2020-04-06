package lectures.part4Implicits

import scala.annotation.tailrec

object PimpMyLibrary extends App {

  // 2.isPrime

  implicit class RichInt(value: Int) {
    def isEven: Boolean = value % 2 == 0
    def sqrt: Double = Math.sqrt(value)

    def times(f: () => Unit): Unit = {
      def timeAux(n: Int): Unit =
        if (n <= 0) ()
        else {
          f()
          timeAux(n - 1)
        }
      timeAux(value)
    }

    def *[T](list: List[T]): List[T] = {
      def concat(n: Int): List[T] =
        if (n <= 0) List()
        else concat(n - 1) ++ list

      concat(value)
    }
  }

  println(42.isEven)  //new RichInt(42).isEven  -> compiler does this
  //type enrichment - pimping

  //Excercises

  // Enrich the String

  implicit class RichString(value: String) {
    def asInt: Int = Integer.valueOf(value)
    def encrypt: String = value.map(c => (c + 1).asInstanceOf[Char])
  }

  println("42".asInt - 30)
  println("Donat".encrypt)

  4.times(() => println("Scala Rocks"))

  println(4 * List(1, 2, 3))

  implicit def stringToInt(string: String): Int = Integer.valueOf(string)
  println("6" / 2)


}
