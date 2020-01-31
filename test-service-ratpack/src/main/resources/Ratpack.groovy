import static ratpack.groovy.Groovy.ratpack
import com.google.common.io.Resources
import de.codecentric.injected.GreeterService
import de.codecentric.injected.GreetingConfig
import de.codecentric.injected.HelloWorldHandler
import de.codecentric.protection.ExtractUserHandler
import de.codecentric.protection.GreetValidatedUserHandler
import de.codecentric.simple.SimpleHelloWorldHandler
import de.codecentric.parseAndSerialize.ParseAndSerializeHandler

ratpack {
	serverConfig {
		// first, try to load config from static application.properties file
		props(Resources.asByteSource(Resources.getResource('application.properties')))
		// then, map environment variables. Those take precedence
		env()

		//in case of of config via environment variables, set RATPACK_CONFIG__USE_EXCLAMATION_MARK
		require('/greeting', GreetingConfig)
	}
	bindings {
		binder { b ->
			b.bind(HelloWorldHandler).asEagerSingleton()
			b.bind(GreeterService).asEagerSingleton()
		}
	}
    handlers {
		prefix('inline') {
			get {
				render 'Hello, World!'
			}
			get(':name') {
				render "Hello $pathTokens.name!"
			}
		}
		prefix('simple') {
			get new SimpleHelloWorldHandler()
			get(':name', new SimpleHelloWorldHandler())
		}

		prefix('injected') {
			get HelloWorldHandler
			get(':name', HelloWorldHandler)
		}

		prefix('protection') {
			all new ExtractUserHandler()
			get new GreetValidatedUserHandler()
		}

		post('parseAndSerialize', new ParseAndSerializeHandler())
    }
}
