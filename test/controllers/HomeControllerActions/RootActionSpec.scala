package controllers.HomeControllerActions

import application.Speaker.{EnglishSpeaker, SpeakerInt, WorkerInt}
import controllers.HomeController
import infrastructure.user.UserDao
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.db.slick.SlickApi
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.ExecutionContext.Implicits.global

class RootActionSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  "/" should {
    "render the index page from a new instance of controller" in {
      val controller = new HomeController(
        cc = stubControllerComponents(),
        injectedSpeaker = mock[SpeakerInt],
        injectedWorker = mock[WorkerInt],
        englishSpeaker = mock[EnglishSpeaker],
        slickApi = mock[SlickApi],
        userDao = mock[UserDao],
        executionContext = global
      )
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Welcome to Play")
    }

    "render the index page from the application" in {
      val controller = new HomeController(
        cc = stubControllerComponents(),
        injectedSpeaker = mock[SpeakerInt],
        englishSpeaker = mock[EnglishSpeaker],
        injectedWorker = mock[WorkerInt],
        slickApi = mock[SlickApi],
        userDao = mock[UserDao],
        executionContext = global
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
}
