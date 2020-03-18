package lectures.part1as

object AdvancedPatternMatching extends App {

  val numbers = List(1)

  val description = numbers match {
    case head :: Nil => println(s"The only element is $head")
    case _ => println("More element")
  }

  description

  class Person(val name: String, val age: Int)

  object Person {
    def unapply(person: Person): Option[(String, Int)] =
      if (person.age < 21) None
      else Some((person.name, person.age))

    def unapply(age: Int): Option[String] =
      Some(if (age < 21) "minor" else "major")
  }

  val bob = new Person("Bob", 25)

  val greeting = bob match {
    case Person(n, a) => s"Hello, a nevem $n és $a éves vagyok"
  }

  val legalStatus = bob.age match {
    case Person(status) => s"My legal status is $status"
  }

  println(greeting)
  println(legalStatus)

  //excercise
  object even {
    def unapply(arg: Int): Boolean = arg % 2 == 0
  }

  object singleDigit {
    def unapply(arg: Int): Boolean = arg < 10 && arg > -10
  }

  val number: Int = 7
  val mathProperty = number match {
    case even() => "Even number"
    case singleDigit() => "Single digit"
    case _ => "No property"
  }
  println(mathProperty)

  //infix patterns
  case class Or[A, B](a:A, b:B)
  val either = Or(2, "two")
  val humanDescription = either match {
    case number Or string => s"$number is written in $string"
  }
  println(humanDescription)

  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      override def isEmpty: Boolean = false
      override def get: String = person.name
    }
  }

  println(bob match {
    case PersonWrapper(n) => s"This person's name is $n"
    case _ => "This is an alien"
  })

}
