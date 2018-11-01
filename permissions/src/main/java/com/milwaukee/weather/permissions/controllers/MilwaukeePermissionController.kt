package com.milwaukee.weather.permissions.controllers

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.milwaukee.weather.permissions.R
import com.milwaukee.weather.permissions.models.PermissionResult
import com.milwaukee.weather.permissions.models.PermissionState
import com.milwaukee.weather.permissions.controllers.base.PermissionController
import com.milwaukee.weather.permissions.exceptions.CompleteDeniedPermissionsException
import com.milwaukee.weather.permissions.exceptions.DeniedPermissionsException
import com.milwaukee.weather.permissions.exceptions.UnknownException
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class MilwaukeePermissionController : PermissionController {

    private lateinit var activity: AppCompatActivity

    private val permissionSubject by lazy { PublishSubject.create<Pair<Int, List<PermissionResult>>>() }

    override fun initValues(activity: AppCompatActivity) {
        this.activity = activity
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionSubject.onNext(Pair(requestCode, permissions.mapToResults(grantResults)))
    }

    override fun requestPermissions(
        permissions: Array<String>,
        title: Int,
        messageRationale: Int,
        requestCode: Int
    ): Single<Boolean> {

        return permissionSubject.hide()
            .observeOn(Schedulers.computation())
            .filter { it.first == requestCode }
            .map { it.second }
            .doOnSubscribe(provideRationalPermissionsConsumer(permissions, title, messageRationale, requestCode))
            .firstOrError()
            .map(::processPermissionsResults)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                if (it is CompleteDeniedPermissionsException) {
                    provideAlertDialogFromParameters(R.string.permissions_app_settings, R.string.permissions_settings,
                        { dialogInterface -> dialogInterface.dismiss()
                        }, { startSettingsIntent() }).show()
                }
            }
    }

    private fun startSettingsIntent() {
        activity.startActivity(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", activity.packageName, null)
        })
    }

    private fun provideRationalPermissionsConsumer(
        permissions: Array<String>,
        title: Int,
        messageRationale: Int,
        requestCode: Int
    ): Consumer<Disposable> {
        return Consumer {
            if (permissions.shouldShowRequestPermissionRationale(activity)) {
                provideAlertDialogFromParameters(title, messageRationale,
                    { dialogInterface ->
                        dialogInterface.dismiss()
                        val result =
                            permissions.map { permission ->
                                PermissionResult(
                                    permission,
                                    PermissionState.DENIED
                                )
                            }
                        permissionSubject.onNext(Pair(requestCode, result))
                    }, {
                        ActivityCompat.requestPermissions(activity, permissions, requestCode)
                    }).show()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, requestCode)
            }
        }
    }

    private fun provideAlertDialogFromParameters(
        title: Int,
        messageRationale: Int,
        negativeButtonAction: (dialogInterface: DialogInterface) -> Unit,
        positiveButtonAction: () -> Unit
    ): AlertDialog.Builder {
        return AlertDialog.Builder(activity).apply {
            setTitle(title)
            setMessage(messageRationale)
            setCancelable(false)
            setNegativeButton(R.string.permissions_rationale_skip) { dialogInterface, _ ->
                negativeButtonAction.invoke(dialogInterface)
            }
            setPositiveButton(R.string.permissions_rationale_accept) { _, _ ->
                positiveButtonAction.invoke()
            }
        }
    }

    private fun processPermissionsResults(results: List<PermissionResult>): Boolean {
        return when {
            results.all { it.grantResult == PermissionState.GRANTED } -> true
            results.any { it.grantResult == PermissionState.COMPLETELY_DENIED } -> throw CompleteDeniedPermissionsException()
            results.any { it.grantResult == PermissionState.DENIED } -> throw DeniedPermissionsException()
            else -> throw UnknownException()
        }
    }

    private fun Boolean.permissionToState() = if (this) {
        PermissionState.DENIED
    } else {
        PermissionState.COMPLETELY_DENIED
    }

    private fun Array<out String>.mapToResults(grantResults: IntArray): List<PermissionResult> {
        return mapIndexed { index, permission ->
            PermissionResult(
                permission, when (grantResults[index]) {
                    PackageManager.PERMISSION_GRANTED -> PermissionState.GRANTED
                    else -> ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        permission
                    ).permissionToState()
                }
            )
        }
    }

    private fun Array<out String>.shouldShowRequestPermissionRationale(activity: AppCompatActivity): Boolean {
        return any { ActivityCompat.shouldShowRequestPermissionRationale(activity, it) }
    }

}