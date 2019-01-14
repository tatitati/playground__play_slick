package infrastructure.message

import com.google.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

class MessageDao @Inject() (
                          @NamedDatabase("mydb") protected val dbConfigProvider: DatabaseConfigProvider,
                          executionContext: ExecutionContext
                        ) extends HasDatabaseConfigProvider[JdbcProfile] {


  private val table = TableQuery[MessageSchema]
}