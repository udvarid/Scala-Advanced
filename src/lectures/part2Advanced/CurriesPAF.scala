package lectures.part2Advanced

object CurriesPAF extends App {

  //curried function
  val supperAdder: Int => Int => Int =
    x => y => x + y

  val add3 = supperAdder(3)

  //curried method
  def curriedAdder(x: Int)(y: Int): Int = x + y

  val add: Int => Int = curriedAdder(4)

  //lifting ETA-EXPENSION

  //function != method
  def inc(x: Int) = x + 1
  List(1, 2, 3).map(inc) //ETA expension, the method transformed to function

  //partial function applications
  val add5 = curriedAdder(5) _  //with the '_' sign I tell the compiler to convert it to function
  println(add5(4))

  //function
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simmpleAddMethod(x: Int, y: Int) =  x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  val add7Funct = (x: Int) => simpleAddFunction(x, 7)
  val add7Funct2 = simpleAddFunction.curried(7)
  val add7Funct3 = simpleAddFunction(7, _: Int)
  val add7SimplMethod = (x: Int) => simmpleAddMethod(x, 7)
  val add7SimplMethod2 = simmpleAddMethod(7, _: Int)
  val add7CurriedMethod = curriedAddMethod(7) _ //(PAF)
  val add7CurriedMethod2 = curriedAddMethod(7)(_) //(PAF)

  println(add7Funct(5))
  println(add7Funct2(5))
  println(add7Funct3(5))
  println(add7SimplMethod(5))
  println(add7SimplMethod2(5))
  println(add7CurriedMethod(5))
  println(add7CurriedMethod2(5))

  def concatenator(a: String, b: String, c: String) = a + b + c
  val greeting = concatenator("Hello ", _: String, "!")

  println(greeting("Donát"))

  val greeting2 = concatenator("Hello ", _: String, _: String)
  println(greeting2("Udvari ", "Donát"))

  def curriedFormatter(s: String)(d: Double): String = s.format(d)
  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

  val simpleFormat = curriedFormatter("%4.2f") _  //lift
  val seriousFormat = curriedFormatter("%8.6f") _
  val preciseFormat = curriedFormatter("%14.12f") _

  println("---")
  println(numbers.map(simpleFormat))
  println("---")
  println(numbers.map(curriedFormatter("%4.2f"))) //compiler does eta-expansion
  println("---")
  println(numbers.map(seriousFormat))
  println("---")
  println(numbers.map(preciseFormat))

}
