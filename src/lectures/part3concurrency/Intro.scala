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


}
