subprojects {
    dependencies {
        implementation(project(":core"))

        implementation("org.slf4j:slf4j-api:${project.ext.get("slf4jVersion")}")
        implementation("org.slf4j:slf4j-simple:${project.ext.get("slf4jVersion")}")
    }
}