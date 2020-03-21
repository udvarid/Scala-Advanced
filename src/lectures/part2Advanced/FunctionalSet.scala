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


}


