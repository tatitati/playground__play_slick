package learning.Slick

import infrastructure.user.{User, UserSchema}
import org.scalatest.FunSuite
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


class SelectSpec extends FunSuite with GuiceOneAppPerTest with Injecting with MockitoSugar {
  val userTable = TableQuery[UserSchema]


  test("can select all rows") {
    var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
    exec(
        userTable.schema.drop andThen
        userTable.schema.create andThen
        (userTable ++= Seq(User("aaaaaa", "bbbbb"), User("cccccc", "ddddd"))),
      db
    )

    val rows = exec(userTable.result, db)

    assert(rows.isInstanceOf[Vector[User]])
    assert(rows.size === 2)
  }

  test("can select one column") {
    var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
    exec(
        userTable.delete andThen
        (userTable ++= Seq(User("aaaaaa", "bbbbb"), User("cccccc", "ddddd"))),
      db
    )

    val rows = exec(userTable.map(_.firstName).result, db)

    assert(rows === Vector("aaaaaa", "cccccc"))
  }

  test("can select multiple (but not all) columns") {
    var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
    exec(
      userTable.delete andThen
        (userTable ++= Seq(User("aaaaaa", "bbbbb"), User("cccccc", "ddddd"))),
      db
    )

    val rows = exec(userTable.map(t => (t.firstName, t.id)).result, db)

    assert(rows === Vector(("aaaaaa", 5),("cccccc", 6)))
  }

  test("can use LIMIT when selecting") {
    var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
    exec(
      userTable.delete andThen
        (userTable ++= Seq(User("aaaaaa", "bbbbb"), User("cccccc", "ddddd"))),
      db
    )

    val rows = exec(userTable.take(1).result, db)

    assert(rows.isInstanceOf[Vector[User]])
    assert(rows.size === 1)
  }

  test("can use ORDER BY when selecting") {
    var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
    exec(
      userTable.delete andThen
        (userTable ++= Seq(User("aaaaaa", "bbbbb"), User("cccccc", "ddddd"))),
      db
    )

    val rows = exec(userTable.sortBy(_.lastName.desc).result, db)

    assert(rows.isInstanceOf[Vector[User]])
    assert(rows === Vector(User("cccccc","ddddd", 10), User("aaaaaa", "bbbbb", 9)))
  }

  test("can select all_types_studio") {
    var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
    exec(
        userTable.delete andThen
        (userTable ++= Seq(User("aaaaaa", "bbbbb"), User("cccccc", "ddddd"))),
      db
    )

    val future = db.run(userTable.result)
    assert(future.isInstanceOf[Future[User]])

    val rows = Await.result(future, 2.seconds)
    assert(rows.isInstanceOf[Vector[User]])
  }


  private def exec[T](action: DBIO[T], db: JdbcProfile#Backend#Database): T =
  {
    val future = db.run(action)
    Await.result(future, 2.seconds)
  }
}
