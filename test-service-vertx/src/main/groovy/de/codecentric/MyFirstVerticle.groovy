package de.codecentric

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise

class MyFirstVerticle extends AbstractVerticle {

	@Override
	void start(Promise<Void> startPromise) {
		vertx.createHttpServer().requestHandler { req ->
			req.response()
				.putHeader("content-type", "text/plain")
				.end("Hello from my first verticle");
		} listen(3000) { http ->
			if (http.succeeded()) {
				startPromise.complete()
				println("HTTP server started on port 3000");
			} else {
				startPromise.fail(http.cause())
			}
		}
	}
}
