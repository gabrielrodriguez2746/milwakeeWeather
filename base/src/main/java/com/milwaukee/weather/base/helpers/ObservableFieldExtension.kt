package com.milwaukee.weather.base.helpers

import android.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.Observable.create
import android.databinding.Observable as DataBindingObservable

fun <T> ObservableField<T>.toObservable(): Observable<T> {
    return create { e ->
        val initialValue = get()
        initialValue?.let {
            e.onNext(initialValue)
        }
        val callback = object : DataBindingObservable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: DataBindingObservable, i: Int) {
                get()?.let { e.onNext(it) }
            }
        }
        addOnPropertyChangedCallback(callback)
        e.setCancellable { removeOnPropertyChangedCallback(callback) }
    }
}