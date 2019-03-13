package com.fox.one.support.common.utils

import android.graphics.Bitmap
import android.text.TextUtils
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * Created by zhangyinghao on 2018/4/16.
 */
object StringUtils {
    private val CACHE_SIZE = 4096

    var mRegex = "[0-9]*"


    /**
     * is null or its length is 0
     *
     *
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
    </pre> *
     *
     * @param str
     * @return if string is null or its size is 0, return true, else return false.
     */
    fun isEmpty(str: String?): Boolean {
        return str == null || str.length == 0
    }

    /**
     * encoded in utf-8
     *
     *
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
    </pre> *
     *
     * @param str
     * @return
     */
    fun utf8Encode(str: String): String? {
        if (!isEmpty(str) && str.toByteArray().size != str.length) {
            try {
                return URLEncoder.encode(str, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                throw RuntimeException("UnsupportedEncodingException occurred. ", e)
            }

        }
        return str
    }

    /**
     * encoded in utf-8, if exception, return defultReturn
     *
     * @param str
     * @param defultReturn
     * @return
     */
    fun utf8Encode(str: String, defultReturn: String): String? {
        if (!isEmpty(str) && str.toByteArray().size != str.length) {
            try {
                return URLEncoder.encode(str, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                return defultReturn
            }

        }
        return str
    }

    fun ip2String(ip: Int): String {
        return ((ip shr 24 and 0xFF).toString() + "." + (ip shr 16 and 0xFF) + "."
                + (ip shr 8 and 0xFF) + "." + (ip and 0xFF))
    }

    /**
     * InputSteam 转换到 String，会把输入流关闭
     *
     * @param inputStream 输入流
     * @return String 如果有异常则返回null
     */
    fun fromInputStream(inputStream: InputStream): String? {
        try {
            val readBuffer = ByteArray(CACHE_SIZE)
            val byteArrayOutputStream = ByteArrayOutputStream()
            while (true) {
                val readLen = inputStream.read(readBuffer, 0, CACHE_SIZE)
                if (readLen <= 0) {
                    break
                }

                byteArrayOutputStream.write(readBuffer, 0, readLen)
            }

            return byteArrayOutputStream.toString("UTF-8")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return null
    }

    fun getStackStr(error: Throwable): String {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        error.printStackTrace(pw)
        val str = sw.toString()
        pw.close()
        return str
    }

    fun isNumber(str: String?): Boolean {
        if (str == null || str == "") return false
        val chars = str.toCharArray()
        var hasDot = false
        for (i in chars.indices) {//检视每个字符
            if (!chars[i].toString().matches(mRegex.toRegex())) {//如果不是数字
                if (chars[i] == '.') {//看看是不是 .
                    if (!hasDot) {//如果字符串中没有 .
                        hasDot = true//是小数
                    } else {
                        return false// 多个. 不是数字
                    }
                } else {
                    return false// 不是.是其他字符，不是数字
                }
            }
        }
        return true// 通过检查 是数字
    }

    fun blurPhoneNumber(tel: String?): String? {
        if (tel == null) return null
        val length = tel.length
        val sb = StringBuilder()
        val array = tel.toCharArray()
        for (i in 0 until length) {
            if (i < length - 4 && i >= length - 8) {
                sb.append("*")
            } else {
                sb.append(array[i])
            }
        }
        if (tel.contains(" ")) sb.insert(0, "+")
        return sb.toString()
    }
}

fun String.toMD5(): String {
    return SecurityUtils.MD5.get32MD5String(this)
}

fun String.toMD5short(): String {
    return SecurityUtils.MD5.get16MD5String(this)
}

fun String.generateQRCode(size: Int, offsetRatio: Float = 0.1f): Bitmap? {
    val result: BitMatrix
    try {
        val hints = HashMap<EncodeHintType, Any>()
        hints[EncodeHintType.CHARACTER_SET] = "utf-8"
        hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
        hints[EncodeHintType.MARGIN] = 0

        val minWidth = DisplayUtils.dp2px(100.0f).toInt()
        var width = (size shr 1)
        width = if (width < minWidth) minWidth else width

        result = MultiFormatWriter().encode(this, BarcodeFormat.QR_CODE, width, width, hints)
    } catch (iae: IllegalArgumentException) {
        // Unsupported format
        return null
    }

    val width = result.width
    val newWidth = width
    val height = result.height
    val newHeight = height
    val pixels = IntArray(newWidth * newHeight)
    for (y in 0 until newHeight) {
        val offset = y * newWidth
        for (x in 0 until newWidth) {
            pixels[offset + x] = if (result.get(x, y)) {
                -0x1000000 // black
            } else {
                -0x1 // white
            }
        }
    }
    val bitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.RGB_565)
    bitmap.setPixels(pixels, 0, newWidth, 0, 0, newWidth, newHeight)
    return bitmap
}

fun String.maskMiddle(startOffset: Int, endOffset: Int, mask: String): String {
    if (TextUtils.isEmpty(this)) {
        return this
    }

    if (length < startOffset || length < endOffset) {
        return this
    }

    if (length < (startOffset + endOffset)) {
        return "$mask${substring(length - endOffset)}"
    }

    val start = substring(0, startOffset)
    val end = substring(length - endOffset)
    return "$start$mask$end"
}

