package controllers

import App.Application._
import App.Domain.User
import javax.inject._
import play.api.db.slick.{DbName, SlickApi}
import play.api.libs.json.Json
import play.api.mvc._
import infrastructure.user.UserDao

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject() (
                                 cc: ControllerComponents,
                                 myservice: SpeakerInt,
                                 englishSpeaker: EnglishSpeaker,
                                 slickApi: SlickApi,
                                 userDao: UserDao
                               ) (implicit executionContext: ExecutionContext) extends AbstractController(cc) {

    case class Animal(val age: Int, val name: String)
    val listUsers = List(
        Animal(32, "user1"),
        Animal(45, "user2")
    )

    val hello = Action {
        Ok("hello!!!!!!")
    }

    def detail = Action {
        Ok(Json.obj(
            "id" -> 2323,
            "name" -> "anyname"
        ))
    }

    def list = Action {
        Ok(Json.arr(
            listUsers.map(
                userItem => Json.obj(
                    "age" -> userItem.age,
                    "name" -> userItem.name
                )
            )
        ))
    }

    def say = Action {
        Ok(myservice.sayHello())}

    def sayenglish = Action {
        Ok(englishSpeaker.sayHello())
    }

    def insert = Action.async { implicit request =>
        userDao.insert(
            User(2, "firstnameeee", "lastnameeeee")
        ).map(_ => Ok("done"))
    }


    /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
}
