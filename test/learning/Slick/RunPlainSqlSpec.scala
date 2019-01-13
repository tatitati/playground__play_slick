package learning.Slick

import infrastructure.user.{User, UserSchema}
import org.scalatest.FlatSpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test.Injecting
import slick.jdbc.{GetResult, JdbcProfile}
import slick.lifted.TableQuery
import slick.jdbc.MySQLProfile.api._

class RunPlainSqlSpec extends FlatSpec with GuiceOneAppPerTest with Injecting with Exec {
  val userTable = TableQuery[UserSchema]


  "Slick" should "run plain sql queries" in {
    var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
    givenDbFixture(db)

    var action = sql"""select first_name from user""".as[String]
    var results = exec(action, db)
    assert(results === Vector("aaaaaa", "cccccc"))


    var action1 = sql"""select * from user""".as[(String, String, Long)]
    var results1 = exec(action1, db)
    assert(results1 === Vector(("aaaaaa","bbbbb", 1), ("cccccc","ddddd", 2)))


    var action3 = sql"""select first_name, id from user""".as[(String, Long)]
    var results3 = exec(action3, db)
    assert(results3 === Vector(("aaaaaa", 1), ("cccccc", 2)))


    implicit val getChannelResult = GetResult(r => User(r.<<, r.<<, r.<<))
    var action2 = sql"""select * from user""".as
    var results2 = exec(action2, db)
    assert(results2 === Vector(User("aaaaaa","bbbbb", 1), User("cccccc","ddddd", 2)))
  }

  private def givenDbFixture(db: JdbcProfile#Backend#Database) = {
    exec(
        userTable.schema.drop andThen
        userTable.schema.create andThen
        (userTable ++= Seq(
          User("aaaaaa", "bbbbb"),
          User("cccccc", "ddddd")
        )),
      db
    )
  }
}
