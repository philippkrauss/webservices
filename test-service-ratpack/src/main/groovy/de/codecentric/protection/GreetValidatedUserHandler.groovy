package de.codecentric.protection

import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.handling.GroovyHandler

class GreetValidatedUserHandler extends GroovyHandler {
	@Override
	protected void handle(GroovyContext context) {
		ValidatedUser validatedUser = context.get(ValidatedUser)
		context.render("Hello, ${validatedUser.name}!" as String)
	}
}
