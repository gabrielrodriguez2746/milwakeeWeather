package com.milwaukee.weather.permissions.models

data class PermissionResult(val permission: String,
                            val grantResult: PermissionState
)