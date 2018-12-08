import com.google.inject.AbstractModule
import App.Application._

class Module extends AbstractModule {
	def configure() = {
		bind(
			classOf[SpeakerInt])
			.to(classOf[SpanishSpeaker])
	}
}