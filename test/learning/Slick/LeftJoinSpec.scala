package learning.Slick

import infrastructure.message.MessageSchema
import infrastructure.user.{User, UserSchema}
import infrastructure.writer.{Writer, WriterDao, WriterSchema}
import org.scalatest.FunSuite
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.guice._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test._
import slick.jdbc.{JdbcProfile, MySQLProfile}
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery



class LeftJoinSpec extends FunSuite with GuiceOneAppPerTest with Injecting with MockitoSugar with Exec {
  val writerTable = TableQuery[WriterSchema]
  val userTable = TableQuery[UserSchema]
  val messageTable = TableQuery[MessageSchema]


  test("LEFT JOIN writer-message") {
    var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
    exec(
        messageTable.schema.drop andThen
        writerTable.schema.drop andThen

        writerTable.schema.create andThen
        messageTable.schema.create,
      db
    )

    var writerId1 = exec(
      WriterDao.insertWriter ++= Seq(Writer("writer1", "writersurname1")),
      db
    )

    var writerId2 = exec(
      WriterDao.insertWriter ++= Seq(Writer("writer2", "writersurname2")),
      db
    )

    println(writerId1)
    println(writerId2)

  }
}