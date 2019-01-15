package infrastructure.writer

import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

object WriterDao {
  lazy val writerTable = TableQuery[WriterSchema]
  lazy val insertWriter = writerTable returning writerTable.map(_.id)
}

class WriterDao @Inject()(
                          @NamedDatabase("mydb") protected val dbConfigProvider: DatabaseConfigProvider,
                          executionContext: ExecutionContext
                        ) extends HasDatabaseConfigProvider[JdbcProfile] {


  def insert(user: Writer): Future[Unit] = {
    db.run(WriterDao.writerTable += user).map{ _ => () }(executionContext)
  }

  def dropSchemaAction = {
    db.run(WriterDao.writerTable.schema.drop)
  }

  def createDatabase = {
    db.run(WriterDao.writerTable.schema.create)
    db.run(
      DBIO.seq(
        WriterDao.writerTable += Writer("Acme, Inc.", "99 Market Street"),
        WriterDao.writerTable += Writer("Superior Coffee", "1 Party Place"),
        WriterDao.writerTable += Writer("The High Ground", "100 Coffee Lane")
      )
    )
  }
}