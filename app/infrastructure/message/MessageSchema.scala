package infrastructure.message

import infrastructure.writter.WriterSchema
import slick.lifted.Tag
import slick.jdbc.MySQLProfile.api._

class MessageSchema(tag: Tag) extends Table[Message](tag, "message") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def senderId = column[Long]("sender_id")
  def content = column[String]("content")

  def sender = foreignKey("sender_fk", senderId, TableQuery[WriterSchema])(_.id)
  def * = (senderId, content, id).mapTo[Message]
}