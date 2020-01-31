package de.codecentric.injected

import de.codecentric.injected.GreeterService
import de.codecentric.injected.GreetingConfig
import de.codecentric.injected.HelloWorldHandler
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.groovy.test.handling.GroovyRequestFixture
import ratpack.guice.BindingsImposition
import ratpack.impose.ImpositionsSpec
import spock.lang.Specification

class HelloWorldHandlerTest extends Specification {

	def "should get default greeting from GreeterService"() {
		setup:
		GreeterService greeterService = Mock(GreeterService)
		String output = 'some-mocked-output'
		when:
		def result = GroovyRequestFixture.handle(new HelloWorldHandler(greeterService: greeterService)) {}
		then:
		1 * greeterService.createGreeting('Hello', 'World') >> output
		0 * _
		result.rendered(String) == output
	}

	def "should pass parameters to GreeterService"() {
		setup:
		GreeterService greeterService = Mock(GreeterService)
		String output = 'some-mocked-output'
		when:
		GroovyRequestFixture.handle(new HelloWorldHandler(greeterService: greeterService)) {
			pathBinding([name: 'somebody'])
			uri('?greeting=Qapla')
		}
		then:
		1 * greeterService.createGreeting('Qapla', 'somebody') >> output
		0 * _
	}

	def "integration test"() {
		setup:
		def aut = new GroovyRatpackMainApplicationUnderTest() {
			@Override
			protected void addImpositions(ImpositionsSpec impositions) {
				impositions.add BindingsImposition.of { r ->
					r.bindInstance(new GreetingConfig(useExclamationMark: false))
				}
			}
		}
		when:
		def response = aut.httpClient.get('/injected/somebody?greeting=Qapla')
		then:
		response.body.text == 'Qapla, somebody'
	}
}
