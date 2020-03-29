package lectures.part3concurrency

import java.util.concurrent.{Executors}

object Intro extends App {

  //JVM threads
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("Running in parallel")
  })

  aThread.start() // start a paralell thread

  aThread.join() // block, until aThread finishes running

  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("Hello")))
  val threadGoodBye = new Thread(() => (1 to 5).foreach(_ => println("Good Bye")))

  threadHello.start()
  threadGoodBye.start()

  //different runs produce different runs


  //Executors
  val pool = Executors.newFixedThreadPool(10)
  pool.execute(() => println("something in the pool"))

  pool.execute(() => {
    Thread.sleep(1000)
    println("Done after 1 second")
  })

  pool.execute(() => {
    Thread.sleep(1000)
    println("almost done")
    Thread.sleep(1000)
    println("Done after 2 second")

  })

  pool.shutdown() // => this will finish the ongoing threads
  //pool.shutdownNow() // this is kill the pool at this point

  def runInParallel = {
    var x = 0

    val thread1 = new Thread(() => {
      x = 1
    })
    val thread2 = new Thread(() => {
      x = 2
    })

    thread1.start()
    thread2.start()
    if (x > 0) println(x) // only very rarely can the thread finish before this
  }

  for (_ <- 1 to 10000) runInParallel

  class BankAccount(var amount: Int) {
    override def toString: String = "" + amount
  }

  //def 1 : syncronization
  def buySafe(account: BankAccount, thing: String, price: Int) = {
    account.synchronized( {
      account.amount -= price
      println("I've bought " + thing)
      println("My account is " + account)
    })
  }

  //def 2: use @volatile before the variable which would be safe

  //excercise
  def inceptionThread(maxThreads: Int, index: Int = 1): Thread = new Thread(() => {
    if (index < maxThreads ) {
      val newThread = inceptionThread(maxThreads, index + 1)
      newThread.start()
      newThread.join()
    }
    println(s"This is the $index thread")
  })

  inceptionThread(50).start()

}
