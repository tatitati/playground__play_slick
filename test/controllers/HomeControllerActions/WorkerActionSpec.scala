package controllers.HomeControllerActions

import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._

class WorkerActionSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  "/worker" should {
    "/worker ingest an english worker" in {
      val request = FakeRequest(GET, "/worker")
      val home = route(app, request).get

      status(home) mustBe OK
      contentAsString(home) must include ("I'm a teacher worker")
    }
  }
}
