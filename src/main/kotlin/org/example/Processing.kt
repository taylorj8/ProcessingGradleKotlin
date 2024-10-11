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
        background(215f, 195f, 160f)
        val graphHeight = height - 300f
        val graphSpacing = 6f


        // draw graph lines
        stroke(100f, 100f, 100f)
        strokeWeight(1.5f)
        for (i in 0 downTo -30 step 5) {
            line(150f, graphHeight - i * graphSpacing, width - 150f, graphHeight - i * graphSpacing)
            fill(0f, 0f, 0f)
            text("${i}°", 160f, graphHeight - i * graphSpacing - 3)
        }

        // draw box for chart
        stroke(0f, 0f, 0f)
        strokeWeight(2f)
        line(400f, 400f, width.toFloat(), 400f)
        line(400f, 0f, 400f, 400f)

        // draw map image in box
        image(loadImage("data/map.png"), 400f, 0f, width.toFloat(), 400f)



        // draw lines connecting temperature graph to movement chart
        stroke(100f, 100f, 100f)
        for (temp in temps) {
            val survivorsList = survivors.flatten()
            val survivor = survivorsList.find { it.lon == temp.lon && it.direction == 'R' }
            survivor?.let {
                line(temp.lon.lonToPixelLocation(), graphHeight - temp.temperature * graphSpacing, temp.lon.lonToPixelLocation(), survivor.lat.latToPixelLocation())
            } ?: run {
                // find survivor with closest longitude above and below the temperature point
                val above = survivorsList.filter { it.lon > temp.lon && it.direction == 'R' }.minByOrNull { it.lon }
                val below = survivorsList.filter { it.lon < temp.lon && it.direction == 'R' }.maxByOrNull { it.lon }
                // get average latitude of the two survivors
                val mappedLat = map(temp.lon, below!!.lon, above!!.lon, below.lat, above.lat)
                line(temp.lon.lonToPixelLocation(), graphHeight - temp.temperature * graphSpacing, temp.lon.lonToPixelLocation(), mappedLat.latToPixelLocation())
            }
        }


        // draw line showing path of survivors with variable line length based on number of survivors
        var color = Triple(200f, 100f, 150f)
        for (division in survivors) {
            for (i in 0 until division.size - 1) {
                val start = division[i]
                val end = division[i + 1]

                stroke(color.first, color.second, color.third)
                strokeWeight(map(start.survivors.toFloat(), 4000f, 340000f, 1.5f, 40f))
                line(start.lon.lonToPixelLocation(), start.lat.latToPixelLocation(), end.lon.lonToPixelLocation(), end.lat.latToPixelLocation())
            }
            // change color for next division
            color = color.shift()
        }


        // draw line chart under the cities representing temperature
        for (tempPair in temps.zipWithNext()) {
            val temp = tempPair.first
            val tempOffset = temp.temperature * graphSpacing

            // draw temperature point
            fill(0f, 0f, 255f)
            stroke(0f, 0f, 0f)
            ellipse(temp.lon.lonToPixelLocation(), graphHeight - tempOffset, 8f, 8f)

            // draw temperature label
            fill(0f, 0f, 0f)
            text("${temp.temperature}°", temp.lon.lonToPixelLocation(), graphHeight - tempOffset + 25)

            // draw line connecting the temperature points
            line(temp.lon.lonToPixelLocation(), graphHeight - tempOffset, tempPair.second.lon.lonToPixelLocation(), graphHeight - tempPair.second.temperature * graphSpacing)
        }
        // draw the last temperature point
        text("${temps.last().temperature}°", temps.last().lon.lonToPixelLocation(), graphHeight - temps.last().temperature * graphSpacing + 25)
        fill(0f, 0f, 255f)
        ellipse(temps.last().lon.lonToPixelLocation(), graphHeight - temps.last().temperature * graphSpacing, 8f, 8f)


        // draw cities
        for (city in cities) {
            fill(255f, 255f, 0f)
            stroke(0f, 0f, 0f)
            ellipse(city.lon.lonToPixelLocation(), city.lat.latToPixelLocation(), 10f, 10f)

            fill(0f, 0f, 0f)
            text(city.name, city.lon.lonToPixelLocation(), city.lat.latToPixelLocation() - 10)
        }

        // Draw the 0.1 longitude and latitude grid
        stroke(50f, 50f, 50f)
        strokeWeight(1f)

        // Draw longitude lines
        for (lon in 21..39 step 1) {
            for (i in 0..4) {
                val lonValue = lon + i * 0.2f
                val x = lonValue.lonToPixelLocation()
                line(x, 0f, x, height.toFloat())
            }
        }

        // Draw latitude lines
        for (lat in 48..57 step 1) {
            for (i in 0..4) {
                val latValue = lat + i * 0.2f
                val y = latValue.latToPixelLocation()
                line(0f, y, width.toFloat(), y)
            }
        }

    }

    private fun Float.lonToPixelLocation(): Float {
        // convert from range 21.5 to 40.0 to range 0 to width
        return map(this, 21f, 39f, 300f, width.toFloat()) //1200
    }

    private fun Float.latToPixelLocation(): Float {
        // convert from range 50.0 to 60.0 to range height to 0
        return map(this, 48f, 57f, height.toFloat(), 0f) //480
    }

    private fun Triple<Float, Float, Float>.shift(): Triple<Float, Float, Float> {
        return Triple(third, first, second)
    }
}