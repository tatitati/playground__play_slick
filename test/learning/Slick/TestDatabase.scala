package learning.Slick

import infrastructure.user.{User, UserSchema}
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test._
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery
import scala.concurrent.duration._

import scala.concurrent.Await

class TestDatabase extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  val userTable = TableQuery[UserSchema]

  "Slick" should {
    "Statements represent the SQL query CREATE TABLE" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      exec(
          userTable.schema.create,
        db
      )
    }
  }

  private def exec[T](action: DBIO[T], db: JdbcProfile#Backend#Database): T =
  {
    val future = db.run(action)
    Await.result(future, 2.seconds)
  }
}
