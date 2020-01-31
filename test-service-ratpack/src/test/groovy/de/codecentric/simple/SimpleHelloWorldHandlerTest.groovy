package de.codecentric.simple

import ratpack.groovy.test.handling.GroovyRequestFixture
import ratpack.test.handling.RequestFixture
import spock.lang.Specification

class SimpleHelloWorldHandlerTest extends Specification {

	def "should say hello"() {
		when:
		def result = RequestFixture.requestFixture().handle(new SimpleHelloWorldHandler())
		then:
		result.rendered(String) == 'Hello, World!'
	}

	def "should say hello to somebody"() {
		when:
		def result = GroovyRequestFixture.handle(new SimpleHelloWorldHandler()) {
			pathBinding([name: 'somebody'])
		}
		then:
		result.rendered(String) == 'Hello, somebody!'
	}

	def "should say Qapla to somebody"() {
		when:
		def result = GroovyRequestFixture.handle(new SimpleHelloWorldHandler()) {
			pathBinding([name: 'somebody'])
			uri('?greeting=Qapla')
		}
		then:
		result.rendered(String) == 'Qapla, somebody!'
	}
}
