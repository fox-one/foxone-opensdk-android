package  com.fox.one.support.framework.imageloader

import android.graphics.drawable.Drawable
import java.lang.Exception
import javax.sql.DataSource

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-19
 */
interface ImageRequestListener {
    abstract fun onLoadFailed(e: Exception?, model: Any?, isFirstResource: Boolean): Boolean

    abstract fun onResourceReady(resource: Drawable?, model: Any?, dataSource: DataSourceFrom, isFirstResource: Boolean): Boolean
}