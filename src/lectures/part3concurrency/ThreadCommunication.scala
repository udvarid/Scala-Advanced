package lectures.part3concurrency

import scala.collection.mutable
import scala.util.Random

object ThreadCommunication extends App {

  //consumer - producer problem
  //producer -> [x] -> consumer

  class SimpleContainer {
    private var value: Int = 0

    def isEmpty: Boolean = value == 0

    def set(newValue: Int) = value = newValue

    def get = {
      val result = value
      value = 0
      result
    }
  }

  def naivProdCons() = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("Waiting...")
      while (container.isEmpty) {
        println("consumer is waiting...")
      }

      println("I've consumed " + container.get)
    })

    val producer = new Thread(() => {
      println("Producing...")
      Thread.sleep(500)
      val value = 42
      println("I've produced " + value)
      container.set(value)
    })

    producer.start()
    consumer.start()
  }


  //naivProdCons()

  def smartProdCons() = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("Consumer is waiting..")
      container.synchronized {
        container.wait()
      }

      //container must have some value
      println("I've consumed " + container.get)
    })

    val producer = new Thread(() => {
      println("Producing")
      Thread.sleep(2000)
      val value = 42

      container.synchronized{
        println("I producing the value " + value)
        container.set(value)
        container.notify()
      }
    })

    producer.start()
    consumer.start()
  }

  //smartProdCons()


  //complicated problem
  //producer -> [? ? ?] -> consumer

  def prodConsLargeBuffer() = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capaciyt: Int = 3

    val consumer = new Thread(() => {
      val random = new Random()
      while(true) {
        buffer.synchronized{
          if (buffer.isEmpty) {
            println("Buffer is empty, waiting...")
            buffer.wait()
          }

          //there must be ONE value
          val x = buffer.dequeue()
          println("Consuming " + x)

          buffer.notify()
        }

        Thread.sleep(500)
      }
    })

    val producer = new Thread(() => {
      val random = new Random()
      var i = 0
      while (true) {
        buffer.synchronized{
          if (buffer.size == capaciyt) {
            println("Buffer is at max capacity, waiting...")
            buffer.wait()
          }

          //there must be at least ONE empty space
          println("Producer producing " + i)
          buffer.enqueue(i)

          buffer.notify()

          i += 1
        }

        Thread.sleep(500)
      }
    })

    consumer.start()
    producer.start()
  }

  prodConsLargeBuffer()

}
