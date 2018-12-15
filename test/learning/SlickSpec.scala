package learning

import App.Domain.User
import infrastructure.user.UserTable
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Configuration
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test._
import slick.jdbc.JdbcProfile
import slick.lifted
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global

class LearningSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar with HasDatabaseConfigProvider[JdbcProfile] {
  val myapp = new GuiceApplicationBuilder()
    .configure(Configuration.from(
      Map(
        "slick.dbs.default.profile" -> "slick.jdbc.MySQLProfile$",
        "slick.dbs.default.db.driver" -> "com.mysql.jdbc.Driver",
        "slick.dbs.default.db.url" -> "jdbc:mysql://localhost:3306/play_db",
        "slick.dbs.default.db.user" -> "root",
        "slick.dbs.default.db.password" -> ""
      )
    )).build()

  val dbConfigProvider = myapp.injector.instanceOf[DatabaseConfigProvider]

  "UserDao" should {
    "Play can instert one row" in {
      import profile.api._

      val table = lifted.TableQuery[UserTable]

//      val user = User(6, "asdfasdf", "asdfadsfadsfasdfadsfad")
      val query1 = table.filter(_.firstName === "asdfasdf")

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
