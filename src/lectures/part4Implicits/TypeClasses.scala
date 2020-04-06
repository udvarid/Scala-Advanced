package lectures.part4Implicits

object TypeClasses extends App {

  trait HTMLWritable {
    def toHtml: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHtml: String = s"<div>$name ($age) <a href=$email/> </div>"
  }

  User("John", 33, "John@hotmail.com").toHtml

  //2. pattern matching
  object HTMLSerializerPMn {
    def serializeToHtml(value: Any) = value match {
      case User(n, a, e) =>
      case _ =>
    }
  }

  //3. better design
  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  object UserSerialize extends HTMLSerializer[User] {
    def serialize(user: User): String = s"<div>$user.name ($user.age) <a href=$user.email/> </div>"
  }

  val john = User("John", 33, "John@hotmail.com")
  println(UserSerialize.serialize(john))

  //Typeclass
  trait MyTypeClass[T] {
    def action(value: T): String
  }

  /*
  * Equality
   */
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  implicit object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  object FullyEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name && a.email == b.email
  }

  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean =
      equalizer.apply(a, b)
  }

  val john2 = User("John", 34, "John2@hotmail.com")

  println(Equal(john, john2))


}
