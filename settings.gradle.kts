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
    "creep-tenuous-desktop-app"
)

rootProject.name = "CreepTenuousDesktop"
