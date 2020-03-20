package exercises

import lectures.part2Advanced.EmptySet

import scala.annotation.tailrec

trait MySet[A] extends (A => Boolean ){

  def apply(elem: A): Boolean =
    contains(elem)
  def contains(elem: A): Boolean
  def +(elem: A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A]

  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]

  def filter(predicate: A => Boolean): MySet[A]

  def foreach(f: A => Unit): Unit
}

object MySet {
  def apply[A](values: A*): MySet[A] = {
    @tailrec
    def buildrec(valSeq: Seq[A], acc: MySet[A]): MySet[A] =
      if (valSeq.isEmpty) acc
      else buildrec(valSeq.tail, acc + valSeq.head)

    buildrec(values.toSeq, new EmptySet[A])
  }
}
