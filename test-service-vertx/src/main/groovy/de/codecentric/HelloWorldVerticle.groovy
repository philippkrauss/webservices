package de.codecentric

import de.codecentric.simple.SimpleHelloWorldHandler
import de.codecentric.validation.ExtractUserHandler
import de.codecentric.validation.GreetValidatedUserHandler
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.ext.web.Router

class HelloWorldVerticle extends AbstractVerticle {

	@Override
	void start(Promise<Void> startPromise) {
		Router router = Router.router(vertx)
		router.with {
			get('/inline').handler({ context ->
				context.response().end('Hello, World!')
			})
			get('/simple').handler(new SimpleHelloWorldHandler())
			get('/simple/:name').handler(new SimpleHelloWorldHandler())
			route('/protection').handler(new ExtractUserHandler())
			get('/protection').handler(new GreetValidatedUserHandler())
		}
		vertx
			.createHttpServer()
			.requestHandler(router)
			.listen(
				config().getInteger("http.port", 8080),
				{ result ->
					if (result.succeeded()) {
						startPromise.complete()
					} else {
						startPromise.fail(result.cause())
					}
				}
			)
	}
}
