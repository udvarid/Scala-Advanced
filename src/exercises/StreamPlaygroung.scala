package exercises

import scala.annotation.tailrec

abstract class MyStream[+A] {
  def isEmpty: Boolean
  def head: A
  def tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B] //prepend operator
  def ++[B >: A](anotherStream: MyStream[B]): MyStream[B] //concatenate

  def foreach(f: A => Unit): Unit
  def map[B](f: A => B): MyStream[B]
  def flatmap[B](f: A => MyStream[B]): MyStream[B]
  def filter(predicat: A => Boolean): MyStream[A]

  def take(n: Int): MyStream[A] //takes the first n elements from the stream
  def takeAsList(n: Int): List[A] = take(n).toList()

  @tailrec
  final def toList[B >: A](acc: List[B] = Nil): List[B] =
    if (isEmpty) acc.reverse
    else tail.toList(head :: acc)
}

object EmptyStream extends MyStream[Nothing] {
  override def isEmpty: Boolean = true
  override def head: Nothing = throw new NoSuchElementException
  override def tail: MyStream[Nothing] = throw new NoSuchElementException
  override def #::[B >: Nothing](element: B): MyStream[B] = new Cons(element, this)
  override def ++[B >: Nothing](anotherStream: MyStream[B]): MyStream[B] = anotherStream
  override def foreach(f: Nothing => Unit): Unit = ()
  override def map[B](f: Nothing => B): MyStream[B] = this
  override def flatmap[B](f: Nothing => MyStream[B]): MyStream[B] = this
  override def filter(predicat: Nothing => Boolean): MyStream[Nothing] = this
  override def take(n: Int): MyStream[Nothing] = this
}

class Cons[+A](hd: A, t1: => MyStream[A]) extends MyStream[A] {
  override def isEmpty: Boolean = false
  override val head: A = hd
  override lazy val tail: MyStream[A] = t1 // call by need
  override def #::[B >: A](element: B): MyStream[B] = new Cons(element, this)
  override def ++[B >: A](anotherStream: MyStream[B]): MyStream[B] = new Cons(head, tail ++ anotherStream)
  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }
  override def map[B](f: A => B): MyStream[B] = new Cons(f(head), tail.map(f))
  override def flatmap[B](f: A => MyStream[B]): MyStream[B] = f(head) ++ tail.flatmap(f)
  override def filter(predicat: A => Boolean): MyStream[A] =
    if (predicat(head)) new Cons(head, tail.filter(predicat))
    else tail.filter(predicat)

  override def take(n: Int): MyStream[A] =
    if (n <= 0) EmptyStream
    else if (n == 1) new Cons(head, EmptyStream)
    else new Cons(head, tail.take(n - 1))
}

object MyStream {
  def from[A](start: A)(generator: A => A): MyStream[A] =
    new Cons(start, MyStream.from(generator(start))(generator))
}

object StreamPlaygroung extends App{

  val naturals = MyStream.from(1)(_ + 1)
  println(naturals.head)
  println(naturals.tail.head)
  println(naturals.tail.tail.head)

  val startsFrom0 = 0 #:: naturals
  println(startsFrom0.head)

  startsFrom0.take(10000).foreach(println)

  //map, flatmap
  println(startsFrom0.map(_ * 2).take(100).toList())

}
