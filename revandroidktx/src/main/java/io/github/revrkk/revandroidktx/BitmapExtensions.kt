package io.github.revrkk.revandroidktx

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import kotlin.math.min
import kotlin.math.roundToInt

fun Bitmap.convertBitmapToByteArray(): ByteArray =
    ByteArrayOutputStream(this.width * this.height).also {
        this.compress(Bitmap.CompressFormat.PNG, 80, it)
    }.toByteArray()

fun Bitmap.scaleDown(maxImageSize: Float, filter: Boolean): Bitmap {
    val ratio = min(maxImageSize / this.width, maxImageSize / this.height)
    val width = (ratio * this.width).roundToInt()
    val height = (ratio * this.height).roundToInt()
    return Bitmap.createScaledBitmap(this, width, height, filter)
}
