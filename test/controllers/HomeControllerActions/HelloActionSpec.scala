package controllers.HomeControllerActions

import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._

class HelloActionSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  "/hello" should {
    "display hello!!!!!!" in {
      val request = FakeRequest(GET, "/hello")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/plain")
      contentAsString(home) must include ("hello!!!!!!")
    }
  }
}
