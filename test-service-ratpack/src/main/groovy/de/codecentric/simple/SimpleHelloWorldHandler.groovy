package de.codecentric.simple

import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.handling.GroovyHandler

class SimpleHelloWorldHandler extends GroovyHandler{
	@Override
	protected void handle(GroovyContext context) {
		String greeting = context.request.queryParams.greeting ?: 'Hello'
		String name = context.pathTokens.name ?: 'World'
		context.render("$greeting, $name!" as String)
	}
}
