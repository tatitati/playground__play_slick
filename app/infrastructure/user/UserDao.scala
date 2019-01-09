package infrastructure.user


import com.google.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

class UserDao @Inject() (
                          @NamedDatabase("mydb") protected val dbConfigProvider: DatabaseConfigProvider,
                          executionContext: ExecutionContext
                        ) extends HasDatabaseConfigProvider[JdbcProfile] {


  private val table = TableQuery[UserSchema]

  def insert(user: User): Future[Unit] = {
    db.run(table += user).map{ _ => () }(executionContext)
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