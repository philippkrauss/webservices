package de.codecentric.validation

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class ExtractUserHandler implements Handler<RoutingContext> {

	@Override
	void handle(RoutingContext event) {
		if (!event.request().headers().contains('X-AUTHENTICATED-USER')) {
			event
				.response()
				.setStatusCode(HttpURLConnection.HTTP_FORBIDDEN)
				.end()
			return
		}
		String username = event.request().headers().get('X-AUTHENTICATED-USER')
		event.data()['name'] = new ValidatedUser(name: username)
		event.next()
	}
}
