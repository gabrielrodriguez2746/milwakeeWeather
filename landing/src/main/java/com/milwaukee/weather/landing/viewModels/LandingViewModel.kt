package com.milwaukee.weather.landing.viewModels

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.milwaukee.weather.base.helpers.applySchedulers
import com.milwaukee.weather.base.helpers.disposeWithoutFear
import com.milwaukee.weather.base.helpers.toObservable
import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.base.places.models.Place
import com.milwaukee.weather.base.places.models.SinglePlace
import com.milwaukee.weather.base.providers.ResourceProvider
import com.milwaukee.weather.base.weather.models.SingleWeather
import com.milwaukee.weather.landing.R
import com.milwaukee.weather.landing.controllers.LandingController
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LandingViewModel @Inject constructor(
    private val controller: LandingController,
    private val provider: ResourceProvider
) : LifecycleObserver {

    private var lastHighlightPlace = ""

    val itemsClickedSubject = PublishSubject.create<Int>()

    val hideKeyboardSubject = PublishSubject.create<Unit>()

    val querySearched = ObservableField(lastHighlightPlace)
    val queryHint = ObservableField("")
    val places = ObservableField(listOf<Place>())

    val searchEnable = ObservableBoolean()
    val showEndIcon = ObservableBoolean(true)

    val startDrawable = ObservableInt(R.drawable.ic_menu)
    val endDrawable = ObservableInt(R.drawable.ic_search)

    private var startDisposable: Disposable? = null
    private val actionsDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        startDisposable = controller
            .getUserLocation()
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap(::processPLaceFromLocation)
            .applySchedulers()
            .subscribe({ print("place ${it.first}, weather ${it.second}") }, Throwable::printStackTrace)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        subscribeToSearchClicks()
        subscribeToHideKeyBoard()
        subscribeToQueryChanges()
        subscribeToEndButton()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        actionsDisposable.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        startDisposable?.disposeWithoutFear()
    }

    private fun subscribeToSearchClicks() {
        actionsDisposable += itemsClickedSubject.hide()
            .filter { it == R.id.editText_search && !searchEnable.get() }
            .observeOn(Schedulers.computation())
            .debounce(300, TimeUnit.MILLISECONDS)
            .doOnNext {
                searchEnable.set(true)
                startDrawable.set(R.drawable.ic_back)
                endDrawable.set(R.drawable.ic_close_grey)
                queryHint.set(provider.getString(R.string.landing_search_hint))
            }
            .applySchedulers()
            .subscribe {
                querySearched.set("")
                showEndIcon.set(false)
            }
    }

    private fun subscribeToHideKeyBoard() {
        actionsDisposable += hideKeyboardSubject.hide()
            .doOnNext {
                searchEnable.set(false)
                startDrawable.set(R.drawable.ic_menu)
                endDrawable.set(R.drawable.ic_search)
                queryHint.set("")
            }
            .applySchedulers()
            .subscribe { querySearched.set(lastHighlightPlace) }
    }

    private fun subscribeToQueryChanges() {
        actionsDisposable += getQueryObservable()
            .doOnNext { showEndIcon.set(true) }
            .applySchedulers()
            .subscribe(::processQueryRequest)
    }

    private fun subscribeToEndButton() {
        actionsDisposable += itemsClickedSubject.hide()
            .filter { it == R.id.imageView_end && searchEnable.get() && showEndIcon.get() }
            .doOnNext { querySearched.set("") }
            .applySchedulers()
            .subscribe { showEndIcon.set(false) }
    }

    private fun getQueryObservable(): Observable<String> {
        return querySearched.toObservable()
            .map { it.trim() }
            .observeOn(Schedulers.computation())
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { it.isNotBlank() }
            .distinctUntilChanged()
    }

    private fun processQueryRequest(query: String) {
        actionsDisposable += createSingleRequest(query)
            .applySchedulers()
            .subscribe({}, Throwable::printStackTrace)
    }

    private fun createSingleRequest(query: String): Single<List<Place>> {
        return controller.getPlacesAutocomplete(query)

    }

    private fun processPLaceFromLocation(location: Location): Single<Pair<SinglePlace, SingleWeather>>? {
        return Single.zip(controller.getPlaceDataByCoordinates(location).applySchedulers()
            .doOnSuccess {
                with(it.text) {
                    lastHighlightPlace = this
                    querySearched.set(this)
                }
            },
            controller.getWeatherByLocation(location).applySchedulers(), BiFunction<SinglePlace, SingleWeather,
                    Pair<SinglePlace, SingleWeather>> { place, weather -> Pair(place, weather) })
    }

}