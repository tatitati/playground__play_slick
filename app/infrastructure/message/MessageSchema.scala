package infrastructure.message


import infrastructure.user.User
import slick.jdbc.MySQLProfile.api._
import slick.lifted.Tag

class MessageSchema(tag: Tag) extends Table[Message](tag, "message") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def senderId = column[String]("sender_id")
  def content = column[String]("content")

  def sender = foreignKey("sender_fk", senderId, TableQuery[User])
  def * = (senderId, content, id).mapTo[Message]
}