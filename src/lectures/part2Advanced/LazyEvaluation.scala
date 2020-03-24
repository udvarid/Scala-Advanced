package lectures.part2Advanced

object LazyEvaluation extends App {

  //lazy delays the evaluation
  lazy val x: Int = throw new RuntimeException //it will not crash as not evaluated

  lazy val x2: Int = {
    println("hello")
    42
  }

  println(x2)
  println(x2)

  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }

  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition

  println(if (simpleCondition && lazyCondition) "Yes" else "No")
  //as the first part is false, the 2. is not evaluated as not necessery, therefore
  //the "Boo" hasn't written out

  //conjunction with call by name
  def byNameMethod(n: => Int): Int = {
    lazy val t = n  //as this is a lazy value, onlye once evaluated CALL BY NEED
    t + t + t + 1
  }

  def retreiveMagicNumber: Int = {
    println("Waiting")
    Thread.sleep(1000)
    42
  }

  println(byNameMethod(retreiveMagicNumber))

  val numbers = List(5, 23, 32, 27, 7)

  def lt30Filter(x: Int): Boolean = {
    println(s"$x is less than 30?")
    x < 30
  }

  def gt20Filter(x: Int): Boolean = {
    println(s"$x is greater than 20?")
    x > 20
  }

  val lt30 = numbers.filter(lt30Filter)
  val gt20 = lt30.filter(gt20Filter)

  println(gt20)

  println("-----")
  val lt30Lazy = numbers.withFilter(lt30Filter) //withFilter use Lazy method
  val gt20Lazy = lt30Lazy.withFilter(gt20Filter)

  gt20Lazy.foreach(println)

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
    def takeAsList(n: Int): List[A]
  }

  object MyStream {
    def from[A](start: A)(generator: A => A): MyStream[A] = ???
  }




}
