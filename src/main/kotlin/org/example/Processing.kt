package org.example

import processing.core.PApplet
import java.io.File

class Processing : PApplet() {
    private lateinit var cities: List<City>
    private lateinit var temps: List<Temp>
    private lateinit var survivors: List<List<Survivors>>

    override fun settings() {
        size(1600, 900, JAVA2D)
    }

    override fun setup() {
        cities = readCities(File("data/minard-data-city.csv"))
        temps = readTemps(File("data/minard-data-temp.csv"))
        survivors = readSurvivors(File("data/minard-data-survivors.csv"))

        background(215f, 195f, 160f)
        // draw map image in box
        val map = loadImage("resources/Russia_Map.png")
        image(map, 400f, 0f, width.toFloat(), 400f)

        textAlign(CENTER)
        rectMode(CENTER)
        imageMode(CENTER)
        textSize(15f)

        Charts(width, height).draw(this, cities, temps, survivors)
        Legend().draw(this)
        Styling().draw(this)
    }
}