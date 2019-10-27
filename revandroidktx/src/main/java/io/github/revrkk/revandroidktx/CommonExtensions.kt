package io.github.revrkk.revandroidktx

import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.Spanned
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*

fun String?.notEquals(param: String?, ignoreCase: Boolean = false): Boolean = !this.equals(param, ignoreCase)

fun Any?.isNull(): Boolean = this == null
fun Any?.isNotNull(): Boolean = this != null
fun String?.isNotNullNorEmpty() = !this.isNullOrEmpty()
fun List<Any>?.isNotNullNorEmpty() = !this.isNullOrEmpty()
fun <E> List<E>.random(): E? = if (size > 0) get(Random().nextInt(size)) else null

fun String.urlEncode(): String = try {
    URLEncoder.encode(this, "UTF-8")
} catch (e: UnsupportedEncodingException) {
    e.printStackTrace()
    ""
}


fun Editable?.toInt() = this.toString().toInt()

infix fun String?.eqi(string2: String?) = this.equals(string2, true)
infix fun String?.nei(string2: String?) = !this.equals(string2, true)

val String?.isY
    get() = this.equals("Y", true)
val String?.isN
    get() = this.equals("N", true)
val String?.isD
    get() = this.equals("D", true)

fun String.asHtml(): Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
} else {
    @Suppress("DEPRECATION")
    Html.fromHtml(this)
}
