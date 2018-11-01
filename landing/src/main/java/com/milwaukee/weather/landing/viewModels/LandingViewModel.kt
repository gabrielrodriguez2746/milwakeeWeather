package com.milwaukee.weather.landing.viewModels

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.ObservableField
import com.milwaukee.weather.base.helpers.applySchedulers
import com.milwaukee.weather.base.helpers.disposeWithoutFear
import com.milwaukee.weather.base.places.models.SinglePlace
import com.milwaukee.weather.base.weather.models.SingleWeather
import com.milwaukee.weather.landing.controllers.LandingController
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LandingViewModel @Inject constructor(
    private val controller: LandingController
) : LifecycleObserver {

    val itemsClickedSubject = PublishSubject.create<Int>()
    val querySearched = ObservableField("")

    private var startDisposable : Disposable? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        startDisposable = controller
            .getUserLocation()
            .flatMap { location ->
                Single.zip(controller.getPlaceDataByCoordinates(location).applySchedulers(),
                    controller.getWeatherByLocation(location).applySchedulers(), BiFunction<SinglePlace, SingleWeather,
                            Pair<SinglePlace, SingleWeather>> { place, weather -> Pair(place, weather) })
            }
            .applySchedulers()
            .subscribe({ print("place ${it.first}, weather ${it.second}") }, Throwable::printStackTrace)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        startDisposable?.disposeWithoutFear()
    }

}