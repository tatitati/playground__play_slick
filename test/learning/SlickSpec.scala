package learning

import App.Domain.User
import infrastructure.user.UserTable
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test._
import slick.jdbc.{JdbcActionComponent, JdbcProfile}
import slick.lifted.TableQuery
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.duration._
import scala.concurrent.Await

class SlickSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  "Slick" should {
    "can select all" in {
      val userTable = TableQuery[UserTable]
      val action = userTable.result

      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      val future = db.run(action)

      val rows = Await.result(future, 2.seconds)


      assert(rows.isInstanceOf[Vector[User]])
      assert(rows.size === 1)
    }
  }

//  val dbConfigProvider = myapp.injector.instanceOf[DatabaseConfigProvider]
//
//  "UserDao" should {
//    "Play can instert one row" in {
//      import profile.api._
//
//      val table = lifted.TableQuery[UserTable]
//
//      val user = User(6, "asdfasdf", "asdfadsfadsfasdfadsfad")
//      val db = Database.forConfig("default")
//
//      db.run(table += user).map { _ => () }
//    }
//  }


}
