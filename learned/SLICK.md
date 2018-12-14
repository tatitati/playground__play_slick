/conf/evolutions/default/1.sql

```sql
# Users schema

# --- !Ups

CREATE TABLE User (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE User;
```



infrastructure/user/userDao.scala
 > DAO (Database Access Object): is our "repository". The fight Repository vs DAO is fucking blur....

```scala
package infrastructure.user

import App.Domain.User

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import profile.api._

class UserDao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {
  private val Users = TableQuery[UserTable]

  def insert(user: User): Future[Unit] = db.run(Users += user).map { _ => () }
}
```

infrastructure/user/userTable.scala
> This should be mapper........?

```scala
package infrastructure.user

import App.Domain._
import slick.jdbc.MySQLProfile.api._
import slick.lifted.Tag

class UserTable(tag: Tag) extends Table[User](tag, "user") {

  val id = column[String]("id", O.PrimaryKey)
  val firstName = column[String]("first_name")
  val lastName = column[String]("last_name")

  // MAPPING HERE FOR "select * from...."
  override def * =
    (id, firstName, lastName) <> (User.tupled, User.unapply)
}
```
The previous file is a mapper. So all types need to match in both directions. In this case User case class is:
domain/User.scala

```scala
package App.Domain


case class User(
                 var id: Long,
                 firstName: String,
                 lastName: String
               )

```

We use all this in our controller in the next way:

```scala
class HomeController @Inject() (
                                 cc: ControllerComponents,
                                 ....
                                 slickApi: SlickApi,
                                 userDao: UserDao
                               ) (implicit executionContext: ExecutionContext) extends AbstractController(cc) {
    ....
    def insert = Action.async { implicit request =>
        userDao.insert(
            User("1", "firstnameeee", "lastnameeeee")
        ).map(_ => Ok("done"))
    }
}
```
