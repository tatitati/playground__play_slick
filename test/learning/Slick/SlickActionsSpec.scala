package learning.Slick

import App.Domain.User
import infrastructure.user.UserTable
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test._
import slick.jdbc.JdbcBackend._
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class SlickActionsSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  val userTable = TableQuery[UserTable]

  "Slick" should {
    "can delete all rows" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      val action = userTable.delete
      val future = db.run(action)
      Await.result(future, 2.seconds)
    }

    "can insert a simngle row" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db

      val action = userTable += User(9, "eeeeee", "ffffff")
      val future = db.run(action)
      val rows = Await.result(future, 2.seconds)

      assert(rows == 1)
    }

    "can insert two rows: Way 1" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db

      val newusers = Seq(
        User(7, "aaaaaa", "bbbbb"),
        User(8, "cccccc", "ddddd")
      )

      val action = userTable ++= newusers
      val future = db.run(action)
      val rows = Await.result(future, 2.seconds)

      assert(rows == Some(2))
    }

    "can select all" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      assert(db.isInstanceOf[DatabaseDef])

      val action = userTable.result
      val future = db.run(action)
      assert(future.isInstanceOf[Future[User]])

      val rows = Await.result(future, 2.seconds)
      assert(rows.isInstanceOf[Vector[User]])
      assert(rows.size === 3)
    }

//    "can combine actions" in {
//      val actionsCombined = (
//        userTable.schema.create andThen
//
//
//      )
//    }

    "can  delete all" in  {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      val userTable = TableQuery[UserTable]
      val action = userTable.delete
      val future = db.run(action)
      Await.result(future, 2.seconds)
    }
  }
}
