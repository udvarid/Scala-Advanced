package lectures.part4Implicits

object OrganisingImplicits extends App {

  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  println(List(1, 2, 6, 7, 4, 3).sorted)

  //scala.Predef -> this is where the implicit ordering rule is coming

  /*
  Implicits
    - val/var
    - object
    - method (def) = defs without parameter
   */

  //Excercise

  case class Person(name: String, age: Int)

  val persons = List(
    Person("Donát", 43),
    Person("Bianka", 31),
    Person("Peter", 38)
  )

  object AlphabeticOrdering {
    implicit val personSorter: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }
  object AgeOrdering {
    implicit val ageSorter: Ordering[Person] = Ordering.fromLessThan(_.age < _.age)
  }

  import AgeOrdering._
  println(persons.sorted)

  /*
  Implicit scope
  - normal scope = Local scope -> ahol írjuk a kódot
  - imported scope
  - compainons of all types involved in the method signature
    - List
    - Ordering
    - all the types involved = A or any supertype
   */

}
