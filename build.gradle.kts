import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion = "2.3.2"
val jvmVersion = "1.9.0"

plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.compose") version "1.5.0"
}

group = "com.zer0s2m.creeptenuous.desktop"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

fun runTasks() {
    tasks.withType<JavaCompile> {
        sourceCompatibility to JavaVersion.VERSION_17
        targetCompatibility to JavaVersion.VERSION_17
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
    version = "0.0.1-SNAPSHOT"

    project.ext.set("ktorVersion", ktorVersion)
    project.ext.set("jvmVersion", jvmVersion)

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
        if (!project.name.contains("creep-tenuous-desktop-common")) {
            implementation(project(":creep-tenuous-desktop-common"))
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.zer0s2m.creeptenuous.desktop.app.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "CreepTenuousDesktop"
            packageVersion = "1.0.0"
        }
    }
}

dependencies {
    implementation(project(":creep-tenuous-desktop-common"))
    implementation(project(":creep-tenuous-desktop-core"))
    implementation(project(":creep-tenuous-desktop-reactive"))
    implementation(project(":creep-tenuous-desktop-ui"))
    implementation(project(":creep-tenuous-desktop-app"))

    implementation(compose.desktop.currentOs)
}
