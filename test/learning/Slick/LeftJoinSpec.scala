package learning.Slick

import infrastructure.message.{Message, MessageDao, MessageSchema}
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


  test("LEFT JOIN: create fixture with two writers") {
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

    assert(writerId1 === Vector(1))
    assert(writerId2 === Vector(2))
  }

  test("LEFT JOIN: create fixture of two messages referencing one of the writer") {
    var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db

    var messageId1 = exec(
      MessageDao.insertMessage ++= Seq(Message(2, "messageContent1")),
      db
    )

    var messageId2 = exec(
      MessageDao.insertMessage ++= Seq(Message(2, "messageContent2")),
      db
    )
  }
}