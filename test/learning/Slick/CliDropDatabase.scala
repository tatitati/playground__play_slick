package learning.Slick

import infrastructure.message.MessageSchema
import infrastructure.user.{User, UserSchema}
import infrastructure.writer.WriterSchema
import org.scalatest.FunSuite
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.guice._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test._
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery


class CliDropDatabase extends FunSuite with GuiceOneAppPerTest with Injecting with MockitoSugar with Exec {
  val userTable = TableQuery[UserSchema]
  val messageTable = TableQuery[MessageSchema]
  val writerTable = TableQuery[WriterSchema]


  test("Statements represent the SQL query CREATE TABLE") {
    var db = DatabaseConfigProvider.get[JdbcProfile]("mydb")(Play.current).db
    exec(
      (userTable.schema.drop) andThen
        (messageTable.schema.drop) andThen
        (writerTable.schema.drop)
      ,
      db
    )
  }
}
