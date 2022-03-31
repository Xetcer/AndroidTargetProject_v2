package xetzer.targetproject_v2

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import kotlin.math.roundToInt

class PictureUtils {
    fun getScaledBitmap(path: String, activity: Activity): Bitmap {
        val size = Point()
        activity.windowManager.defaultDisplay.getSize(size)
        return getScaledBitmap(path, size.x, size.y)
    }

    fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {
        //чтение размеров изображения на диске
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)

        val srcWidth = options.outWidth.toFloat()
        val srcHeight = options.outHeight.toFloat()

        // выясняем на сколько нужно уменьшить фотографию
        var inSampleSize = 1
        if (srcHeight > destHeight || srcWidth > destWidth) {
            val heightScale = srcHeight / destHeight
            val widthScale = srcWidth / destWidth

            val sampleScale = if (heightScale > widthScale) {
                heightScale
            } else {
                widthScale
            }
            inSampleSize = sampleScale.roundToInt()
        }

        options = BitmapFactory.Options()
        options.inSampleSize = inSampleSize
        // чтение и создание окончательного растрового изображения
        return BitmapFactory.decodeFile(path, options)
    }

}