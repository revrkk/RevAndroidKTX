package io.github.revrkk.revandroidktx

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.util.*


fun Context?.showShortToast(msg: Int) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
fun Context?.showShortToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
fun Context?.showLongToast(msg: Int) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
fun Context?.showLongToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
fun Context?.hideKeyboard(v: View?) {
    (this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run {
        hideSoftInputFromWindow(v?.windowToken, 0)
    }
}
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