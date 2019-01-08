package learning.Slick

import App.Domain.User
import infrastructure.user.UserSchema
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test._
import slick.jdbc.{JdbcProfile, MySQLProfile}
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class WhereSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  val userTable = TableQuery[UserSchema]

  "Slick" should {
    "Create queries when filter instead of Actions" in {
      var action = userTable.filter(_.firstName === "bbbbb")
      assert(action.isInstanceOf[Query[
          UserSchema,
          UserSchema#TableElementType,
          Seq
          ]
        ]
      )
    }

    "Select filtering when equal" in {
        var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
        exec(
            userTable.delete andThen
            (userTable ++= Seq(User(7, "aaaaaa", "bbbbb"), User(8, "cccccc", "ddddd")))
          ,db
        )

        var rows = exec(userTable.filter(_.firstName === "cccccc").result, db)
        assert(rows === Vector(User(8, "cccccc", "ddddd")))
    }

    "Select filtering when NO equal" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      exec(
        userTable.delete andThen
          (userTable ++= Seq(User(7, "aaaaaa", "bbbbb"), User(8, "cccccc", "ddddd")))
        ,db
      )

      var rows = exec(userTable.filter(_.firstName =!= "cccccc").result, db)
      assert(rows === Vector(User(7, "aaaaaa", "bbbbb")))
    }
  }

  private def exec[T](action: DBIO[T], db: JdbcProfile#Backend#Database): T =
  {
    val future = db.run(action)
    Await.result(future, 2.seconds)
  }
}
