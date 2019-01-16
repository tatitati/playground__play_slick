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
import scala.concurrent.duration._
import scala.concurrent.Await


class LeftJoinSpec extends FunSuite with GuiceOneAppPerTest with Injecting with MockitoSugar with Exec {
  val writerTable = TableQuery[WriterSchema]
  val userTable = TableQuery[UserSchema]
  val messageTable = TableQuery[MessageSchema]

  test("Create fixture with two writers") {
    implicit val db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
    exec(
        messageTable.schema.drop andThen
        writerTable.schema.drop andThen

        writerTable.schema.create andThen
        messageTable.schema.create
    )

    var writerId1 = exec(WriterDao.insertWriter ++= Seq(Writer("writer1", "writersurname1")))
    var writerId2 = exec(WriterDao.insertWriter ++= Seq(Writer("writer2", "writersurname2")))

    assert(writerId1 === Vector(1))
    assert(writerId2 === Vector(2))
  }

  test("Create fixture of two messages referencing one of the writer") {
    implicit val db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db

    var messageId1 = exec(MessageDao.insertMessage ++= Seq(Message(2, "messageContent1")))
    var messageId2 = exec(MessageDao.insertMessage ++= Seq(Message(2, "messageContent2")))

    assert(messageId1 === Vector(1))
    assert(messageId2 === Vector(2))
  }

  test("Request a message (root) with a writer relationship") {
    implicit val db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db

    var future = db.run(messageTable.joinLeft(writerTable).on(_.senderId === _.id).result)
    var message = Await.result(future, 2.seconds)

    assert(
      message == Vector(
        (
          Message(2,"messageContent1",1),
          Some(Writer("writer2","writersurname2",2))
        ), (
          Message(2,"messageContent2",2),
          Some(Writer("writer2","writersurname2",2))
        )
      )
    )
  }
}