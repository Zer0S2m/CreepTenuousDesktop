pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(
    "creep-tenuous-desktop-common",
    "creep-tenuous-desktop-core",
    "creep-tenuous-desktop-ui",
    "creep-tenuous-desktop-app",
    "creep-tenuous-desktop-reactive",
    "creep-tenuous-desktop-reactive:creep-tenuous-desktop-reactive-models",
    "creep-tenuous-desktop-reactive:creep-tenuous-desktop-reactive-handlers",
    "creep-tenuous-desktop-reactive:creep-tenuous-desktop-reactive-triggers"
)

findProject(":creep-tenuous-desktop-reactive:creep-tenuous-desktop-reactive-handlers")?.name =
    "creep-tenuous-desktop-reactive-handlers"
findProject(":creep-tenuous-desktop-reactive:creep-tenuous-desktop-reactive-models")?.name =
    "creep-tenuous-desktop-reactive-models"
findProject(":creep-tenuous-desktop-reactive:creep-tenuous-desktop-reactive-triggers")?.name =
    "creep-tenuous-desktop-reactive-triggers"

rootProject.name = "CreepTenuousDesktop"
