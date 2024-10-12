package org.example

import processing.core.PApplet
import processing.core.PImage
import java.io.File

class Processing : PApplet() {
    private lateinit var cities: List<City>
    private lateinit var temps: List<Temp>
    private lateinit var survivors: List<List<Survivors>>
    private lateinit var map: PImage

    override fun settings() {
        size(1600, 800, JAVA2D)
    }

    override fun setup() {
        cities = readCities(File("data/minard-data-city.csv"))
        temps = readTemps(File("data/minard-data-temp.csv"))
        survivors = readSurvivors(File("data/minard-data-survivors.csv"))
        map = loadImage("data/map.png")

        textAlign(CENTER)
    }

    override fun draw() {
        background(215f, 195f, 160f)
        val graphHeight = height - 350f
        val graphSpacing = 6f

        Legend().draw(this, map, graphHeight, graphSpacing)

        Charts(width, height).draw(this, cities, temps, survivors, graphHeight, graphSpacing)


//        for (division in survivors) {
//            for ((s1, s2) in division.zipWithNext()) {
//                val angle = atan2(s2.lat - s1.lat, s2.lon - s1.lon)
//                drawArrow(s1.lon.lonToPixelLocation(), s1.lat.latToPixelLocation(), 20f, angle)
//            }
//        }
    }

    private fun drawArrow(x: Float, y: Float, size: Float, angle: Float) {
        pushMatrix()
        translate(x, y)
        rotate(angle)
        line(0f, 0f, size, 0f)
        line(size, 0f, size - 5, -5f)
        line(size, 0f, size - 5, 5f)
        popMatrix()
    }
}