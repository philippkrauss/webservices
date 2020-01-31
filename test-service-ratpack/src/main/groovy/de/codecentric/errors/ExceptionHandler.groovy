package de.codecentric.errors

import groovy.util.logging.Slf4j
import ratpack.error.ServerErrorHandler
import ratpack.handling.Context

@Slf4j
class ExceptionHandler implements ServerErrorHandler {
	@Override
	void error(Context context, Throwable throwable) throws Exception {
		log.error 'Exception when handling request', throwable
		switch (throwable) {
			case GreetingException:
				context.response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).send(throwable.message)
				break
			default:
				context.response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).send('internal server error')
				break
		}
	}
}
