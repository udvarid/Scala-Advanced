package lectures.part3concurrency

import java.util.concurrent.atomic.AtomicReference

import scala.collection.parallel.immutable.ParVector

object ParallelUtils extends App {

  //1 - parallel collection
  val parList = List(1, 2, 3).par

  val aParVector = ParVector[Int](1,2,3)

  def measure[T](operation: => T): Long = {
    val time = System.currentTimeMillis()
    operation
    System.currentTimeMillis() - time
  }

  val list = (1 to 100000).toList
  val serialTime = measure {
    list.map(_ + 1)
  }

  val parallelTime = measure {
    list.par.map(_ + 1)
  }

  println("SerialTime " + serialTime)
  println("parallelTime " + parallelTime)

  // map, flatmap, filter, foreach can be used at paralellised version
  // reduce and fold is not secure, as the serial order is not secured! Not to use!

  //2. atomic ops and references

  val atomic = new AtomicReference[Int](2)

  val currentAtomicValue = atomic.get() // thread safe
  atomic.set(4) // thread safe
  val oldValue = atomic.getAndSet(6)  //thread safe combo
  println(oldValue)
  atomic.compareAndSet(5, 7) //thread safe combo
  //if the expectedvalue is true, then set the new value
  println(atomic.get())
  atomic.compareAndSet(6, 7)
  println(atomic.get())

  println(atomic.updateAndGet(_ + 4)) //thread safe function run
  println(atomic.getAndUpdate(_ + 4)) //thread safe function run, reverse order
  println(atomic.get())


}
