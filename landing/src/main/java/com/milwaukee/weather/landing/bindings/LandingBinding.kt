package com.milwaukee.weather.landing.bindings

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.milwaukee.waether.baseui.GenericListAdapter
import com.milwaukee.waether.baseui.RecyclerGenericView
import com.milwaukee.waether.baseui.RecyclerItem
import com.milwaukee.waether.baseui.ViewFactory
import com.milwaukee.weather.base.helpers.addRippleForeground
import com.milwaukee.weather.base.helpers.removeForeground
import com.milwaukee.weather.base.places.models.Place
import com.milwaukee.weather.landing.R
import com.milwaukee.weather.landing.widgets.DividerItemView
import com.milwaukee.weather.landing.widgets.PlaceItemView
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

@BindingAdapter(value = ["on_hide_keyboard", "on_notify_keyboard"], requireAll = false)
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
    } else {

    }
}

@BindingAdapter(value = ["on_configure", "on_listener"], requireAll = false)
fun RecyclerView.onConfigure(
    onConfigure: Boolean,
    listener: PublishSubject<String>?
) {
    if (onConfigure) {
        layoutManager = LinearLayoutManager(context)
        adapter = GenericListAdapter<RecyclerItem<*>, RecyclerGenericView<*>>({
            listener?.onNext(it)
            hideKeyBoard()
        }, object : ViewFactory<RecyclerGenericView<*>>() {
            override fun getView(parent: ViewGroup, viewType: Int): RecyclerGenericView<*> {
                return when (viewType) {
                    1 -> PlaceItemView(parent.context).apply {
                        addRippleForeground()
                        isClickable = true
                        isFocusable = true
                        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    }
                    else -> DividerItemView(parent.context).apply {
                        val margin = parent.context.resources.getDimensionPixelSize(R.dimen.space_medium)
                        with((layoutParams as ViewGroup.MarginLayoutParams)) {
                            leftMargin = margin
                            rightMargin = margin
                        }
                    }
                }
            }
        })
    }
}

@BindingAdapter("on_items")
fun RecyclerView.onItemsChange(
    places: List<Place>
) {
    val items = mutableListOf<RecyclerItem<*>>()
    val lastIndex = places.lastIndex
    places.forEachIndexed { index, place ->
        items.add(object : RecyclerItem<Place> {
            override fun getType(): Int = 1
            override fun getComparator(): Place = place
            override fun getContent(): Place = place
            override fun getId(): Int = place.id.hashCode()
        })
        if (index != lastIndex) {
            items.add(object : RecyclerItem<Any> {
                override fun getComparator(): Any = Any()
                override fun getContent(): Any = Any()
                override fun getId(): Int = View.generateViewId()
                override fun getType(): Int = 0
            })
        }
    }
    (adapter as? GenericListAdapter<RecyclerItem<*>, RecyclerGenericView<*>>)?.submitList(items)
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



