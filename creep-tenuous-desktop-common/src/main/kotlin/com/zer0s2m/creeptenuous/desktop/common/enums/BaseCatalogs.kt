package com.zer0s2m.creeptenuous.desktop.common.enums

/**
 * Personal base directories for each user.
 *
 * @param title Catalog name.
 */
enum class BaseCatalogs(val title: String) {

    VIDEOS(title = "Videos"),

    DOCUMENTS(title = "Documents"),

    IMAGES(title = "Images"),

    MUSICS(title = "Musics");

    companion object {

        /**
         * Get all the names of personal directories.
         *
         * @param Name of personal directories.
         */
        fun list(): Collection<String> {
            return listOf(
                VIDEOS.title,
                DOCUMENTS.title,
                IMAGES.title,
                MUSICS.title
            )
        }

    }

}