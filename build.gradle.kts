import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val grpcVersion = "1.39.0"
val grpcKotlinVersion = "1.1.0"
val kotlinVersion = "1.5.21"
val protobufVersion = "3.17.3"
val javaProtobufLibraryVersion = "3.17.3"

plugins {
	application
	idea
	id("org.springframework.boot") version "2.5.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.google.protobuf") version "0.8.17"
	kotlin("jvm") version "1.5.21"
	kotlin("plugin.spring") version "1.5.21"
}

repositories {
	mavenCentral()
	google()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.google.protobuf:protobuf-java:$javaProtobufLibraryVersion")
	implementation("com.google.protobuf:protobuf-java-util:$javaProtobufLibraryVersion")
	implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
	implementation("io.grpc:grpc-netty-shaded:$grpcVersion")
	implementation("io.grpc:grpc-services:$grpcVersion")
	implementation("javax.annotation:javax.annotation-api:1.3.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// Need to add generated proto interface source directories so that we can import them
idea {
	module {
		generatedSourceDirs.add(file("build/generated/source/proto/main/grpc"))
		generatedSourceDirs.add(file("build/generated/source/proto/main/grpckt"))
		generatedSourceDirs.add(file("build/generated/source/proto/main/java"))
	}
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:$protobufVersion"
	}
	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
		}
		id("grpckt") {
			artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk7@jar"
		}
	}

	// Generates proto interfaces at build/generated/source/proto/main/<id>
	generateProtoTasks {
		ofSourceSet("main").forEach {
			it.plugins {
				id("grpc")
				id("grpckt")
			}
		}
	}
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

application {
	mainClass.set("com.eventbrite.grpckotlintutorial.GrpcKotlinTutorialApplicationKt")
}