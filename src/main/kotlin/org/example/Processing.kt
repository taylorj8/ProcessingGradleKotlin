package org.example

import processing.core.PApplet
import java.io.File

class Processing : PApplet() {
    private lateinit var cities: List<City>
    private lateinit var temps: List<Temp>
    private lateinit var survivors: List<List<Survivors>>

    override fun settings() {
        size(1600, 800, JAVA2D)
    }

    override fun setup() {
        cities = readCities(File("data/minard-data-city.csv"))
        temps = readTemps(File("data/minard-data-temp.csv"))
        survivors = readSurvivors(File("data/minard-data-survivors.csv"))

        textAlign(CENTER)


    }

    override fun draw() {
        background(200f, 230f, 170f)

        // draw cities
        for (city in cities) {
            fill(255f, 0f, 0f)
            ellipse(city.lon.lonToPixelLocation(), city.lat.latToPixelLocation(), 10f, 10f)

            fill(0f, 0f, 0f)
            text(city.name, city.lon.lonToPixelLocation(), city.lat.latToPixelLocation() - 10)
        }

//        // draw line showing path of the survivors
//        for (i in 0 until survivors.size - 1) {
//            val start = survivors[i]
//            val end = survivors[i + 1]
//
//            stroke(0f, 0f, 0f)
//            strokeWeight(2f)
//            line(start.lon.lonToPixelLocation(), start.lat.latToPixelLocation(), end.lon.lonToPixelLocation(), end.lat.latToPixelLocation())
//        }

        // draw line showing path of survivors with variable line length based on number of survivors
        for (division in survivors) {
            for (i in 0 until division.size - 1) {
                val start = division[i]
                val end = division[i + 1]


                // vary colour based on survivor count
                val red = map(start.survivors.toFloat(), 4000f, 340000f, 0f, 255f)
                stroke(red, 50f, 50f)
                strokeWeight(map(start.survivors.toFloat(), 4000f, 340000f, 2f, 50f))
                line(start.lon.lonToPixelLocation(), start.lat.latToPixelLocation(), end.lon.lonToPixelLocation(), end.lat.latToPixelLocation())
            }
        }
    }

    private fun Float.lonToPixelLocation(): Float {
        // convert from range 20.0 to 40.0 to range 0 to width
        return map(this, 20f, 40f, 0f, width.toFloat())
    }

    private fun Float.latToPixelLocation(): Float {
        // convert from range 50.0 to 60.0 to range height to 0
        return map(this, 50f, 60f, height.toFloat(), 0f)
    }
}