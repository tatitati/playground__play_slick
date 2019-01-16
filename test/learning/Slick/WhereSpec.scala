package learning.Slick

import infrastructure.user.{User, UserSchema}
import org.scalatest.FunSuite
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.guice._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test._
import slick.jdbc.{JdbcProfile, MySQLProfile}
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery


class WhereSpec extends FunSuite with GuiceOneAppPerTest with Injecting with MockitoSugar with Exec {
  val userTable = TableQuery[UserSchema]

  test("Create queries when filter instead of Actions") {
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

  test("Select filtering when equal") {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db

      var rows = exec(userTable.filter(_.firstName === "cccccc").result, db)
      assert(rows === Vector(User("cccccc", "ddddd", 2)))
  }

  test("Select filtering when NO equal") {
    var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db

    var rows = exec(userTable.filter(_.firstName =!= "cccccc").result, db)
    assert(rows === Vector(User("aaaaaa", "bbbbb", 1)))
  }
}
