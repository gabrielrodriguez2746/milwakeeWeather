package com.milwaukee.weather.base.permissions

import android.support.annotation.StringRes
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import io.reactivex.Single

/**
 * This a request permissions wrapper to manage it form controller classes
 */
interface PermissionWrapper : ActivityCompat.OnRequestPermissionsResultCallback {

    /**
     * Allow to set the activity bind to the permissions request
     */
    fun initValues(activity: AppCompatActivity)


    /**
     * Returns a boolean indicating if permissions are granted
     * @return single with request permission results.
     * @throws [CompleteDeniedPermissionsException] or [DeniedPermissionsException] or [UnknownException]
     */
    @Throws(Throwable::class)
    fun requestPermissions(
        permissions: Array<String>,
        @StringRes title: Int,
        @StringRes messageRationale: Int,
        requestCode: Int
    ): Single<Boolean>
}