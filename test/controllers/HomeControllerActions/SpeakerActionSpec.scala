package controllers.HomeControllerActions

import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._

class SpeakerActionSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  "/speaker" should {
    "ingest an spanish speaker" in {
      val request = FakeRequest(GET, "/speaker")
      val home = route(app, request).get

      status(home) mustBe OK
      contentAsString(home) must include ("Hola!!, soy spanish speaker injectado")
    }
  }
}
