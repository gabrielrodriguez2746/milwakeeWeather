package com.milwaukee.weather.landing.bindings

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.milwaukee.weather.base.helpers.addRippleForeground
import com.milwaukee.weather.base.helpers.removeForeground
import io.reactivex.subjects.PublishSubject

@BindingConversion
fun booleanToVisibility(visibility: Boolean): Int {
    return if (visibility) View.VISIBLE else View.GONE
}

@BindingAdapter("on_view_clicked")
fun View.setOnPublishClickListener(listener: PublishSubject<Int>) {
    setOnClickListener {
        listener.onNext(id)
    }
}

@BindingAdapter("on_ripple_change")
fun View.toggleRippleForeGround(haveRipple: Boolean) {
    if (haveRipple) {
        addRippleForeground()
    } else {
        removeForeground()
    }
}

@BindingAdapter("on_search_enable")
fun View.onSearchEnable(isSearchEnable: Boolean) {
    isFocusable = isSearchEnable
    isFocusableInTouchMode = isSearchEnable
    if (isSearchEnable) {
        requestFocus()
        showKeyBoard()
    } else {
        clearFocus()
    }
}


@BindingAdapter("on_icon_change")
fun ImageView.onLeftIconSearchEnable(@DrawableRes icon: Int) {
    setImageDrawable(ContextCompat.getDrawable(context, icon))
}

@BindingAdapter(value = [ "on_hide_keyboard", "on_notify_keyboard"], requireAll = false)
fun View.hideKeyBoardOnTouchListener(
    shouldHideKeyboard: Boolean,
    subject: PublishSubject<Unit>?
) {
    if (shouldHideKeyboard) {
        setOnTouchListener { v, _ ->
            v.hideKeyBoard()
            subject?.onNext(Unit)
            false
        }
    }
}

private fun View.showKeyBoard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        showSoftInput(this@showKeyBoard, InputMethodManager.SHOW_IMPLICIT)
    }
}

private fun View.hideKeyBoard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(windowToken, 0)
    }
}



