package infrastructure.user


import slick.jdbc.MySQLProfile.api._
import slick.lifted.Tag

class UserSchema(tag: Tag) extends Table[User](tag, "user") {

  def firstName = column[String]("first_name")
  def lastName = column[String]("last_name")
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def * =
    (firstName, lastName, id).mapTo[User]
}