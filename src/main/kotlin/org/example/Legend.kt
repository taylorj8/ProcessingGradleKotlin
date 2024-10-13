package org.example

import processing.core.PApplet
import processing.core.PApplet.map
import processing.core.PImage

class Legend {

    private fun PApplet.mapBackground(map: PImage, graphHeight: Float, graphSpacing: Float) {
        // draw graph lines
        stroke(100f, 100f, 100f)
        strokeWeight(1.5f)
        for (i in 0 downTo -30 step 5) {
            line(400f, graphHeight - i * graphSpacing, width.toFloat(), graphHeight - i * graphSpacing)
            fill(0f, 0f, 0f)
            text("${i}°C", 417f, graphHeight - i * graphSpacing - 3)
        }

        // draw map image in box
        image(map, 400f, 0f, width.toFloat(), 400f)
    }

    // draw dividing lines
    private fun PApplet.dividers() {
        stroke(0f, 0f, 0f)
        strokeWeight(2f)
        line(0f, 400f, width.toFloat(), 400f)
        line(400f, 0f, 400f, 630f)
        line(0f, 133f, 400f, 134f)
        line(0f, 267f, 400f, 267f)
    }

    // draw survivor count legend
    private fun PApplet.survivorLegend() {
        fill(0f, 0f, 0f)
        textSize(22f)
        text("Remaining Survivors", 200f, 30f)

        // scale
        fill(0f, 0f, 0f)
        textSize(13f)
        text("340,000", 35f, 125f)
        text("256,000", 109f, 125f)
        text("172,000", 198f, 125f)
        text("88,000", 287f, 125f)
        text("4,000", 365f, 125f)

        // draw scale lines
        stroke(50f, 50f, 50f)
        strokeWeight(1f)
        line(20f, 115f, 20f, 75f)
        line(109f, 115f, 109f, 75f)
        line(198f, 115f, 198f, 75f)
        line(287f, 115f, 287f, 75f)
        line(375f, 115f, 375f, 75f)

        noStroke()
        fill(0f, 0f, 0f)
        for ((i, x) in (40..375).withIndex()) {
            val thickness = map(i.toFloat(), 0f, 360f, 44f, 4f)
            ellipse(x.toFloat(), 75f, thickness, thickness)
        }
        fill(150f, 100f, 200f)
        for ((i, x) in (40..375).withIndex()) {
            val thickness = map(i.toFloat(), 0f, 360f, 40f, 1f)
            ellipse(x.toFloat(), 75f, thickness, thickness)
        }
    }

    private fun PApplet.divisions() {
        fill(0f, 0f, 0f)
        textSize(22f)
        text("Divisions", 200f, 165f)

        strokeWeight(1f)
        fill(250f, 150f, 175f)
        rect(50f, 210f, 50f, 35f)
        fill(125f, 75f, 87.5f)
        rect(100f, 210f, 50f, 35f)

        fill(150f, 175f, 250f)
        rect(175f, 210f, 50f, 35f)
        fill(75f, 87.5f, 125f)
        rect(225f, 210f, 50f, 35f)

        fill(175f, 250f, 150f)
        rect(300f, 210f, 50f, 35f)
        fill(87.5f, 125f, 75f)
        rect(350f, 210f, 50f, 35f)

        textSize(16f)
        fill(0f, 0f, 0f)
        text("1st", 75f, 255f)
        text("2nd", 200f, 255f)
        text("3rd", 325f, 255f)

        noFill()
        stroke(0f, 0f, 0f)
        strokeWeight(2f)

        rect(75f, 210f, 100f, 35f)
        rect(200f, 210f, 100f, 35f)
        rect(325f, 210f, 100f, 35f)
    }

    private fun PApplet.direction() {
        fill(0f, 0f, 0f)
        textSize(22f)
        text("Direction", 200f, 300f)

        strokeWeight(1f)
        stroke(0f, 0f, 0f)

        strokeWeight(0f)
        fill(250f, 150f, 175f)
        rect(68f, 345f, 70f, 35f, 17.5f)

        fill(175f, 250f, 150f)
        rect(148f, 345f, 70f, 35f, 17.5f)

        fill(150f, 175f, 250f)
        rect(108f, 345f, 50f, 35f)


        fill(125f, 75f, 87.5f)
        rect(252f, 345f, 70f, 35f, 17.5f)

        fill(87.5f, 125f, 75f)
        rect(332f, 345f, 70f, 35f, 17.5f)

        fill(75f, 87.5f, 125f)
        rect(292f, 345f, 50f, 35f)


        strokeWeight(2.5f)
        noFill()
        rect(108f, 345f, 150f, 35f, 17.5f)
        stroke(255f, 255f, 255f)
        rect(292f, 345f, 150f, 35f, 17.5f)

        textSize(16f)
        fill(0f, 0f, 0f)
        text("Attacking", 108f, 388f)
        text("Retreating", 292f, 388f)
    }

    fun draw(applet: PApplet, map: PImage, graphHeight: Float, graphSpacing: Float) {
        applet.run {
            mapBackground(map, graphHeight, graphSpacing)
            dividers()
            survivorLegend()
            divisions()
            direction()
        }
    }
}
