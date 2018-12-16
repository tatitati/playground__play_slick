package learning

import App.Domain.User
import infrastructure.user.UserTable
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test._
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.JdbcBackend._
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class SlickSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  "Slick" should {
    "can select all" in {
      val userTable = TableQuery[UserTable]
      val action = userTable.result

      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      assert(db.isInstanceOf[DatabaseDef])

      val future = db.run(action)
      assert(future.isInstanceOf[Future[User]])

      val rows = Await.result(future, 2.seconds)
      assert(rows.isInstanceOf[Vector[User]])
      assert(rows.size === 1)
    }

//    "can insert one row" in {
//      val userTable = TableQuery[UserTable]
//      val action = userTable.result
//
//      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
//      val future = db.run(action)
//
//      val rows = Await.result(future, 2.seconds)
//
//
//      assert(rows.isInstanceOf[Vector[User]])
//      assert(rows.size === 1)
//    }
  }
}
