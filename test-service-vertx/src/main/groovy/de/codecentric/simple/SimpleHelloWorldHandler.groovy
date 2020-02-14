package de.codecentric.simple

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class SimpleHelloWorldHandler implements Handler<RoutingContext> {

	@Override
	void handle(RoutingContext event) {
		String greeting = event.queryParams()['greeting'] ?: 'Hello'
		String name = event.pathParam('name') ?: 'World'
		event.response().end("${greeting}, ${name}!")
	}
}
