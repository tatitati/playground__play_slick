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

class CliDropDatabase extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar with Exec {
  val userTable = TableQuery[UserSchema]

  "Slick" should {
    "Statements represent the SQL query CREATE TABLE" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      exec(
        userTable.schema.drop,
        db
      )
    }
  }
}
