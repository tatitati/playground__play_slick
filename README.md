## Setup

```
sbt run
```

## run tests

```
sbt test
```


## Some stuff to play

http://localhost:9000

http://localhost:9000/hello

http://localhost:9000/detail

http://localhost:9000/list


# Learned lessons

## DI with Guice
- In play it works out of the box. Not needed any setup to make it work
- DI configuration is setup in Module.scala

```
// app/Module.scala

import com.google.inject.AbstractModule
import App.Application._

class Module extends AbstractModule {
	def configure() = {
		bind(
			classOf[SpeakerInt])
			.to(classOf[SpanishSpeaker])
	}
}
```

>If you call this module Module and place it in the root package (app folder), it will automatically be registered with Play. Alternatively, if you want to give it a different name or put it in a different package, you can register it with Play by appending its fully qualified class name to the play.modules.enabled list in application.conf:
