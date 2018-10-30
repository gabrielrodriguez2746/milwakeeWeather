package com.milwaukee.weather.base.permissions

data class PermissionResult(val permission: String,
                            val grantResult: PermissionState)

enum class PermissionState { GRANTED, DENIED, COMPLETELY_DENIED }