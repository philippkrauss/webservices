package de.codecentric.validation

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class GreetValidatedUserHandler implements Handler<RoutingContext> {

	@Override
	void handle(RoutingContext event) {
		ValidatedUser validatedUser = (ValidatedUser) event.data()['name']
		event.response().end("Hello, ${validatedUser.name}!")
	}
}
