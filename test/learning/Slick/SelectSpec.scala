package learning.Slick

import App.Domain.User
import infrastructure.user.UserSchema
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test._
import slick.jdbc.JdbcBackend._
import slick.jdbc.{JdbcProfile, MySQLProfile}
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


class SelectSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  val userTable = TableQuery[UserSchema]

  "Slick" should {
    "can select all rows" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      exec(
          userTable.delete andThen
          (userTable ++= Seq(User(7, "aaaaaa", "bbbbb"), User(8, "cccccc", "ddddd"))),
        db
      )

      val rows = exec(userTable.result, db)

      assert(rows.isInstanceOf[Vector[User]])
      assert(rows.size === 2)
    }

    "can select some columns" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      exec(
          userTable.delete andThen
          (userTable ++= Seq(User(7, "aaaaaa", "bbbbb"), User(8, "cccccc", "ddddd"))),
        db
      )

      val rows = exec(userTable.map(_.firstName).result, db)

      assert(rows === Vector("aaaaaa", "cccccc"))
    }

    "can select all_types_studio" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      exec(
          userTable.delete andThen
          (userTable ++= Seq(User(7, "aaaaaa", "bbbbb"), User(8, "cccccc", "ddddd"))),
        db
      )

      val future = db.run(userTable.result)
      assert(future.isInstanceOf[Future[User]])

      val rows = Await.result(future, 2.seconds)
      assert(rows.isInstanceOf[Vector[User]])
    }
  }

  private def exec[T](action: DBIO[T], db: JdbcProfile#Backend#Database): T =
  {
    val future = db.run(action)
    Await.result(future, 2.seconds)
  }
}
