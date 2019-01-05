package learning.Slick

import App.Domain.User
import infrastructure.user.UserTable
import org.scalatest.FlatSpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test.Injecting
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery

import scala.concurrent.duration._
import scala.concurrent.Await
import slick.jdbc.MySQLProfile.api._

class RunPlainSqlSpec extends FlatSpec with GuiceOneAppPerTest with Injecting {
  val userTable = TableQuery[UserTable]

  "Slick" should "run plain sql queries" in {
    val db = cleanDb()
    givenDbFixture(db)

    val action = sql""" select first_name from user """.as[String]

    var results = Await.result(db.run(action), 2.seconds)
    assert(results === Vector("aaaaaa", "cccccc"))
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
