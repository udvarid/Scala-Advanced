package lectures.part2Advanced

import exercises.MySet

object FunctionalSet extends App{

  val s = MySet(1, 2, 3)
  val s2 = s + 4
  val s3 = s2 + 3
  s3 foreach println

  val s4 = s3 map (x => x + 3)

  s4 foreach println

  println("-------")
  val s5 = s3 flatMap (x => MySet(x, x * 2))
  s5 foreach println

  println("-------")
  val s6 = s5 filter (_ % 2 == 0)
  s6 foreach println

  //testing the opposite set
  println("--------testing opposite")
  val baseSet = MySet(1, 3, 4, 5, 6)

  val oppSet = !baseSet
  println(baseSet(4))
  println(oppSet(4))
  println(oppSet(7))
  val oppSet2 = oppSet.filter(_ % 2 == 0)
  println(oppSet2(7))
  val oppSet3 = oppSet2 + 7
  println(oppSet3(7))


}


