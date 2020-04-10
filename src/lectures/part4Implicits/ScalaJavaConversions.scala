package lectures.part4Implicits

import java.{util => ju}

object ScalaJavaConversions extends App {

  import collection.JavaConverters._

  val javaSet: ju.HashSet[Int] = new ju.HashSet[Int]()
  (1 to 5).foreach(javaSet.add)

  println(javaSet)

  val scalaSet = javaSet.asScala

  println(scalaSet)
}
