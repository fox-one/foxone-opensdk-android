package com.fox.one.support.framework.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.fox.one.support.framework.delegate.Delegable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-19
 */
interface IImageLoader: Delegable {

    fun getDefaultPlaceHolder(): Int

    fun display(imageView: ImageView, uri: String)

    fun display(imageView: ImageView, uri: String, @DrawableRes placeHolder: Int)

    fun <D> display(imageView: ImageView, uri: String, requestOptions: D)

    fun <D> display(imageView: ImageView, uri: String, @DrawableRes placeHolder: Int, requestOptions: D)

    fun display(imageView: ImageView, uri: Uri)

    fun display(imageView: ImageView, uri: Uri, @DrawableRes placeHolder: Int)

    fun display(imageView: ImageView, @DrawableRes resId: Int)

    fun display(imageView: ImageView, @DrawableRes resId: Int, @DrawableRes placeHolder: Int)

    fun display(imageView: ImageView, bitmap: Bitmap)

    fun display(imageView: ImageView, bitmap: Bitmap, @DrawableRes placeHolder: Int)

    fun load(context: Context, uri: String, requestListener: ImageRequestListener?)
}