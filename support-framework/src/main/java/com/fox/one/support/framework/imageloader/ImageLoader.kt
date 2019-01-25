package  com.fox.one.support.framework.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.fox.one.support.framework.delegate.IDelegate

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-19
 */
object ImageLoader: IDelegate<IImageLoader>, IImageLoader {
    override fun getDefaultPlaceHolder(): Int {
        return imageLoaderImpl.getDefaultPlaceHolder()
    }

    override fun display(imageView: ImageView, uri: String) {
        imageLoaderImpl.display(imageView, uri)
    }

    override fun display(imageView: ImageView, uri: String, placeHolder: Int) {
        imageLoaderImpl.display(imageView, uri, placeHolder)
    }

    override fun <D> display(imageView: ImageView, uri: String, requestOptions: D) {
        imageLoaderImpl.display(imageView, uri, requestOptions)
    }

    override fun <D> display(imageView: ImageView, uri: String, placeHolder: Int, requestOptions: D) {
        imageLoaderImpl.display(imageView, uri, placeHolder, requestOptions)
    }

    override fun display(imageView: ImageView, uri: Uri) {
        imageLoaderImpl?.display(imageView, uri)
    }

    override fun display(imageView: ImageView, uri: Uri, placeHolder: Int) {
        imageLoaderImpl.display(imageView, uri, placeHolder)
    }

    override fun display(imageView: ImageView, resId: Int) {
        imageLoaderImpl.display(imageView, resId)
    }

    override fun display(imageView: ImageView, resId: Int, placeHolder: Int) {
        imageLoaderImpl.display(imageView, resId, placeHolder)
    }

    override fun display(imageView: ImageView, bitmap: Bitmap) {
        imageLoaderImpl.display(imageView, bitmap)
    }

    override fun display(imageView: ImageView, bitmap: Bitmap, placeHolder: Int) {
        imageLoaderImpl.display(imageView, bitmap, placeHolder)
    }

    override fun load(context: Context, uri: String, requestListener: ImageRequestListener?) {
        imageLoaderImpl.load(context, uri, requestListener)
    }

    override fun setDelegable(obj: IImageLoader) {
        imageLoaderImpl = obj
    }

    private lateinit var imageLoaderImpl: IImageLoader
}