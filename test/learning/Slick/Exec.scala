package learning.Slick

import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.Await
import scala.concurrent.duration._

trait Exec {
  def exec[T](action: DBIO[T])(implicit db: JdbcProfile#Backend#Database): T =
  {
    val future = db.run(action)
    Await.result(future, 2.seconds)
  }
}
