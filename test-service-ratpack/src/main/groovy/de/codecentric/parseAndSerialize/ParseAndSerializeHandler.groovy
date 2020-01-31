package de.codecentric.parseAndSerialize

import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.handling.GroovyHandler
import ratpack.jackson.Jackson

class ParseAndSerializeHandler extends GroovyHandler {
	@Override
	protected void handle(GroovyContext context) {
		context.parse(GreetingRequest) then {
			context.render(Jackson.json(new GreetingResponse(message: "${it.greeting}, ${it.name}!" as String)))
		}
	}
}
