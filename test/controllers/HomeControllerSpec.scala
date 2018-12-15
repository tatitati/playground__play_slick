package controllers

import play.api.test._
import play.api.test.Helpers._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import App.Application._
import infrastructure.user.UserDao
import play.api.db.slick.SlickApi
import scala.concurrent.ExecutionContext.Implicits.global

class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  "/" should {
    "render the index page from a new instance of controller" in {
      val controller = new HomeController(
        cc = stubControllerComponents(),
        myservice = mock[SpeakerInt],
        englishSpeaker = mock[EnglishSpeaker],
        userDao = mock[UserDao],
        slickApi = mock[SlickApi]
      )
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Welcome to Play")
    }

    "render the index page from the application" in {
      val controller = new HomeController(
        cc = stubControllerComponents(),
        myservice = mock[SpeakerInt],
        englishSpeaker = mock[EnglishSpeaker],
        userDao = mock[UserDao],
        slickApi = mock[SlickApi]
      )
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
    }

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
    }
  }



  "/hello" should {
    "display hello!!!!!!" in {
      val request = FakeRequest(GET, "/hello")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/plain")
      contentAsString(home) must include ("hello!!!!!!")
    }
  }



  "/list" should {
    "/list display a json" in {
      val request = FakeRequest(GET, "/list")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
    }

    "/say ingest an spanish speaker" in {
      val request = FakeRequest(GET, "/say")
      val home = route(app, request).get

      status(home) mustBe OK
      contentAsString(home) must include ("Hola!!, soy un servicio injectado")
    }
  }



  "/say" should {
    "/say ingest an spanish speaker" in {
      val request = FakeRequest(GET, "/say")
      val home = route(app, request).get

      status(home) mustBe OK
      contentAsString(home) must include ("Hola!!, soy un servicio injectado")
    }
  }
}
