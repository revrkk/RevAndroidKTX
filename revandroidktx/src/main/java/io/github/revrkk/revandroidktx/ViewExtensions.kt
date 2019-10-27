package io.github.revrkk.revandroidktx

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Spinner
import android.widget.ToggleButton
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar

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

infix fun View?.onClick(function: (v: View) -> Unit) {
    this?.setOnClickListener { function(this) }
}

infix fun View.onFocus(function: (v: View) -> Unit) {
    setOnFocusChangeListener { v, hasFocus -> if (hasFocus) function(v) }
}

infix fun View.offFocus(function: (v: View) -> Unit) {
    setOnFocusChangeListener { v, hasFocus -> if (!hasFocus) function(v) }
}

infix fun ToggleButton.onChange(function: (CompoundButton, Boolean) -> Unit) {
    setOnCheckedChangeListener(function)
}

infix fun SwipeRefreshLayout.onRefresh(function: () -> Unit) {
    setOnRefreshListener(function)
}

infix fun EditText.onTextChange(f: (typedString: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = f(s.toString())
    })
}


fun View?.showShortSnack(msg: String) {
    if (this != null)
        Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()
}

fun View?.showLongSnack(msg: String) {
    if (this != null)
        Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()
}

