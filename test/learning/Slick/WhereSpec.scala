package learning.Slick

import infrastructure.user.{User, UserSchema}
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

      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      exec(
          userTable.schema.drop andThen
          userTable.schema.create andThen
          (userTable ++= Seq(User("aaaaaa", "bbbbb"), User("cccccc", "ddddd")))
        ,db
      )
    }

    "Select filtering when equal" in {
        var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db

        var rows = exec(userTable.filter(_.firstName === "cccccc").result, db)
        assert(rows === Vector(User("cccccc", "ddddd", 2)))
    }

    "Select filtering when NO equal" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db

      var rows = exec(userTable.filter(_.firstName =!= "cccccc").result, db)
      assert(rows === Vector(User("aaaaaa", "bbbbb", 1)))
    }
  }

  private def exec[T](action: DBIO[T], db: JdbcProfile#Backend#Database): T =
  {
    val future = db.run(action)
    Await.result(future, 2.seconds)
  }
}
