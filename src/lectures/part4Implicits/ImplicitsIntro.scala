package lectures.part4Implicits

object ImplicitsIntro extends App {

  case class Person(name: String) {
    def greet = s"Hi, my name is $name"
  }

  implicit def fromStringToPerson(str: String): Person = Person(str)

  println("Donát".greet) // fromStringToPerson("Donát").greet
  //this works only, when the compiler can find only one implicit method which pass to here

  //implicit parameters

  def sumUp(a: Int)(implicit b: Int): Int = a + b
  implicit val defaultB = 10
  println(sumUp(4)) // the compiler put the defaultB into the 2. parameter

}
