// Example: https://github.com/JetBrains/kotlin/tree/master/libraries/examples/kotlin-gradle-subplugin-example

plugins {
    kotlin("jvm") version "2.0.20" // we have a kotlin project

    `java-gradle-plugin` // we generate a gradle plugin configured in the gradlePlugin section
    `kotlin-dsl` // To be able to use Kotlin when developing the Plugin<Project> class

    `maven-publish` // the generated plugin will be published to mavenLocal
}

group = "at.neon"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("compiler-embeddable"))
    // https://youtrack.jetbrains.com/issue/KT-47897/Official-Kotlin-Gradle-plugin-api
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin-api:2.0.0") // Use the appropriate version
    implementation(gradleApi())
    // must be in target program!
    // implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.5.3")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // The following does not support Kotlin 2.0 yet
    // testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.5.0")

    testImplementation("com.bennyhuo.kotlin:kotlin-compile-testing-extensions:2.0.0-1.3.0")
}

tasks.test {
    useJUnitPlatform()
}

/*
println("rootProject extras:")
rootProject.extra.properties.forEach { (key, value) -> println("-: $key: $value")  }
println("project extras:")
project.extra.properties.forEach { (key, value) -> println("- $key: $value")  }
*/

gradlePlugin {
    plugins {
        create("kPerfMeasure") {
            id = "at.neon.k-perf-measure-plugin" // to use this plugin later in other projects we will use plugins { id("at.neon.k-perf-measure") }
            implementationClass = "at.neon.gradle.KPerfMeasureGradlePlugin"
        }
    }
}
