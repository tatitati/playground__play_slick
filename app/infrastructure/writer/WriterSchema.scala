package infrastructure.writer

import slick.jdbc.MySQLProfile.api._
import slick.lifted.Tag

class WriterSchema(tag: Tag) extends Table[Writer](tag, "writter") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def firstName = column[String]("first_name")
  def lastName = column[String]("last_name")

  def * = (firstName, lastName, id).mapTo[Writer]
}