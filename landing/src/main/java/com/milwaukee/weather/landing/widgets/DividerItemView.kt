package com.milwaukee.weather.landing.widgets

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.milwaukee.waether.baseui.RecyclerGenericView
import com.milwaukee.waether.baseui.RecyclerItem
import com.milwaukee.weather.landing.R

class DividerItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), RecyclerGenericView<RecyclerItem<Any>> {

    init {
        layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            context.resources.getDimensionPixelSize(R.dimen.spacing_xmicro))
        setBackgroundColor(ContextCompat.getColor(context, R.color.gray600))
    }

    override fun bind(item: RecyclerItem<Any>, position: Int): Unit = Unit
    override fun setListener(listener: (String) -> Unit): Unit = Unit

}