package learning.Slick

import infrastructure.user.UserTable
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery

class StatementsSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  val userTable = TableQuery[UserTable]

  "Slick" should {
    "Statements represent the SQL query" in {
      val statement1 = userTable.schema.createStatements
      assert(statement1.isInstanceOf[Iterator[Unit]])

      val statement2 = userTable.schema.createStatements.mkString
      assert("create table `user` (`id` BIGINT NOT NULL PRIMARY KEY,`first_name` TEXT NOT NULL,`last_name` TEXT NOT NULL)" === statement2)

      val action1 = userTable.result
      assert(List("select `id`, `first_name`, `last_name` from `user`") === action1.statements)
      assert(Some("select `id`, `first_name`, `last_name` from `user`") === action1.statements.headOption)

      val action2 = userTable.schema.create
      assert(action2.isInstanceOf[DBIO[Unit]])
    }
  }
}
