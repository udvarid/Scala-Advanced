package lectures.part3concurrency

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Random, Success, Try}
import scala.concurrent.duration._

object FutureExcercise extends App {

  //1.
  val aFuture = Future {
    42
  }

  aFuture.onComplete {
    case Success(a) => println(s"The result is $a")
    case Failure(exception) => println(s"I've failed $exception")
  }

  Await.result(aFuture, 2.seconds)

  //2.
  def inSequence[T, Z](fa: Future[T], fb: Future[Z]): Future[Z] = {
    fa.flatMap(_ => fb)
  }

  val future1 = Future {
    Thread.sleep(1000)
    11
  }

  val future2 = Future {
    Thread.sleep(1000)
    22
  }

  val seqThread = inSequence(future1, future2)

  Await.result(seqThread, 2.seconds)

  println(seqThread.value)


  //3.
  def firstFuture[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val promise = Promise[A]
    fa.onComplete(promise.tryComplete)
    fb.onComplete(promise.tryComplete)
    promise.future
  }

  def lastFuture[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val promise = Promise[A]
    val lastPromise = Promise[A]

    val checkAndComplete = (result : Try[A]) =>
      if (!promise.tryComplete(result)) lastPromise.complete(result)

    fa.onComplete(checkAndComplete)
    fb.onComplete(checkAndComplete)
    lastPromise.future
  }

  val random = new Random()

  val future31 = Future {
    Thread.sleep(random.nextInt(1000))
    100
  }

  val future32 = Future {
    Thread.sleep(random.nextInt(1000))
    200
  }

  val myFuture: Future[Int] = firstFuture(future31, future32)
  val myFuture2: Future[Int] = lastFuture(future31, future32)

  myFuture.onComplete {
    case Success(a) => println(s"The result is $a")
    case Failure(exception) => println(s"I've failed $exception")
  }

  myFuture2.onComplete {
    case Success(a) => println(s"The result is $a")
    case Failure(exception) => println(s"I've failed $exception")
  }
  Await.result(myFuture, 2.seconds)
  Await.result(myFuture2, 2.seconds)

}
