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


        // draw graph lines
        stroke(100f, 100f, 100f)
        strokeWeight(1.5f)
        for (i in 0 downTo -30 step 5) {
            line(400f, graphHeight - i * graphSpacing, width.toFloat(), graphHeight - i * graphSpacing)
            fill(0f, 0f, 0f)
            text("${i}°", 415f, graphHeight - i * graphSpacing - 3)
        }

        // draw map image in box
        image(map, 400f, 0f, width.toFloat(), 400f)

        // draw dividing lines
        stroke(0f, 0f, 0f)
        strokeWeight(2f)
        line(0f, 400f, width.toFloat(), 400f)
        line(400f, 0f, 400f, 630f)
        line(0f, 133f, 400f, 134f)
        line(0f, 267f, 400f, 267f)

        // draw survivor count legend
        noStroke()
        fill(0f, 0f, 0f)
        for ((i, x) in (40..360).withIndex()) {
            val thickness = map(i.toFloat(), 0f, 360f, 44f, 4f)
            ellipse(x.toFloat(), 93f, thickness, thickness)
        }
        fill(150f, 100f, 200f)
        for ((i, x) in (40..360).withIndex()) {
            val thickness = map(i.toFloat(), 0f, 360f, 40f, 1f)
            ellipse(x.toFloat(), 93f, thickness, thickness)
        }

        // draw lines connecting temperature graph to movement chart
        stroke(150f, 150f, 150f)
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


        // draw line showing path of survivors with variable line width based on number of survivors
        var color = Triple(250f, 150f, 175f)
        for (division in survivors) {
            for ((start, end) in division.zipWithNext()) {
                val c = if (start.direction == 'A') 0f else 255f
                val t = if (start.direction == 'A') 4f else 2.5f
                stroke(c, c, c)
                strokeWeight(map(start.survivors.toFloat(), 4000f, 340000f, 1.5f, 40f) + t)
                line(start.lon.lonToPixelLocation(), start.lat.latToPixelLocation(), end.lon.lonToPixelLocation(), end.lat.latToPixelLocation())
            }

            for ((start, end) in division.zipWithNext()) {
                val x = if (start.direction == 'A') 1f else 0.5f
                stroke(color.first * x, color.second * x, color.third * x)
                strokeWeight(map(start.survivors.toFloat(), 4000f, 340000f, 1.5f, 40f))
                line(start.lon.lonToPixelLocation(), start.lat.latToPixelLocation(), end.lon.lonToPixelLocation(), end.lat.latToPixelLocation())
            }
            // change color for next division
            color = color.shift()
        }


        // draw line chart under the cities representing temperature
        for ((t1, t2) in temps.zipWithNext()) {
            // draw line connecting the temperature points
            stroke(0f, 0f, 0f)
            line(t1.lon.lonToPixelLocation(), graphHeight - t1.temperature * graphSpacing, t2.lon.lonToPixelLocation(), graphHeight - t2.temperature * graphSpacing)
        }
        for (temp in temps) {
            val tempOffset = temp.temperature * graphSpacing

            // draw temperature point
            fill(220f, 50f, 50f)
            stroke(0f, 0f, 0f)
            ellipse(temp.lon.lonToPixelLocation(), graphHeight - tempOffset, 8f, 8f)

            // draw temperature label
            fill(0f, 0f, 0f)
            text("${temp.temperature}°", temp.lon.lonToPixelLocation(), graphHeight - tempOffset + 20)
        }

        // draw cities
        for (city in cities) {
            fill(240f, 240f, 30f)
            stroke(0f, 0f, 0f)
            ellipse(city.lon.lonToPixelLocation(), city.lat.latToPixelLocation(), 10f, 10f)

            fill(255f, 255f, 255f)
            text(city.name, city.lon.lonToPixelLocation(), city.lat.latToPixelLocation() - 10)
        }


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

    // helper functions
    private fun Float.lonToPixelLocation(): Float {
        // convert from range 21.5 to 40.0 to range 0 to width
        return map(this, 21f, 39f, 300f, width.toFloat()) //1200
    }

    private fun Float.latToPixelLocation(): Float {
        // convert from range 50.0 to 60.0 to range height to 0
        return map(this, 48f, 57f, height.toFloat(), 0f) //480
    }

    private fun Triple<Float, Float, Float>.shift(): Triple<Float, Float, Float> {
        return Triple(second, third, first)
    }
}