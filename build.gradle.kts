import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    id("org.jetbrains.compose") version "1.4.1"
}

val ktorVersion = "2.3.2"

group = "com.zer0s2m.creeptenuous.desktop"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

fun runTasks() {
    tasks.withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "com.zer0s2m.creeptenuous.desktop"
    version = "0.0.1-SNAPSHOT"

    project.ext.set("ktorVersion", ktorVersion)

    runTasks()
}

subprojects {
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

    runTasks()
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
