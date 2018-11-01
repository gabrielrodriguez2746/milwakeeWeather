package com.milwaukee.weather.base.helpers

import okhttp3.HttpUrl
import okhttp3.Request

fun Request.getNewRequest(requestKey: String, requestValue: String): Request {
    return newBuilder()
        .url(buildNewUrl(requestKey, requestValue)).build()
}

fun Request.buildNewUrl(requestKey: String, requestValue: String): HttpUrl {
    return url().newBuilder().addQueryParameter(requestKey, requestValue).build()
}