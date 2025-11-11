plugins {
    java
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "be.kdg.keepDishesGoing"
version = "0.0.1-SNAPSHOT"
description = "KeepDishesGoing"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

extra["springModulithVersion"] = "1.4.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-amqp") // << add this
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    
    implementation("org.springframework.modulith:spring-modulith-events-api:1.4.1")
    implementation("org.springframework.modulith:spring-modulith-events-amqp:1.4.1")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation ("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("org.springframework.security:spring-security-test")
    //MAJOR VULNERABILITY
    //CVE-2021-44228 -> Base Score: CRITICAL (10.0)
    implementation("org.apache.logging.log4j:log4j-api:2.13.3")
    implementation ("org.apache.logging.log4j:log4j-core:2.13.3")
    testImplementation ("org.springframework.security:spring-security-test")
    
    implementation("com.stripe:stripe-java:25.0.0")
    implementation("io.github.cdimascio:dotenv-java:3.0.0")
    
    
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")

    runtimeOnly ("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
}


dependencyManagement {
    imports {
        mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
