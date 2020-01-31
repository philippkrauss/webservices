package de.codecentric.protection

import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.groovy.test.handling.GroovyRequestFixture
import spock.lang.Specification

class GreetValidatedUserHandlerTest extends Specification {

	def "should greet validated user"() {
		when:
		def result = GroovyRequestFixture.handle(new GreetValidatedUserHandler()) {
			registry {
				add(new ValidatedUser(name: 'somebody'))
			}
		}
		then:
		result.rendered(String) == 'Hello, somebody!'
	}

	def "integration test: greet somebody"() {
		setup:
		def aut = new GroovyRatpackMainApplicationUnderTest()
		when:
		def response = aut.httpClient.requestSpec { request ->
			request.headers.set('X-VALIDATED-USER', 'somebody')
		} get('/protection')
		then:
		response.body.text == 'Hello, somebody!'
	}

	def "integration test: fail on missing header"() {
		setup:
		def aut = new GroovyRatpackMainApplicationUnderTest()
		when:
		def response = aut.httpClient.get('/protection')
		then:
		response.statusCode == HttpURLConnection.HTTP_FORBIDDEN
	}
}
