package lectures.part2Advanced

object PartialFunctions extends App {

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 89
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 89
  }

  //PF can have only 1 parameter type
  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 89
  } // a partial function value

  println(aFussyFunction(2))
  println(aNicerFussyFunction(2))
  println(aPartialFunction(2))
  println("------------------")

  //Partial function utilites
  println(aPartialFunction.isDefinedAt(3))
  println(aPartialFunction.isDefinedAt(5))

  println("------------------")
  //Option-re emeli
  println(aPartialFunction.lift(2))
  println(aPartialFunction.lift(3))

  println("------------------")
  //egy kiegészítő fv-t csatol hozzá
  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }
  println(pfChain(2))
  println(pfChain(45))

  println("------------------")
  //excercise
  val aManualFussyFunction = new PartialFunction[Int, Int] {
    override def isDefinedAt(x: Int): Boolean =
      x == 1 || x == 2 || x== 5

    override def apply(v1: Int): Int = v1 match {
      case 1 => 42
      case 2 => 56
      case 5 => 89
    }
  }

  val chatbot: PartialFunction[String, String] = {
    case "hello" => "Hello, my name is Donat-Robot"
    case "goodbye" => "Hasta la vista, Baby"
    case "call mom" => "Nem hívom fel anyád"
    case _ => "Nem értelek"
  }

  scala.io.Source.stdin.getLines().map(chatbot).foreach(println)
}
