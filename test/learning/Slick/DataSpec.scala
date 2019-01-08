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


class DataSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  val userTable = TableQuery[UserSchema]

  "Slick" should {
    "can delete all rows" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      exec(userTable.delete, db)
    }

    "can insert a single row" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      val action = userTable += User(9, "eeeeee", "ffffff")
      val rows = exec(action, db)

      assert(rows == 1)
    }

    "can insert two rows: Way 1" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      val newusers = Seq(
        User(7, "aaaaaa", "bbbbb"),
        User(8, "cccccc", "ddddd")
      )

      val action = userTable ++= newusers
      val rows = exec(action, db)

      assert(rows == Some(2))
    }

    "can select all" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      val action = userTable.result
      val rows = exec(action, db)

      assert(rows.isInstanceOf[Vector[User]])
      assert(rows.size === 3)
    }

    "can select all_types_studio" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      assert(db.isInstanceOf[DatabaseDef])

      val action = userTable.result

      val future = db.run(action)
      assert(future.isInstanceOf[Future[User]])

      val rows = Await.result(future, 2.seconds)
      assert(rows.isInstanceOf[Vector[User]])
    }

    "can combine actions" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      var actionsCombined = (
        (userTable += User(20, "gggggg", "hhhhhh")) andThen
        (userTable += User(21, "iiiiii", "jjjjjj")) andThen
        (userTable += User(22, "kkkkkk", "llllll"))
      )

      val rows1 = exec(actionsCombined, db)
      assert(rows1 == 1)

      val rows2 = exec(userTable.result, db)

      assert(rows2.size === 6)
    }

    "can  delete all" in  {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      val action = userTable.delete
      exec(action, db)
    }
  }

  private def exec[T](action: DBIO[T], db: JdbcProfile#Backend#Database): T =
  {
    val future = db.run(action)
    Await.result(future, 2.seconds)
  }
}
