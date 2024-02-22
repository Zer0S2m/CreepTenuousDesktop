package com.zer0s2m.creeptenuous.desktop.common.enums

/**
 * System configuration.
 *
 * @param path Path to configuration files and directories.
 */
enum class Config(val path: String) {

    PATH_DIRECTORY_CONFIG(System.getProperty("user.home") + "/.config/CreepTenuous"),

    PATH_DIRECTORY_DOWNLOAD(System.getProperty("user.home") + "/Downloads"),

    PATH_DIRECTORY_DOWNLOAD_MAIN(PATH_DIRECTORY_DOWNLOAD.path + "/CreepTenuous"),

    PATH_CONFIG_STATE(PATH_DIRECTORY_CONFIG.path + "/state-desktop-db.json")

}
