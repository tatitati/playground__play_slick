package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  *
  * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
  */
class LearningSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

	"Scala" should {

		"Convert from list to map" in {
			case class Person(val age: Int, val name: String)

			val listPerson = List(
				Person(32, "juan"),
				Person(50, "antonio")
			)

			val mapPerson = listPerson.map(personItem => (personItem.name, personItem.age)).toMap
			println(mapPerson) // Map(juan -> 32, antonio -> 50)
		}
	}
}
