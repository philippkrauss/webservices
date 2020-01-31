package de.codecentric.errors

import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.handling.GroovyHandler

class GreetingErrorsHandler extends GroovyHandler {
	@Override
	protected void handle(GroovyContext context) {
		if (context.request.queryParams.error == 'greeting') {
			throw new GreetingException('I do not want to greet you!')
		}
		if (context.request.queryParams.error == 'runtime') {
			throw new RuntimeException()
		}
		context.render('Hello, World!')
	}
}
