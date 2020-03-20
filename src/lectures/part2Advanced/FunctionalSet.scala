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

class DonSet[A](head: A, tail: MySet[A] ) extends MySet[A] {
  override def contains(elem: A): Boolean =
    elem == head || tail.contains(elem)

  override def +(elem: A): MySet[A] =
    if (this contains elem) this
    else new DonSet(elem, this)

  override def ++(anotherSet: MySet[A]): MySet[A] =
    tail ++ anotherSet + head

  override def map[B](f: A => B): MySet[B] =
    (tail map f) + f(head)

  override def flatMap[B](f: A => MySet[B]): MySet[B] =
    (tail flatMap f) ++ f(head)

  override def filter(predicate: A => Boolean): MySet[A] = {
    val filteredTail = tail filter predicate
    if (predicate(head)) filteredTail + head
    else filteredTail
  }

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail foreach f
  }

}

class EmptySet[A] extends MySet[A] {
  override def contains(elem: A): Boolean = false

  override def +(elem: A): MySet[A] = new DonSet[A](elem, this)

  override def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

  override def map[B](f: A => B): MySet[B] = new EmptySet[B]

  override def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]

  override def filter(predicate: A => Boolean): MySet[A] = this

  override def foreach(f: A => Unit): Unit = ()

}
