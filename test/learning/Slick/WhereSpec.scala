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


class WhereSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  val userTable = TableQuery[UserSchema]

  "Slick" should {
    "can filter" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      exec(userTable.delete, db)
    }
  }

  private def exec[T](action: DBIO[T], db: JdbcProfile#Backend#Database): T =
  {
    val future = db.run(action)
    Await.result(future, 2.seconds)
  }
}
