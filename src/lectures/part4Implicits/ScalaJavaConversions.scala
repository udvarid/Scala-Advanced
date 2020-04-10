package lectures.part4Implicits

import java.{util => ju}

object ScalaJavaConversions extends App {

  import collection.JavaConverters._

  val javaSet: ju.HashSet[Int] = new ju.HashSet[Int]()
  (1 to 5).foreach(javaSet.add)

  println(javaSet)

  val scalaSet = javaSet.asScala

  println(scalaSet)

  /*
  Exercise
  create a Scala-Java Optional-Option
    .asScala
 */
  class ToScala[T](value: => T) {
    def asScala: T = value
  }

  implicit def asScalaOptional[T](o: ju.Optional[T]): ToScala[Option[T]] = new ToScala[Option[T]](
    if (o.isPresent) Some(o.get) else None
  )

  val juOptional: ju.Optional[Int] = ju.Optional.of(2)
  val scalaOption = juOptional.asScala
  println(scalaOption)
}
