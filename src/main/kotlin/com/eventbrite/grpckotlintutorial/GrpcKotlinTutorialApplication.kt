package com.eventbrite.grpckotlintutorial

import io.grpc.Server
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class EventServiceApplication(
	// this is public in order to support the integration tests where we need to retrieve dependencies for test env setup
	private val appContext: ApplicationContext = AnnotationConfigApplicationContext(AppConfig::class.java),
	private val server: Server = appContext.getBean(Server::class.java),
	private val serverPort: String? = "50051"
) {
	/**
	 * Start the server
	 */
	fun run() {
		server.start()

		// Register a shutdown hook to shut down server when JVM shuts down
		// https://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#addShutdownHook-java.lang.Thread-
		Runtime.getRuntime().addShutdownHook(
			Thread {
				server.shutdown()
			}
		)
	}

	/**
	 * This method blocks until the server is terminated
	 */
	fun blockUntilServerTerminates() {
		// https://grpc.github.io/grpc-java/javadoc/io/grpc/Server.html#awaitTermination--
		server.awaitTermination()
	}
}

fun main() {
	val app = EventServiceApplication()
	app.run()
	app.blockUntilServerTerminates()
}
