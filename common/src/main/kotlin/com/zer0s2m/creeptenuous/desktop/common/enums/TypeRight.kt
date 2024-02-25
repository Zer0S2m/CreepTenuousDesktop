package com.zer0s2m.creeptenuous.desktop.common.enums

/**
 * Storage of all types of rights for interacting with a file object
 *
 * @param title The name of the right to display
 */
enum class TypeRight(val title: String) {

    CREATE(title = "Create"),

    COPY(title = "Copy"),

    DELETE(title = "Delete"),

    MOVE(title = "Move"),

    DOWNLOAD(title = "Download"),

    UPLOAD(title = "Upload"),

    SHOW(title = "Show"),

    RENAME(title = "Rename"),

    ANALYSIS(title = "Show info"),

}