package org.example

import java.io.File

data class City(
    val name: String,
    val lon: Float,
    val lat: Float
)

data class Temp(
    val lon: Float,
    val temperature: Int,
    val days: Int,
    val month: String,
    val date: Int
)

data class Survivors(
    val lon: Float,
    val lat: Float,
    val survivors: Int,
    val direction: Char,
    val division: Int
)

fun readCities(file: File): List<City> {
    val inputStream = file.inputStream()
    val reader = inputStream.bufferedReader()
    reader.readLine()
    return reader.lineSequence()
        .filter { it.isNotBlank() }
        .map {
            val (lon, lat, name) = it.split(',', ignoreCase = false, limit = 3)
            City(name.trim(), lon.toFloat(), lat.toFloat())
        }.toList()
}

fun readTemps(file: File): List<Temp> {
    val inputStream = file.inputStream()
    val reader = inputStream.bufferedReader()
    reader.readLine()
    return reader.lineSequence()
        .filter { it.isNotBlank() }
        .map {
            val (lon, temp, days, month, date) = it.split(',', ignoreCase = false, limit = 5)
            Temp(lon.toFloat(), temp.toInt(), days.toInt(), month.ifEmpty { "" }.trim(), date.ifEmpty{ "-1" }.toInt())
        }.toList()
}

fun readSurvivors(file: File): List<Survivors> {
    val inputStream = file.inputStream()
    val reader = inputStream.bufferedReader()
    reader.readLine()
    return reader.lineSequence()
        .filter { it.isNotBlank() }
        .map {
            val (lon, lat, survivors, direction, division) = it.split(',', ignoreCase = false, limit = 5)
            Survivors(lon.toFloat(), lat.toFloat(), survivors.toInt(), direction.first(), division.toInt())
        }.toList()
}
