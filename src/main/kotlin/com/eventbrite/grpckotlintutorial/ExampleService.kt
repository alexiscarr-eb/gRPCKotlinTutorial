package com.eventbrite.grpckotlintutorial

import com.eventbrite.grpckotlintutorial.proto.ExampleServiceGrpcKt
import com.eventbrite.grpckotlintutorial.proto.HelloWorldRequest
import com.eventbrite.grpckotlintutorial.proto.HelloWorldResponse

class ExampleService: ExampleServiceGrpcKt.ExampleServiceCoroutineImplBase() {
    override suspend fun helloWorld(request: HelloWorldRequest): HelloWorldResponse {
        return HelloWorldResponse
            .newBuilder()
            .build()
    }
}