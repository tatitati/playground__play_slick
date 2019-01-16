package learning.Slick

import infrastructure.user.{User, UserSchema}
import org.scalatest.FunSuite
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.guice._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test._
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery

class AndThenSpec extends FunSuite with GuiceOneAppPerTest with Injecting with MockitoSugar with Exec {
  val userTable = TableQuery[UserSchema]

  test("can combine actions") {
    // clean db
    implicit val db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
    exec(userTable.delete)



    var actionsCombined = (
      (userTable += User("gggggg", "hhhhhh")) andThen
        (userTable += User("iiiiii", "jjjjjj")) andThen
        (userTable += User("kkkkkk", "llllll"))
      )

    val rows1 = exec(actionsCombined)
    assert(rows1 == 1)

    val rows2 = exec(userTable.result)

    assert(rows2.size === 3)
  }
}
