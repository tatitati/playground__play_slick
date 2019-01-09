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

import scala.concurrent.Await
import scala.concurrent.duration._

class AndThenSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  val userTable = TableQuery[UserSchema]

  "Slick" should {
    "can combine actions" in {
      // clean db
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      exec(userTable.delete, db)



      var actionsCombined = (
        (userTable += User(20, "gggggg", "hhhhhh")) andThen
          (userTable += User(21, "iiiiii", "jjjjjj")) andThen
          (userTable += User(22, "kkkkkk", "llllll"))
        )

      val rows1 = exec(actionsCombined, db)
      assert(rows1 == 1)

      val rows2 = exec(userTable.result, db)

      assert(rows2.size === 3)
    }
  }

  private def exec[T](action: DBIO[T], db: JdbcProfile#Backend#Database): T =
  {
    val future = db.run(action)
    Await.result(future, 2.seconds)
  }
}
