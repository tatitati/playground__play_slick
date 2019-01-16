package learning.Slick

import infrastructure.user.UserSchema
import org.scalatest.FunSuite
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.guice._
import play.api.test._
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery


class StatementsSpec extends FunSuite with GuiceOneAppPerTest with Injecting with MockitoSugar {
  val userTable = TableQuery[UserSchema]

    test("Statements represent the SQL query CREATE TABLE") {
      val statement1 = userTable.schema.createStatements
      assert(statement1.isInstanceOf[Iterator[Unit]])

      val statement2 = userTable.schema.createStatements.mkString
      assert("create table `user` (`first_name` TEXT NOT NULL,`last_name` TEXT NOT NULL,`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY)" === statement2)

      val action2 = userTable.schema.create
      assert(action2.isInstanceOf[DBIO[Unit]])
    }

    test("Statements represent the SQL query of SELECT") {
      val action1 = userTable.result
      assert(List("select `first_name`, `last_name`, `id` from `user`") === action1.statements)
      assert(Some("select `first_name`, `last_name`, `id` from `user`") === action1.statements.headOption)
    }
}
