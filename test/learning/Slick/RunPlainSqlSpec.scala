package learning.Slick

import infrastructure.user.{User, UserSchema}
import org.scalatest.FlatSpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test.Injecting
import slick.jdbc.{GetResult, JdbcProfile}
import slick.lifted.TableQuery

import scala.concurrent.duration._
import scala.concurrent.Await
import slick.jdbc.MySQLProfile.api._

class RunPlainSqlSpec extends FlatSpec with GuiceOneAppPerTest with Injecting {
  val userTable = TableQuery[UserSchema]


  "Slick" should "run plain sql queries" in {
    val db = cleanDb()
    givenDbFixture(db)

    var action = sql"""select first_name from user""".as[String]
    var results = Await.result(db.run(action), 2.seconds)
    assert(results === Vector("aaaaaa", "cccccc"))


    var action1 = sql"""select * from user""".as[(Long, String, String)]
    var results1 = Await.result(db.run(action1), 2.seconds)
    assert(results1 === Vector((7,"aaaaaa","bbbbb"), (8,"cccccc","ddddd")))


    var action3 = sql"""select * from user""".as[(Long, String)]
    var results3 = Await.result(db.run(action3), 2.seconds)
    assert(results3 === Vector((7, "aaaaaa"), (8, "cccccc")))

    
    implicit val getChannelResult = GetResult(r => User(r.<<, r.<<, r.<<))
    var action2 = sql"""select * from user""".as
    var results2 = Await.result(db.run(action2), 2.seconds)
    assert(results2 === Vector(User(7,"aaaaaa","bbbbb"), User(8,"cccccc","ddddd")))
  }



  private def cleanDb(): JdbcProfile#Backend#Database = {
    var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
    val action = userTable.delete
    val future = db.run(action)
    Await.result(future, 2.seconds)
    db
  }

  private def givenDbFixture(db: JdbcProfile#Backend#Database): Unit = {
    val newusers = Seq(
      User(7, "aaaaaa", "bbbbb"),
      User(8, "cccccc", "ddddd")
    )

    val action = userTable ++= newusers
    val future = db.run(action)
    Await.result(future, 2.seconds)
  }
}
