package App.Application

package object Speaker {
	trait SpeakerInt {
		def sayHello(): String
	}

	class EnglishSpeaker() extends SpeakerInt
	{
		def sayHello(): String = {
			s"Hello im an injected service"
		}
	}

	class SpanishSpeaker() extends SpeakerInt
	{
		def sayHello(): String = {
			s"Hola!!, soy un servicio injectado"
		}
	}
}





