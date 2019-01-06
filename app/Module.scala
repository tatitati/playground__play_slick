import application.Speaker.WorkerInt
import application.Worker.TeacherWorker
import com.google.inject.AbstractModule


class Module extends AbstractModule {

	// this injection is done with pragmatic binding. You can see the "binding annotation style" in SpeakerInt.scala
	def configure() = {
		bind(classOf[WorkerInt])
			.to(classOf[TeacherWorker])
	}
}