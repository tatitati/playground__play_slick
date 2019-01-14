package infrastructure.writter

import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class WriterDao @Inject()(
                          @NamedDatabase("mydb") protected val dbConfigProvider: DatabaseConfigProvider,
                          executionContext: ExecutionContext
                        ) extends HasDatabaseConfigProvider[JdbcProfile] {


  private val table = TableQuery[WriterSchema]

  def insert(user: Writer): Future[Unit] = {
    db.run(table += user).map{ _ => () }(executionContext)
  }

  def dropSchemaAction = {
    db.run(table.schema.drop)
  }

  def createDatabase = {
    db.run(table.schema.create)
    db.run(
      DBIO.seq(
        table += Writer("Acme, Inc.", "99 Market Street"),
        table += Writer("Superior Coffee", "1 Party Place"),
        table += Writer("The High Ground", "100 Coffee Lane")
      )
    )
  }
}