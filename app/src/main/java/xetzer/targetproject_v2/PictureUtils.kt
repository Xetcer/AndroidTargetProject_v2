package xetzer.targetproject_v2

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Point
import android.media.ExifInterface
import java.io.File
import kotlin.math.roundToInt

class PictureUtils {
    fun getScaledBitmap(path: String, activity: Activity): Bitmap {
        val size = Point()
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R){
//            val display = activity.display
//            val bounds = activity.windowManager.currentWindowMetrics.bounds
//        }else{
//            @Suppress("DEPRECATION")
        activity.windowManager.defaultDisplay.getSize(size)
//        }
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
        return rotateImageIfRequire(BitmapFactory.decodeFile(path, options), path)
    }

    // Работа с камерой:
    // По какой-то причине после того как происходит фотографирование, фотография поворачивается на 90 градусов.
    // Чтобы это исправить нужно написать функцию по проверке положения фотографии.


    private fun rotateImageIfRequire(img: Bitmap, path: String): Bitmap {
        val file = File(path)
        val ei = ExifInterface(file.path)
        return when (ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                rotateImage(img, 90)
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                rotateImage(img, 180)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                rotateImage(img, 270)
            }
            else -> img
        }
    }

    private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedImage = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        img.recycle()
        return rotatedImage
    }

}