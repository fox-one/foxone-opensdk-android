package com.fox.one.support.common.extension

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.v4.content.ContextCompat
import android.support.v4.os.EnvironmentCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.File

/**
 * Created by zhangyinghao on 2018/6/30.
 */
fun View.hideKeyboard() {
    val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    try {
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    } catch (e: Throwable) {
        // do nothing
    }
}

fun Context.getImageCachePath(): File {
    val root = getBestAvailableImageCacheRoot()
    return File("$root${File.separator}Images")
}

fun getBestAvailableImageCacheRoot(): File {
    val roots = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    return roots
}


private fun Context.getBestAvailableCacheRoot(): File {
    val roots = ContextCompat.getExternalCacheDirs(this)
    roots.filter { it != null && Environment.MEDIA_MOUNTED == EnvironmentCompat.getStorageState(it) }
            .forEach { return it }
    return this.cacheDir
}

fun Context.getBestAvailableFilesRoot(): File {
    val roots = ContextCompat.getExternalFilesDirs(this, Environment.MEDIA_MOUNTED)
    roots.filter { it != null && Environment.MEDIA_MOUNTED == EnvironmentCompat.getStorageState(it) }
            .forEach { return it }
    return this.filesDir
}

private fun Context.getAppPath(): File {
    return if (!hasWritePermission(this)) {
        getBestAvailableCacheRoot()
    } else if (isAvailable()) {
        File("${Environment.getExternalStorageDirectory()}${File.separator}FoxOne${File.separator}Media${File.separator}")
    } else {
        var externalFile: Array<File>? = ContextCompat.getExternalFilesDirs(this, null)
        if (externalFile == null) {
            externalFile = arrayOf(this.getExternalFilesDir(null))
        }
        val root = File("${externalFile[0]}${File.separator}FoxOne${File.separator}Media${File.separator}")
        root.mkdirs()
        return if (root.exists()) {
            root
        } else {
            getBestAvailableCacheRoot()
        }
    }
}

private fun isAvailable(): Boolean {
    val state = Environment.getExternalStorageState()
    if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state) {
        return true
    }
    return false
}


private fun hasWritePermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
}

fun Context.getQRCodePath(name: String): File {
    val root = getBestAvailableFilesRoot()
    val file = File("$root${File.separator}$name.jpg")
    if (file.exists()) {
        file.delete()
        file.createNewFile()
    } else {
        file.createNewFile()
    }
    return file
}

fun Context.getCacheFile(): File {
    val root = getImageCachePath()
    root.mkdirs()
    val file = File("$root${File.separator}${System.currentTimeMillis()}.jpg")
    if (file.exists()) {
        file.delete()
        file.createNewFile()
    } else {
        file.createNewFile()
    }
    return file
}


@SuppressLint("MissingPermission")
fun Context.vibrate(milliseconds: Long) {
    if (this.packageManager.checkPermission(Manifest.permission.VIBRATE, this.packageName) == PackageManager.PERMISSION_GRANTED) {
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if (vibrator?.hasVibrator() == true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, 1))
            } else {
                vibrator.vibrate(milliseconds)
            }
        }
    }
}