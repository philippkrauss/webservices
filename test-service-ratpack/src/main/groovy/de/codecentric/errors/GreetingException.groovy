package de.codecentric.errors

class GreetingException extends RuntimeException {
	GreetingException(String message) {
		super(message)
	}
}
