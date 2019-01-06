package infrastructure.user

import App.Domain.User
import com.google.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile


class UserDao @Inject()
                        (@NamedDatabase("mydb") protected val dbConfigProvider: DatabaseConfigProvider)
                        (implicit executionContext: ExecutionContext)
                        extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._
  private val table = TableQuery[UserSchema]

  def insert(user: User): Future[Unit] = {
    db.run(table += user).map { _ => () }
  }

  def dropSchemaAction = {
    db.run(table.schema.drop)
  }

  def createDatabase = {
    db.run(table.schema.create)
    db.run(
      DBIO.seq(
        table += User(101, "Acme, Inc.", "99 Market Street"),
        table += User(49, "Superior Coffee", "1 Party Place"),
        table += User(150, "The High Ground", "100 Coffee Lane")
      )
    )
  }
}