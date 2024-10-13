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
        size(1600, 900, JAVA2D)
    }

    override fun setup() {
        cities = readCities(File("data/minard-data-city.csv"))
        temps = readTemps(File("data/minard-data-temp.csv"))
        survivors = readSurvivors(File("data/minard-data-survivors.csv"))
        map = loadImage("data/map.png")

        textAlign(CENTER)
        rectMode(CENTER)
        textSize(13f)

        background(215f, 195f, 160f)
        val graphHeight = 450f
        val graphSpacing = 6f

        Legend().draw(this, map, graphHeight, graphSpacing)

        textSize(13f)
        Charts(width, height).draw(this, cities, temps, survivors, graphHeight, graphSpacing)
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