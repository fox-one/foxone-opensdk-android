package com.fox.one.support.common.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.fox.one.support.common.extension.closeSilently
import com.fox.one.support.common.extension.getCacheFile
import java.io.*
import kotlin.math.max

/**
 * Created by zhangyinghao on 2018/6/27.
 */

object ImageUtils {

    const val ERROR_SMALL_SIZE = -1
    const val ERROR_UNKNOWN = -2
    const val SUCCESS = 0

    fun resize(inPath: String, minSize: Int, maxSize: Int, maxBytes: Int, targetPath: String): Int {
        try {
            var ops = BitmapFactory.Options()
            ops.inJustDecodeBounds = true

            BitmapFactory.decodeFile(inPath, ops)

            var width = ops.outWidth
            var height = ops.outHeight
            var size = width * height

            if (size < (minSize * minSize)) {
                return ERROR_SMALL_SIZE
            }

            ops.inJustDecodeBounds = false

            var mSize = maxSize * maxSize
            if (size > mSize) {
                ops.inSampleSize = Math.ceil(Math.sqrt((size / mSize).toDouble())).toInt()

                if (ops.inSampleSize <= 0) {
                    ops.inSampleSize = 1
                }
            }

            val bitmap = BitmapFactory.decodeFile(inPath, ops)

            var bitmapSize = getBitmapSize(bitmap)

            var compressFactor = 70

            var result: ByteArray

            do {
                var output = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressFactor, output)
                result = output.toByteArray()
                bitmapSize = result.size

                output.closeSilently()

                compressFactor -= 10
                if (compressFactor < 10) {
                    break
                }
            } while (bitmapSize > maxBytes)

            var outputStream = BufferedOutputStream(FileOutputStream(targetPath))
            outputStream.write(result)
            outputStream.flush()
            outputStream.close()

            bitmap.recycle()
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.i("liuxiaoming", "${e.toString()}")
            return ERROR_UNKNOWN
        }

        return SUCCESS
    }

    fun resize(file: File, minSize: Int, maxSize: Int, maxBytes: Int, targetPath: String) {
       resize(file.absolutePath, minSize, maxSize, maxBytes, targetPath)
    }

    fun getBitmapSize(bitmap: Bitmap?): Int {
        if (bitmap == null) {
            return 0
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {     //API 19
            return bitmap.allocationByteCount
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            bitmap.byteCount
        } else bitmap.rowBytes * bitmap.height
        //earlier version
    }
}

fun Bitmap.saveToLocal(ctx: Context, name: String, block: () -> Unit = { }) {
    val timeTaken = System.currentTimeMillis()
    val dateSeconds = timeTaken / 1000
    val values = ContentValueBuilder.create()
            .put(MediaStore.Images.ImageColumns.TITLE, name)
            .put(MediaStore.Images.ImageColumns.DISPLAY_NAME, name)
            .put(MediaStore.Images.ImageColumns.DESCRIPTION, "")
            .put(MediaStore.Images.ImageColumns.MIME_TYPE, MimeTypes.Image.JPEG)
            .put(MediaStore.Images.ImageColumns.DATE_TAKEN, timeTaken)
            .put(MediaStore.Images.ImageColumns.DATE_ADDED, dateSeconds)
            .put(MediaStore.Images.ImageColumns.DATE_MODIFIED, dateSeconds)
    if (width != 0) values.put(MediaStore.Images.ImageColumns.WIDTH, width)
    if (height != 0) values.put(MediaStore.Images.ImageColumns.HEIGHT, height)
    val uri = ctx.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values.build())
    if (uri != null) {
        val bos = ctx.contentResolver.openOutputStream(uri)
        compress(Bitmap.CompressFormat.JPEG, 85, bos)
        bos.closeSilently()
        ctx.contentResolver.update(uri, values.build(), null, null)
        block()
    }
}

fun Context.scanAlbum(fileName: String, file: File, width: Int = 0, height: Int = 0, block: (String, Uri) -> Unit = { path, uri -> }) {
    grantUriPermission(packageName, Uri.fromFile(file), Intent.FLAG_GRANT_READ_URI_PERMISSION)
    MediaStore.Images.Media.insertImage(contentResolver, file.canonicalPath, "", "")
    MediaScannerConnection.scanFile(this, arrayOf(fileName), arrayOf("image/*"), block)
}

fun Bitmap.createFile(context: Context): File {
    val file = context.getCacheFile()
    val bos = FileOutputStream(file)
    compress(Bitmap.CompressFormat.JPEG, 85, bos)
    bos.closeSilently()
    return file
}
