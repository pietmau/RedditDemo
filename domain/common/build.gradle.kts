@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.detekt)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
