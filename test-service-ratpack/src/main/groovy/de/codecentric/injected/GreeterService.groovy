package de.codecentric.injected


import javax.inject.Inject

class GreeterService {

	@Inject
	GreetingConfig config

	String createGreeting(String greeting, String name) {
		"${greeting}, ${name}${config.useExclamationMark ? '!' : ''}"
	}
}
