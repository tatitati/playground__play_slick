package application.Speaker

import com.google.inject.ImplementedBy


// this is binding annotations, is the easiest way
@ImplementedBy(classOf[SpanishSpeaker])
trait SpeakerInt {
	def sayHello(): String
}
