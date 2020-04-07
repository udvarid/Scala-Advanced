package exercises

import lectures.part4Implicits.TypeClasses.{User, UserSerialize}

object EqualityPlayground extends App {

  /*
* Equality
 */
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  implicit object FullyEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name && a.email == b.email
  }

  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean =
      equalizer.apply(a, b)
  }

  val john = User("John", 33, "John@hotmail.com")
  println(UserSerialize.serialize(john))

  val john2 = User("John", 34, "John2@hotmail.com")

  println(Equal(john, john2))

  implicit class TypeSafeEqual[T](value: T) {
    def ===(other: T)(implicit equlazer: Equal[T]): Boolean = equlazer.apply(value, other)
    def !==(other: T)(implicit equlazer: Equal[T]): Boolean = ! equlazer.apply(value, other)
  }

  println(john === john)
  println(john === john2)

  // This is Typesafe!
  // john === 42  -> this is not working!

  //Implicitly
  case class Permissions(mask: String)

  implicit val defaultPermission: Permissions = Permissions("1234")

  val standardPermission = implicitly[Permissions]

  println(standardPermission)


}
