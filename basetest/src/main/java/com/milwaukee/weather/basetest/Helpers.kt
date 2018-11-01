package com.milwaukee.weather.basetest

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.FileReader

fun readJsonFile(fileName: String): JsonObject {
    val jsonFile = ClassLoader.getSystemClassLoader()
        .getResource(fileName)
        .file
    val parser = JsonParser()
    val jsonElement = parser.parse(FileReader(jsonFile))
    return jsonElement.asJsonObject
}