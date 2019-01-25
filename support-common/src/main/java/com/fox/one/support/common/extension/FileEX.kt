package com.fox.one.support.common.extension

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import java.io.Closeable
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * Some parts copy from MixinMessenger
 * Created by zhangyinghao on 2018/7/2.
 */


fun File.createImageTemp(prefix: String? = null, type: String? = null): File {
    val time = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    return if (prefix != null) {
        createTempFile("${prefix}_IMAGE_$time", type ?: ".jpg")
    } else {
        createTempFile("IMAGE_$time", type ?: ".jpg")
    }
}


fun File.createTempFile(name: String, type: String): File {
    return createTempFile(name, type, absoluteFile)
}

fun String.getFilePath(context: Context): String? = Uri.parse(this).getFilePath(context)

fun Uri.getFilePath(context: Context): String? {
    val scheme = this.scheme
    var data: String? = null
    if (scheme == null)
        data = this.toString()
    else if (ContentResolver.SCHEME_FILE == scheme) {
        data = this.path
    } else if (ContentResolver.SCHEME_CONTENT == scheme) {
        val cursor = context.contentResolver.query(this, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                if (index > -1) {
                    data = cursor.getString(index)
                    if (data == null) {
                        return getImageUrlWithAuthority(context)
                    }
                } else if (index == -1) {
                    return getImageUrlWithAuthority(context)
                }
            }
            cursor.close()
        } else {
            return getImageUrlWithAuthority(context)
        }
    }
    return data
}

fun Uri.getImageUrlWithAuthority(context: Context): String? {
    if (this.authority != null) {
        var input: InputStream? = null
        try {
            input = context.contentResolver.openInputStream(this)
            val mimeTypeMap = MimeTypeMap.getSingleton()
            val type = mimeTypeMap.getExtensionFromMimeType(context.contentResolver.getType(this))
            val outFile = context.getImageCachePath().createImageTemp(type = ".$type")
            outFile.copyFromInputStream(input)
            return outFile.absolutePath
        } catch (ignored: Exception) {
        } finally {
            input?.closeSilently()
        }
    }
    return null
}

fun File.copyFromInputStream(inputStream: InputStream) {
    inputStream.use { input ->
        this.outputStream().use { output ->
            input.copyTo(output)
        }
    }
}

fun Closeable.closeSilently() {
    try {
        close()
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}
