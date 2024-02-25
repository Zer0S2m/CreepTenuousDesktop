pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(
    "common",
    "core",
    "ui:components",
    "ui:screens",
    "app",
    "reactive:actions",
    "reactive:models",
    "reactive:handlers",
    "reactive:triggers",
    "extended:navigation",
    "extended:core-navigation",
    "extended:core-utils"
)

rootProject.name = "CreepTenuousDesktop"
