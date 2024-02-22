package com.zer0s2m.creeptenuous.desktop.common.utils

import com.zer0s2m.creeptenuous.desktop.common.dto.ConfigState
import com.zer0s2m.creeptenuous.desktop.common.enums.Config
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Path

/**
 * Load configuration for state.
 */
fun loadStorageConfigStateDesktop(): ConfigState {
    checkExistsDirectoryConfig()
    checkExistsFileDBDTO(Config.PATH_CONFIG_STATE.path)

    val data: String = File(Config.PATH_CONFIG_STATE.path)
        .inputStream()
        .readBytes()
        .toString(Charsets.UTF_8)

    return Json.decodeFromString(ConfigState.serializer(), data)
}

fun saveStorageConfigStateDesktop(data: ConfigState) {
    PrintWriter(FileWriter(Config.PATH_CONFIG_STATE.path)).use {
        it.write(Json.encodeToJsonElement(data).toString())
    }
}

fun createDownloadFolder() {
    checkExistsDownloadDirectory()
    checkExistsDownloadDirectoryMain()
}

/**
 * Check for the existence of the configuration area.
 */
private fun checkExistsDirectoryConfig() {
    val pathDirectoryConfig: Path = Path.of(Config.PATH_DIRECTORY_CONFIG.path)
    if (!Files.exists(pathDirectoryConfig)) {
        Files.createDirectory(pathDirectoryConfig)
    }
}

/**
 * Check for the existence of the download directory.
 */
private fun checkExistsDownloadDirectory() {
    val pathDirectoryConfig: Path = Path.of(Config.PATH_DIRECTORY_DOWNLOAD.path)
    if (!Files.exists(pathDirectoryConfig)) {
        Files.createDirectory(pathDirectoryConfig)
    }
}

/**
 * Check for the existence of the download directory.
 */
private fun checkExistsDownloadDirectoryMain() {
    val pathDirectoryConfig: Path = Path.of(Config.PATH_DIRECTORY_DOWNLOAD_MAIN.path)
    if (!Files.exists(pathDirectoryConfig)) {
        Files.createDirectory(pathDirectoryConfig)
    }
}

/**
 * Check the configuration file for its existence.
 *
 * If the file is missing, it will be created.
 *
 * @param path Path to configuration file.
 */
private fun checkExistsFileDBDTO(path: String) {
    val pathDBFile: Path = Path.of(path)
    if (!Files.exists(pathDBFile)) {
        Files.createFile(pathDBFile)
        saveStorageConfigStateDesktop(ConfigState(
            login = "",
            password = "",
            accessToken = null,
            refreshToken = null
        ))
    }
}
