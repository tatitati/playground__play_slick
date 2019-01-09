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

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


class InsertSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  val userTable = TableQuery[UserSchema]

  "Slick" should {

    "can insert a single row" in {
      // clean db
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      exec(userTable.delete, db)

      val rows = exec(
        userTable += User("eeeeee", "ffffff"),
        db
      )

      assert(rows == 1)
      assert(exec(userTable.result, db).size === 1)
    }

    "can insert two rows" in {
      // clean db
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      exec(userTable.delete, db)


      val rows = exec(
        userTable ++= Seq(User("aaaaaa", "bbbbb"), User("cccccc", "ddddd")),
        db
      )

      assert(rows == Some(2))
      assert(exec(userTable.result, db).size === 2)
    }

    "can return the id of the new inser" in {
      var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
      exec(
          userTable.schema.drop andThen
          userTable.schema.create,
        db
      )


      exec(userTable.delete, db)

      var rows = exec(
        userTable returning userTable.map(_.id) += User("eeeeee1", "ffffff1"),
        db
      )
      assert(rows == 1)



      rows = exec(
        userTable returning userTable.map(_.id) += User("eeeeee2", "ffffff2"),
        db
      )
      assert(rows == 2)



      rows = exec(
        userTable returning userTable.map(_.id) += User("eeeeee2", "ffffff2"),
        db
      )
      assert(rows == 3)
    }
  }

  private def exec[T](action: DBIO[T], db: JdbcProfile#Backend#Database): T =
  {
    val future = db.run(action)
    Await.result(future, 2.seconds)
  }
}
