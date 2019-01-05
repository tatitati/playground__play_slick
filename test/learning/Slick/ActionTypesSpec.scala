package learning.Slick

import App.Domain.User
import infrastructure.user.UserSchema
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery

class ActionTypesSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  val userTable = TableQuery[UserSchema]

  "Slick" should {
    "can delete all rows" in {
      val action = userTable.delete

    }

    "can insert a simngle row" in {
      val action = userTable += User(9, "eeeeee", "ffffff")
    }

    "can insert two rows: Way 1" in {
      val newusers = Seq(
        User(7, "aaaaaa", "bbbbb"),
        User(8, "cccccc", "ddddd")
      )

      val action = userTable ++= newusers
    }

    "can select all" in {
      val action = userTable.result
    }

    "can combine actions" in {

      var actionsCombined = (
        (userTable += User(20, "gggggg", "hhhhhh")) andThen
          (userTable += User(21, "iiiiii", "jjjjjj")) andThen
          (userTable += User(22, "kkkkkk", "llllll"))
        )
    }

    "can  delete all" in  {
      val action = userTable.delete
    }
  }
}
