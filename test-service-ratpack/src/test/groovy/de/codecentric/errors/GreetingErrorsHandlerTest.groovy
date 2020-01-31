package de.codecentric.errors

import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.groovy.test.handling.GroovyRequestFixture
import spock.lang.Specification

class GreetingErrorsHandlerTest extends Specification {

	def "throws a GreetingException if requested"() {
		when:
		def result = GroovyRequestFixture.handle(new GreetingErrorsHandler()) {
			uri('?error=greeting')
		}
		then:
		result.exception(GreetingException)
	}

	def "throws a RuntimeException if requested"() {
		when:
		def result = GroovyRequestFixture.handle(new GreetingErrorsHandler()) {
			uri('?error=runtime')
		}
		then:
		result.exception(RuntimeException)
	}

	def "integration test: renders GreetingException and sets proper status code"() {
		when:
		def response = new GroovyRatpackMainApplicationUnderTest().httpClient.get('errors?error=greeting')
		then:
		response.statusCode == HttpURLConnection.HTTP_INTERNAL_ERROR
		response.body.text == 'I do not want to greet you!'
	}

	def "integration test: renders RuntimeException and sets proper status code"() {
		when:
		def response = new GroovyRatpackMainApplicationUnderTest().httpClient.get('errors?error=runtime')
		then:
		response.statusCode == HttpURLConnection.HTTP_INTERNAL_ERROR
		response.body.text == 'internal server error'
	}
}
