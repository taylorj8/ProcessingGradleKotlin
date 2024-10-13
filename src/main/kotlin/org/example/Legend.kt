package org.example

import processing.core.PApplet
import processing.core.PApplet.map

class Legend {

    // draw survivor count legend
    private fun PApplet.survivorLegend() {
        fill(0f, 0f, 0f)
        textSize(22f)
        text("Remaining Survivors", 200f, 30f)

        // scale
        fill(0f, 0f, 0f)
        textSize(15f)
        text("340,000", 35f, 125f)
        text("256,000", 109f, 125f)
        text("172,000", 198f, 125f)
        text("88,000", 287f, 125f)
        text("4,000", 365f, 125f)

        // draw scale lines
        stroke(50f, 50f, 50f)
        strokeWeight(1f)
        line(32f, 115f, 32f, 75f)
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
        fill(150f, 150f, 150f)
//        fill(150f, 100f, 200f)
        for ((i, x) in (40..375).withIndex()) {
            val thickness = map(i.toFloat(), 0f, 360f, 40f, 1f)
            ellipse(x.toFloat(), 75f, thickness, thickness)
        }
    }

    private fun PApplet.divisions() {
        fill(0f, 0f, 0f)
        textSize(22f)
        text("Army Divisions", 200f, 165f)

        stroke(0f, 0f, 0f)
        strokeWeight(2f)

        fill(250f, 150f, 175f)
        rect(75f, 210f, 100f, 35f)
        fill(150f, 175f, 250f)
        rect(200f, 210f, 100f, 35f)
        fill(175f, 250f, 150f)
        rect(325f, 210f, 100f, 35f)

        textSize(16f)
        fill(0f, 0f, 0f)
        text("1st", 75f, 255f)
        text("2nd", 200f, 255f)
        text("3rd", 325f, 255f)
    }

    private fun PApplet.direction() {
        fill(0f, 0f, 0f)
        textSize(22f)
        text("Direction", 200f, 300f)

        strokeWeight(2.5f)
        fill(150f, 150f, 150f)
        rect(108f, 345f, 150f, 35f, 17.5f)
        stroke(255f, 255f, 255f)
        rect(292f, 345f, 150f, 35f, 17.5f)

        textSize(16f)
        fill(0f, 0f, 0f)
        text("Attacking", 108f, 388f)
        text("Retreating", 292f, 388f)
    }

    fun draw(applet: PApplet) {
        applet.run {
            survivorLegend()
            divisions()
            direction()
        }
    }
}
