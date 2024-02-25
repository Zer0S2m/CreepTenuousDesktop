import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val slf4jVersion = "2.0.9"

plugins {
    kotlin("jvm") version "1.9.0"
    alias(libs.plugins.compose)
}

group = "com.zer0s2m.creeptenuous.desktop"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

fun parseEnvFile(): Map<String, String> {
    val env = mutableMapOf<String, String>()
    file(".env").readLines().forEach {
        val obj = it.split("=").toMutableList()
        if (obj[0].contains("export")) {
            obj[0] = obj[0].replace("export", "")
        }
        env[obj[0].trim()] = obj[1].trim()
    }
    return env
}

fun runTasks() {
    tasks.withType<JavaCompile> {
        sourceCompatibility to JavaVersion.VERSION_17
        targetCompatibility to JavaVersion.VERSION_17
    }

    tasks.withType<JavaExec> {
        environment(parseEnvFile())
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget to JavaVersion.VERSION_17
        }

        compilerOptions {
            jvmTarget to JavaVersion.VERSION_17
        }
    }

    tasks.withType<KaptGenerateStubsTask> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget to JavaVersion.VERSION_17
        }

        compilerOptions {
            jvmTarget to JavaVersion.VERSION_17
        }
    }
}

kotlin {
    jvmToolchain(17)

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

allprojects {
    runTasks()

    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "com.zer0s2m.creeptenuous.desktop"
    version = "1.0.0"

    project.ext.set("slf4jVersion", slf4jVersion)
}

subprojects {
    runTasks()

    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }

    dependencies {
        if (!project.name.contains("common")) {
            implementation(project(":common"))
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.zer0s2m.creeptenuous.desktop.app.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.AppImage)
            packageName = "CreepTenuousDesktop"
            packageVersion = "1.0.0"
        }
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":core"))
    implementation(project(":reactive"))
    implementation(project(":ui"))
    implementation(project(":app"))

    implementation(compose.desktop.currentOs)
}
