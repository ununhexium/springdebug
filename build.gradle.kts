plugins {
  id("org.springframework.boot") version "3.1.6"
  id("io.spring.dependency-management") version "1.1.4"
  kotlin("jvm") version "1.8.22"
  kotlin("plugin.spring") version "1.8.22"
}

group = "net.lab0"
version = "1.0.0-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  implementation("org.apache.httpcomponents:httpcore:4.4.13")

  implementation("org.jetbrains.kotlin:kotlin-reflect")

  implementation("org.springframework.boot:spring-boot-starter-data-rest")
  implementation("org.springframework.boot:spring-boot-starter-web")

  // TEST

  testImplementation("org.springframework.boot:spring-boot-starter-test")
}
