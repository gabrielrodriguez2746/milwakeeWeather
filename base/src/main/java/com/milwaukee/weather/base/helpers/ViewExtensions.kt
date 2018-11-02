package com.milwaukee.weather.base.helpers

import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View

fun View.addRippleForeground() {
    if (isMarshmallow()) {
        val outValue = TypedValue()
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackgroundBorderless, outValue, true
        )
        foreground = ContextCompat.getDrawable(context, outValue.resourceId)
    }
}

fun View.removeForeground() {
    if (isMarshmallow()) {
        foreground = null
    }
}