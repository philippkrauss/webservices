package de.codecentric.injected

import groovy.util.logging.Slf4j
import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.handling.GroovyHandler

import javax.inject.Inject

@Slf4j
class HelloWorldHandler extends GroovyHandler {

	@Inject
	GreeterService greeterService

	@Override
	protected void handle(GroovyContext context) {
		String greeting = context.request.queryParams.greeting ?: 'Hello'
		String name = context.pathTokens.name ?: 'World'
		context.render greeterService.createGreeting(greeting, name)
	}
}
