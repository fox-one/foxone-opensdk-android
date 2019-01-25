package com.fox.one.support.common.utils

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.apache.commons.compress.utils.IOUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-17
 */
object ZipUtils {
    fun ByteArray.unGzip(): ByteArray {
        val input = ByteArrayInputStream(this)
        val gzipInput = GzipCompressorInputStream(input)
        val buffer = ByteArray(1024)
        val output = ByteArrayOutputStream()
        var num = gzipInput.read(buffer, 0, buffer.size)
        while (num != -1) {
            output.write(buffer, 0, num)
            num = gzipInput.read(buffer, 0, buffer.size)
        }
        val byteArray = output.toByteArray()
        output.flush()
        IOUtils.closeQuietly(output)
        IOUtils.closeQuietly(gzipInput)
        IOUtils.closeQuietly(input)
        return byteArray
    }
}