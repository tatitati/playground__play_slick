import com.google.inject.AbstractModule
import com.google.inject.name.Names
import Packages.Mymodule._

class Module extends AbstractModule {
	def configure() = {
		bind(
			classOf[MyService])
			.to(classOf[Greeter])
	}
}