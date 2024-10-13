package org.example

import processing.core.PApplet
import processing.core.PApplet.map

class Charts(private val width: Int, private val height: Int) {

    private val graphHeight = 450f
    private val graphSpacing = 6f

    // draw lines connecting temperature graph to movement chart
    private fun PApplet.tempLines(temps: List<Temp>, survivors: List<List<Survivors>>) {
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
    }


    private fun PApplet.survivorPath(survivors: List<List<Survivors>>) {
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
                stroke(color.first, color.second, color.third)
                strokeWeight(map(start.survivors.toFloat(), 4000f, 340000f, 1.5f, 40f))
                line(start.lon.lonToPixelLocation(), start.lat.latToPixelLocation(), end.lon.lonToPixelLocation(), end.lat.latToPixelLocation())
            }
            // change color for next division
            color = color.shift()
        }
    }

    private fun PApplet.tempChart(temps: List<Temp>) {
        // draw graph lines
        stroke(100f, 100f, 100f)
        strokeWeight(1.5f)
        for (i in 0 downTo -35 step 5) {
            line(400f, graphHeight - i * graphSpacing, width.toFloat(), graphHeight - i * graphSpacing)
            fill(0f, 0f, 0f)
            text("${i}°C", 420f, graphHeight - i * graphSpacing - 4)
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

            // draw date label
            temp.month?.let {
                noStroke()
                fill(215f, 195f, 160f)
                rect(temp.lon.lonToPixelLocation(), graphHeight - 20, 20f, 20f)
                fill(0f, 0f, 0f)

                // offset date label for better readability
                val offset = when (temp.date) {
                    "1" -> 3f
                    "6" -> -3f
                    else -> 0f
                }
                text("${temp.month} ${temp.date}", temp.lon.lonToPixelLocation() + offset, graphHeight - 16)
            }
        }
    }

    private fun PApplet.cities(cities: List<City>) {
        // draw cities
        for (city in cities) {
            fill(240f, 240f, 30f)
            stroke(0f, 0f, 0f)
            ellipse(city.lon.lonToPixelLocation(), city.lat.latToPixelLocation(), 10f, 10f)

            textSize(15f)
            fill(255f, 255f, 255f)
            // offset names for better readability
            val xOffset = if (city.name == "Molodexno") 15f else 0f
            val yOffset = if (city.name == "Smorgoni") -18f else -10f
            text(city.name, city.lon.lonToPixelLocation() + xOffset, city.lat.latToPixelLocation() + yOffset)
        }
    }


    private fun Float.lonToPixelLocation(): Float {
        // convert from range 21.5 to 40.0 to range 0 to width
        return map(this, 21f, 39f, 300f, width.toFloat())
    }

    private fun Float.latToPixelLocation(): Float {
        // convert from range 50.0 to 60.0 to range height to 0
        return map(this, 48f, 57f, height.toFloat(), 0f)
    }

    private fun Triple<Float, Float, Float>.shift(): Triple<Float, Float, Float> {
        return Triple(second, third, first)
    }

    fun draw(applet: PApplet, cities: List<City>, temps: List<Temp>, survivors: List<List<Survivors>>) {
        applet.run {
            tempLines(temps, survivors)
            survivorPath(survivors)
            tempChart(temps)
            cities(cities)
        }
    }
}