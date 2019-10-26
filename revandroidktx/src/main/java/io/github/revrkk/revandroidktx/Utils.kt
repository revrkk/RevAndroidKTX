package io.github.revrkk.revandroidktx

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.Spanned
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * Constants
 */
const val YYYY_MM_DD__HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS"
const val DD_MM_YYYY = "dd-MM-yyyy"

/**
 * View Utils
 */
fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View.isVisible() = this.visibility == View.VISIBLE
fun View.toggle() = if (this.isVisible()) this.gone() else this.visible()
fun hide(vararg views: View?, _when: Boolean = true) {
    for (view in views) if (_when) view?.gone() else view?.visible()
}

fun show(vararg views: View?, _when: Boolean = true) {
    for (view in views) if (_when) view?.visible() else view?.gone()
}

fun enable(vararg views: EditText) {
    for (view in views) view.isEnabled = true
}

fun disable(vararg views: EditText) {
    for (view in views) view.isEnabled = false
}

fun View.toggleUpsideDown() = with(this) {
    rotation = if (rotation == 0f) 180f else 0f
}

fun Spinner?.selectValue(value: String) {
    for (i in 0 until this!!.count) {
        if (this.getItemAtPosition(i).toString().toLowerCase() == value.toLowerCase()) {
            this.setSelection(i)
            break
        }
    }
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

fun Context?.showShortToast(msg: Int) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
fun Context?.showShortToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
fun Context?.showLongToast(msg: Int) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
fun Context?.showLongToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
fun Context?.hideKeyboard(v: View?) {
    (this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run {
        hideSoftInputFromWindow(v?.windowToken, 0)
    }
}

fun View?.showShortSnack(msg: String) {
    if (this != null)
        Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()
}

fun View?.showLongSnack(msg: String) {
    if (this != null)
        Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()
}

internal infix fun View?.onClick(function: (v: View) -> Unit) {
    this?.setOnClickListener { function(this) }
}

internal infix fun View.onFocus(function: (v: View) -> Unit) {
    setOnFocusChangeListener { v, hasFocus -> if (hasFocus) function(v) }
}

internal infix fun View.offFocus(function: (v: View) -> Unit) {
    setOnFocusChangeListener { v, hasFocus -> if (!hasFocus) function(v) }
}

internal infix fun ToggleButton.onChange(function: (CompoundButton, Boolean) -> Unit) {
    setOnCheckedChangeListener(function)
}

internal infix fun SwipeRefreshLayout.onRefresh(function: () -> Unit) {
    setOnRefreshListener(function)
}

internal infix fun EditText.onTextChange(f: (typedString: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = f(s.toString())
    })
}

/**
 * Common Utils
 */
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

fun Date.toSimpleDate(): String = SimpleDateFormat(DD_MM_YYYY, Locale.US).format(this)

fun Date.toSimpleDateWithTimeAndMillis(): String = SimpleDateFormat(YYYY_MM_DD__HH_MM_SS_SSS, Locale.US).format(this)

fun Context.changeLang(langCode: String): ContextWrapper {
    var context = this

    val config = context.resources.configuration

    val sysLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        config.locales.get(0)
    } else {
        config.locale
    }
    if (langCode.isNotEmpty() && sysLocale.language != langCode) {
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            config.locale = locale
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context = context.createConfigurationContext(config)
        } else {
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }

    return ContextWrapper(context)
}

fun Date.isOnSameDayAs(vararg dates: Date): Boolean {
    val fmt = SimpleDateFormat("yyyyMMdd", Locale.US)
    val date1 = fmt.format(this)
    for (date in dates)
        if (fmt.format(date) != date1)
            return false

    return true
}