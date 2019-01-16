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


class OrderBySpec extends FunSuite with GuiceOneAppPerTest with Injecting with MockitoSugar with Exec {
  val userTable = TableQuery[UserSchema]

  test("can use ORDER BY when selecting") {
    implicit val db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
    exec(
        userTable.schema.drop andThen
        userTable.schema.create andThen
        (userTable ++= Seq(User("aaaaaa", "bbbbb"), User("cccccc", "ddddd")))
    )

    val rows = exec(userTable.sortBy(_.lastName.desc).result)

    assert(rows.isInstanceOf[Vector[User]])
    assert(rows === Vector(User("cccccc","ddddd", 2), User("aaaaaa", "bbbbb", 1)))
  }
}
