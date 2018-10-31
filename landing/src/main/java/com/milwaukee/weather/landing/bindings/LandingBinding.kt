package com.milwaukee.weather.landing.bindings

import android.databinding.BindingAdapter
import android.databinding.DataBindingComponent
import android.view.View
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@BindingAdapter("on_view_clicked")
fun View.setOnPublishClickListener(listener: PublishSubject<Int>) {
    setOnClickListener {
        listener.onNext(id)
    }
}