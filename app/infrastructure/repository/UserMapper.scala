package App.Infrastructure.Repository

import App.Domain._
import slick.lifted.{Tag, TableQuery}
import slick.jdbc.MySQLProfile.api._


class UserMapper(tag: Tag) extends Table[User](tag, "user") {

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)(null)
  def firstName = column[String]("first_name")(null)
  def lastName = column[String]("last_name")(null)
  def mobile = column[Long]("mobile")(null)
  def email = column[String]("email")(null)

  override def * =
    (id, firstName, lastName, mobile, email) <> (User.tupled, User.unapply)
}