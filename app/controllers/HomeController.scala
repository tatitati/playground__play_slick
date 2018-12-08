package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import App.Application._

@Singleton
class HomeController @Inject() (cc: ControllerComponents, myservice: SpeakerInt) extends AbstractController(cc) {
    case class User(val age: Int, val name: String)

    val listUsers = List(
        User(32, "user1"),
        User(45, "user2")
    )

    val hello = Action {
        Ok("hello!!!!!!")
    }

    def detail = Action {
        Ok(
            Json.obj(
                "id" -> 2323,
                "name" -> "anyname"
            )
        )
    }

    def list = Action {
        Ok(
            Json.arr(
                listUsers.map(
                    userItem => Json.obj(
                        "age" -> userItem.age,
                        "name" -> userItem.name
                    )
                )
            )
        )
    }

    def say = Action {
        Ok(
            myservice.sayHello()
        )
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
