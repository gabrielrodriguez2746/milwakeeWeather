package com.milwaukee.weather.landing

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.ObservableField
import com.milwaukee.weather.landing.controllers.LandingController
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LandingViewModel @Inject constructor(
    private val controller: LandingController
) : LifecycleObserver {

    val itemsClickedSubject = PublishSubject.create<Int>()
    val querySearched = ObservableField("")

    private val startDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        startDisposable += controller
            .getUserLocation()
            .subscribe()
    }

}