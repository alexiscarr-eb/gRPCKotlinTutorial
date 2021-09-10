package com.eventbrite.grpckotlintutorial

import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * This class is responsible for the configuration of application dependencies. These are typically higher-level,
 * configured dependencies, which include service layer objects, data access objects, and infrastructure objects.
 * These configured dependencies are ultimately managed by the Spring IoC container.
 *
 * - New dependencies added to this class should be annotated with @Bean
 * - Application properties can be accessed in the class constructor using the @Value annotation
 */
@Configuration
open class AppConfig {
    @Bean
    open fun server(exampleService: ExampleService): Server {
        val port = 50051
        return ServerBuilder
            .forPort(port)
            .addService(exampleService)
            .addService(ProtoReflectionService.newInstance())
            .build()
    }

    @Bean
    open fun exampleService(): ExampleService {
        return ExampleService()
    }
}