package de.codecentric.protection

import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.handling.GroovyHandler
import ratpack.registry.Registry

class ExtractUserHandler extends GroovyHandler {
	@Override
	protected void handle(GroovyContext context) {
		Optional<String> validatedUserHeader = context.header('X-VALIDATED-USER')
		if (validatedUserHeader.isPresent()) {
			context.next(Registry.single(new ValidatedUser(name: validatedUserHeader.get())))
		} else {
			context.response.status(HttpURLConnection.HTTP_FORBIDDEN).send()
		}
	}
}
