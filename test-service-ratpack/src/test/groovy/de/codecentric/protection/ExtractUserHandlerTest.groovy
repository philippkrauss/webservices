package de.codecentric.protection

import ratpack.groovy.test.handling.GroovyRequestFixture
import spock.lang.Specification

class ExtractUserHandlerTest extends Specification {

	def "should reject requests without user header"() {
		when:
		def result = GroovyRequestFixture.handle(new ExtractUserHandler()) {}
		then:
		result.status.code == HttpURLConnection.HTTP_FORBIDDEN
	}

	def "should pass requests with user header to next handler in chain"() {
		when:
		def result = GroovyRequestFixture.handle(new ExtractUserHandler()) {
			header('X-VALIDATED-USER', 'somebody')
		}
		then:
		result.calledNext
		result.registry.get(ValidatedUser).name == 'somebody'
	}
}
