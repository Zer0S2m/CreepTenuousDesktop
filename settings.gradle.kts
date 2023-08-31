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
    "creep-tenuous-desktop-reactive:creep-tenuous-desktop-reactive-triggers",
    "creep-tenuous-desktop-extended",
    "creep-tenuous-desktop-extended:creep-tenuous-desktop-navigation",
    "creep-tenuous-desktop-extended:creep-tenuous-desktop-core-navigation"
)

rootProject.name = "CreepTenuousDesktop"
