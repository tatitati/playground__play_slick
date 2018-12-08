package Packages

package object Mymodule {
	trait MyService {
		def sayHello(): String
	}

	class Greeter() extends MyService
	{
		def sayHello(): String = {
			s"Hello im an injected service"
		}
	}


}

