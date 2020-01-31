package de.codecentric.parseAndSerialize

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.groovy.test.handling.GroovyRequestFixture
import ratpack.jackson.JsonRender
import spock.lang.Specification

class ParseAndSerializeHandlerTest extends Specification {

	def "should parse request body and return greeting"() {
		when:
		def result = GroovyRequestFixture.handle(new ParseAndSerializeHandler()) {
			body('{"name": "somebody", "greeting": "Qapla"}', 'application/json')
		}
		then:
		result.rendered(JsonRender).object == new GreetingResponse(message: 'Qapla, somebody!')
	}

	def "integration test: should render response"() {
		when:
		def response = new GroovyRatpackMainApplicationUnderTest() {}.httpClient.requestSpec { request ->
			request.body.text(JsonOutput.toJson([name: 'somebody', greeting: 'Qapla']))
			request.body.type('application/json')
		} post('parseAndSerialize')
		then:
		new JsonSlurper().parseText(response.body.text) == [message: 'Qapla, somebody!']
	}
}
