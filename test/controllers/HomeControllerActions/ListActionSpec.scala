package controllers.HomeControllerActions

import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._

class ListActionSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  "/list" should {
    "/list display a json" in {
      val request = FakeRequest(GET, "/list")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
    }
  }
}
