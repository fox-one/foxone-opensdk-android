package com.fox.one.support

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.fox.one.support.GlideApp
import com.fox.one.support.framework.imageloader.DataSourceFrom
import com.fox.one.support.framework.imageloader.IImageLoader
import com.fox.one.support.framework.imageloader.ImageRequestListener

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-19
 */
object ImageLoaderImpl: IImageLoader {
    override fun getDefaultPlaceHolder(): Int {
        return 0
    }

    override fun display(imageView: ImageView, uri: String) {
        GlideApp.with(imageView)
            .load(uri)
            .placeholder(getDefaultPlaceHolder())
            .into(imageView)
    }

    override fun display(imageView: ImageView, uri: String, placeHolder: Int) {
        GlideApp.with(imageView)
            .load(uri)
            .placeholder(placeHolder)
            .into(imageView)
    }

    override fun <D> display(imageView: ImageView, uri: String, requestOptions: D) {
        if (requestOptions is RequestOptions) {
            GlideApp.with(imageView)
                .load(uri)
                .apply(requestOptions)
                .placeholder(getDefaultPlaceHolder())
                .into(imageView)
        }
    }

    override fun <D> display(imageView: ImageView, uri: String, placeHolder: Int, requestOptions: D) {
        if (requestOptions is RequestOptions) {
            GlideApp.with(imageView)
                .load(uri)
                .apply(requestOptions)
                .placeholder(placeHolder)
                .into(imageView)
        }
    }

    override fun display(imageView: ImageView, uri: Uri) {
        GlideApp.with(imageView)
            .load(uri)
            .placeholder(getDefaultPlaceHolder())
            .into(imageView)
    }

    override fun display(imageView: ImageView, uri: Uri, placeHolder: Int) {
        GlideApp.with(imageView)
            .load(uri)
            .placeholder(placeHolder)
            .into(imageView)
    }

    override fun display(imageView: ImageView, resId: Int) {
        GlideApp.with(imageView)
            .load(resId)
            .into(imageView)
    }

    override fun display(imageView: ImageView, resId: Int, placeHolder: Int) {
        GlideApp.with(imageView)
            .load(resId)
            .placeholder(getDefaultPlaceHolder())
            .into(imageView)
    }

    override fun display(imageView: ImageView, bitmap: Bitmap) {
        GlideApp.with(imageView)
            .load(bitmap)
            .into(imageView)
    }

    override fun display(imageView: ImageView, bitmap: Bitmap, placeHolder: Int) {
        GlideApp.with(imageView)
            .load(bitmap)
            .placeholder(getDefaultPlaceHolder())
            .into(imageView)
    }

    @SuppressLint("CheckResult")
    override fun load(context: Context, uri: String, requestListener: ImageRequestListener?) {
        GlideApp.with(context)
            .load(uri)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                    return requestListener?.onLoadFailed(e, model, isFirstResource) ?: false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    return requestListener?.onResourceReady(resource, model,
                        convert(dataSource), isFirstResource) ?: false
                }
            })
    }

    private fun convert(dataSource: DataSource): DataSourceFrom {
        return when(dataSource) {
            DataSource.LOCAL -> DataSourceFrom.LOCAL
            DataSource.REMOTE -> DataSourceFrom.REMOTE
            DataSource.DATA_DISK_CACHE -> DataSourceFrom.DATA_DISK_CACHE
            DataSource.MEMORY_CACHE -> DataSourceFrom.MEMORY_CACHE
            DataSource.RESOURCE_DISK_CACHE -> DataSourceFrom.RESOURCE_DISK_CACHE
        }
    }
}