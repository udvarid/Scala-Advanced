package lectures.part3concurrency

import scala.concurrent.Future
import scala.util.{Failure, Success}

//importatn for futures
import scala.concurrent.ExecutionContext.Implicits.global

object FuturesAndPromises extends App {

  def calculate: Int = {
    Thread.sleep(2000)
    42
  }

  val aFuture = Future {
    calculate
  }

  println(aFuture.value)

  println("Waiting on the future")

  aFuture.onComplete {
    case Success(a) => println(s"The result is $a")
    case Failure(exception)=> println(s"I've failed $exception")
  }

  Thread.sleep(3000)
}
