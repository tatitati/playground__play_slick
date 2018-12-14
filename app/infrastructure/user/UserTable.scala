package infrastructure.user

import App.Domain._
import slick.jdbc.MySQLProfile.api._
import slick.lifted.Tag

class UserTable(tag: Tag) extends Table[User](tag, "user") {

  val id = column[Long]("id", O.PrimaryKey)
  val firstName = column[String]("first_name")
  val lastName = column[String]("last_name")

  override def * =
    (id, firstName, lastName) <> (User.tupled, User.unapply)
}