package com.milwaukee.weather.landing.widgets

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.TextView
import com.milwaukee.waether.baseui.RecyclerGenericView
import com.milwaukee.waether.baseui.RecyclerItem
import com.milwaukee.weather.base.places.models.Place
import com.milwaukee.weather.landing.R

class PlaceItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerGenericView<RecyclerItem<Place>> {

    private lateinit var itemId: String

    private val textViewName by lazy { findViewById<TextView>(R.id.textView_name) }
    private val textViewDescription by lazy { findViewById<TextView>(R.id.textView_description) }

    init {
        inflate(context, R.layout.place_item, this)
    }

    override fun bind(item: RecyclerItem<Place>, position: Int) {
        val place = item.getContent()
        itemId = place.id
        textViewName.text = place.text
        textViewDescription.text = place.description
    }

    override fun setListener(listener: (String) -> Unit) {
        setOnClickListener { listener.invoke(itemId) }
    }

}