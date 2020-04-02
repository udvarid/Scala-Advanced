package lectures.part3concurrency

import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Random, Success}
import scala.concurrent.duration._

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
    case Failure(exception) => println(s"I've failed $exception")
  }


  Thread.sleep(3000)

  //mini social network

  case class Profile(id: String, name: String) {
    def poke(anotherProfile: Profile) =
      println(s"${this.name} poking ${anotherProfile.name} ")
  }

  object SocialNetwork {
    val names = Map(
      "fb.id.1-zuck" -> "Mark",
      "fb.id.2-bill" -> "Bill",
      "fb.id.0-dummy" -> "Dummy"
    )

    val friends = Map(
      "fb.id.1-zuck" -> "fb.id.2-bill"
    )

    val random = new Random()

    //API
    def fetchProfile(id: String): Future[Profile] = Future {
      //Fetching from the DB
      Thread.sleep(random.nextInt(300))
      Profile(id, names(id))
    }

    def fetchBestFriend(profile: Profile): Future[Profile] = Future {
      //Fetching from the DB
      Thread.sleep(random.nextInt(400))
      val bfid = friends(profile.id)
      Profile(bfid, names(bfid))
    }


  }


  val mark = SocialNetwork.fetchProfile("fb.id.1-zuck")
  mark.onComplete {
    case Success(markProfile) => {
      val bill = SocialNetwork.fetchBestFriend(markProfile)
      bill.onComplete {
        case Success(billProfile) => markProfile.poke(billProfile)
        case Failure(ex) => ex.printStackTrace()
      }
    }
    case Failure(ex) => ex.printStackTrace()
  }

  Thread.sleep(1000)

  //functional compostion of futures
  //map, flatmap, filter
  val nameOfTheWall = mark.map(profile => profile.name)
  val markBestFriend = mark.flatMap(profile => SocialNetwork.fetchBestFriend(profile))
  val bestFriendRestricted = markBestFriend.filter(profile => profile.name.startsWith("Z"))

  //for-comprehension
  for {
    mark <- SocialNetwork.fetchProfile("fb.id.1-zuck")
    bill <- SocialNetwork.fetchBestFriend(mark)
  } mark.poke(bill)

  Thread.sleep(1000)

  //fallbacks
  val aProfileWhatever = SocialNetwork.fetchProfile("blalal").recover {
    case e: Throwable => Profile("blala", "blala")
  }

  val aProfileWhatever2 = SocialNetwork.fetchProfile("blalal").recoverWith {
    case e: Throwable => SocialNetwork.fetchProfile("fb.id.0-dummy")
  }

  val aProfileWhatever3 = SocialNetwork.fetchProfile("blalal")
    .fallbackTo(SocialNetwork.fetchProfile("fb.id.0-dummy"))

  //online banking app
  case class User(name: String)

  case class Transaction(sender: String, receiver: String, amount: Double, status: String)

  object BankingApp {
    val name: String = "Donat bank"

    def fetchUser(name: String): Future[User] = Future {
      Thread.sleep(500)
      User(name)
    }

    def createTransaction(user: User, merchantName: String, amount: Double):
    Future[Transaction] = Future {
      Thread.sleep(1000)
      Transaction(user.name, merchantName, amount, "Success")
    }

    def purchase(userName: String, item: String, merchantName: String, cost: Double): String = {
      //fetch the user from the DB
      //create a transaction
      //wait the transaction to finish
      val transactionStatusFurue = for {
        user <- fetchUser(userName)
        transaction <- createTransaction(user, merchantName, cost)
      } yield transaction.status

      //this block the JVM until the result is received
      Await.result(transactionStatusFurue, 2.seconds) // implicit conversions
    }
  }

  println(BankingApp.purchase("Donat", "Iphone", "Bolt", 400))

  //promises
  val promise = Promise[Int]() // controller over Future
  val future = promise.future

  //thread 1 - consumer
  future.onComplete {
    case Success(r) => println(s"I've received $r")
  }

  //thread 2 - producer
  val producer = new Thread(() => {
    println("Producing some numbers")
    Thread.sleep(500)
    promise.success(401)
    println("Producer done")
  })

  producer.start()
  Thread.sleep(1000)

}
