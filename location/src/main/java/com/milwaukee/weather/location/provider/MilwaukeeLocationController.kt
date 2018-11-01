package com.milwaukee.weather.location.provider

import android.Manifest
import android.support.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.milwaukee.weather.base.location.LocationController
import com.milwaukee.weather.base.model.Location
import com.milwaukee.weather.location.exceptions.NoLocationAvailableException
import io.reactivex.Single
import io.reactivex.SingleEmitter
import javax.inject.Inject
import android.location.Location as AndroidLocation

class MilwaukeeLocationController @Inject constructor(
    private val client: FusedLocationProviderClient
) : LocationController {

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun getLocation(): Single<Location> {
        return Single.create { emitter ->
            client.lastLocation
                .addOnSuccessListener { androidLocation ->
                    emitter.processAndroidLocation(androidLocation)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }

        }
    }

    private fun SingleEmitter<Location>.processAndroidLocation(androidLocation: AndroidLocation?) {
        if (androidLocation != null) {
            onSuccess(Location(androidLocation.latitude, androidLocation.longitude))
        } else {
            onError(NoLocationAvailableException())
        }
    }
}