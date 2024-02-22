package com.zer0s2m.creeptenuous.desktop.core.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Image
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

/**
 * Load images based on [ImageIO].
 *
 * @param url Image upload URL.
 */
suspend fun loadImage(url: String): Result<ImageBitmap> {
    val urlImage = URL("http://localhost:8080/${url}")
    val urlSplit: List<String> = url.split("/")
    val filename: String = urlSplit.last()
    val extensionFile: String = filename.split(".").last()

    return try {
        val connection = withContext(Dispatchers.IO) {
            urlImage.openConnection()
        } as HttpURLConnection

        withContext(Dispatchers.IO) {
            connection.connect()
        }

        val inputStream = connection.inputStream
        val bufferedImage = withContext(Dispatchers.IO) {
            ImageIO.read(inputStream)
        }

        val stream = ByteArrayOutputStream()
        withContext(Dispatchers.IO) {
            ImageIO.write(bufferedImage, extensionFile, stream)
        }
        val byteArray = stream.toByteArray()

        Result.success(Image.makeFromEncoded(byteArray).toComposeImageBitmap())
    } catch (e: Exception) {
        Result.failure(e)
    }
}
