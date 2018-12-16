package learning

import infrastructure.user.UserTable
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test._
import slick.jdbc.JdbcProfile
import slick.lifted.{TableQuery}
import slick.jdbc.PostgresProfile.api._

class SlickSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  "Slick" should {
    "can select all" in {
      val userTable = TableQuery[UserTable]
      val action = userTable.result

      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      val rows = db.run(action)
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
