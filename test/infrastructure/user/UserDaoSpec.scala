package infrastructure.user

import App.Domain.User
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Configuration
import play.api.db.slick.DatabaseConfigProvider
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test._
import scala.concurrent.ExecutionContext.Implicits.global

class UserDaoSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  val myapp = new GuiceApplicationBuilder()
    .configure(
      Configuration.from(
        Map(
          "slick.dbs.default.profile" -> "slick.jdbc.MySQLProfile$",
          "slick.dbs.default.db.driver" -> "com.mysql.jdbc.Driver",
          "slick.dbs.default.db.url" -> "jdbc:mysql://localhost:3306/play_db",
          "slick.dbs.default.db.user" -> "root",
          "slick.dbs.default.db.password" -> ""
        )
      )
    ).build()

  val dbConfigProvider = myapp.injector.instanceOf[DatabaseConfigProvider]
  val userDao = new UserDao(dbConfigProvider)

  "UserDao" should {
    "Play can inster new rows" in {
      userDao.insert(User(4, "asdfasdf", "asdfadsfadsfasdfadsfad"))
    }
  }


}
