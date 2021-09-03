import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.google.cloud.tools.jib") version "3.1.4"
    kotlin("jvm") version "1.5.21"
    kotlin("kapt") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
    kotlin("plugin.jpa") version "1.5.21"
}

group = "com.ingins"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.vladmihalcea:hibernate-types-52:2.10.4")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")


    implementation("org.liquibase:liquibase-core")


    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")
    kapt("org.mapstruct:mapstruct-processor:1.4.2.Final")


    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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

tasks.register<Exec>("composeUpCallCenter") {
    doFirst {
        workingDir = project.projectDir
        commandLine = listOf(
            "docker-compose", "up", "--build", "--remove-orphans",
            "--attach-dependencies", "--abort-on-container-exit", "callcenter.ingins.com"
        )
    }
    group = "compose"
    dependsOn("jibDockerBuild")
}

tasks.register<Exec>("composeDown") {
    doFirst {
        workingDir = project.projectDir
        commandLine = listOf("docker-compose", "down", "-v", "--remove-orphans")
    }
    group = "compose"
}

jib {
    from {
        image = "openjdk:16.0.2-oraclelinux8"
    }
    to {
        image = "call-center:latest"
    }
    container {
        jvmFlags = listOf(
            "-XX:+AlwaysActAsServerClassMachine",
            "-Xms2g",
            "-Xmx2g",
            "-XX:G1HeapRegionSize=1m",
            "-XX:+AlwaysPreTouch",
            "-XX:+ScavengeBeforeFullGC",
            "-XX:+DisableExplicitGC",
            "-XX:MaxDirectMemorySize=1g",
            "-XX:MaxMetaspaceSize=256m",
            "--illegal-access=warn",
            "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005"
        )
    }
}