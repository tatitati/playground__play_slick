package learning.Slick

import App.Domain.User
import infrastructure.user.UserTable
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

class CombineActionsSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  val userTable = TableQuery[UserTable]

  "Slick" should {
    "can combine actions" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      var actionsCombined = (
        (userTable += User(20, "gggggg", "hhhhhh")) andThen
          (userTable += User(21, "iiiiii", "jjjjjj")) andThen
          (userTable += User(22, "kkkkkk", "llllll"))
        )
      val future1 = db.run(actionsCombined)
      val rows1 = Await.result(future1, 2.seconds)
      assert(rows1 == 1)


      val action = userTable.result
      val future2 = db.run(action)
      val rows2 = Await.result(future2, 2.seconds)
      assert(rows2.size === 3)

      cleanDb(DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db)
    }
  }

  private def cleanDb(db: JdbcProfile#Backend#Database) = {
      val action = userTable.delete
      val future = db.run(action)
      Await.result(future, 2.seconds)
  }
}
