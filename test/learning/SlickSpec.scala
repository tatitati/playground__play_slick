package learning

import App.Domain.User
import infrastructure.user.UserTable
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.{Configuration, Play}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test._
import play.db.Database
import slick.jdbc.JdbcProfile
import slick.lifted
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global

class SlickSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  "UserDao" should {
    "Create tablequery" in {
      val userTable = lifted.TableQuery[UserTable]
      assert(userTable.isInstanceOf[TableQuery[UserTable]])
    }

    "Play can instert one row" in {

      Database.forConfig("mysql.dev")

//      val user = User(6, "asdfasdf", "asdfadsfadsfasdfadsfad")
//
//      val userTable = lifted.TableQuery[UserTable]
//
//      dbConfig.db.run(userTable.result)
      //      val db = Database.forConfig("default")
      //
      //      db.run(table += user).map { _ => () }
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
