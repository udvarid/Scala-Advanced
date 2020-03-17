package lectures.part1as

object DarkSugars extends App {

  //1. methods with single param
  def singleArgMeth(arg: Int): String = s"$arg hahah"

  val description = singleArgMeth {
    //write some code
    42
  }

  println(description)

  List(1,2,3).map { x =>
    x + 1
  }.foreach(println)

  //2. single abstract method
  trait Action {
    def act(x: Int): Int
  }
  val anIntance: Action = new Action {
    override def act(x: Int): Int = x + 4
  }

  val anotherInstance: Action = (x: Int) => x + 3  //ezzel is lehet anonymus class-t létrehozni, hogyha egyértelmű a compiler számára


  println(anIntance.act(4))
  println(anotherInstance.act(4))

  //runnable
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("Hello Scala")
  })

  val aSweeterThread = new Thread(() => println("Hello again Scala"))  // ugyanaz, mint az előbb

  aThread.run()
  aSweeterThread.run()

  abstract class AnAbstractClass {
    def immplemented: Int = 23
    def f(a: Int): Unit
  }

  val implementTheAbstract: AnAbstractClass = (number: Int) => println("Hey boy")
  //ezzel a nem implementált metódust implementáltuk is

  println(implementTheAbstract.immplemented)
  implementTheAbstract.f(34)

  //3.    :: and #::

  val pretendedList = 2 :: List(3, 4)
  val pretendedList2 = 1 :: 2 :: 3 :: List(4, 5)

  println(pretendedList)
  println(pretendedList2)

  //4. infix types
  class Hallo[A, B]
  //val hallo1: Hallo[Int, String] = ???
  //val hallo2: Int Hallo String = ???

  class -->[A, B]
  //val megmuti: Int --> String = ???

  //setters and getters

  class WithSetterAndGetter {
    private var korom: Int = 0
    def kor: Int = korom
    def kor_=(szam: Int): Unit =
      korom = szam
  }

  val donat = new WithSetterAndGetter
  println(donat.kor)
  donat.kor = 80
  println(donat.kor)







}
