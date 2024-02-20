package com.zer0s2m.creeptenuous.desktop.common.enums

enum class Config(val path: String) {

    PATH_DIRECTORY_CONFIG(System.getProperty("user.home") + "/.config/CreepTenuous"),

    PATH_CONFIG_STATE(PATH_DIRECTORY_CONFIG.path + "/state-desktop-db.json")

}
