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
            text("${i}°", 415f, graphHeight - i * graphSpacing - 3)
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
    }

    fun draw(applet: PApplet, map: PImage, graphHeight: Float, graphSpacing: Float) {
        applet.run {
            mapBackground(map, graphHeight, graphSpacing)
            dividers()
            survivorLegend()
        }
    }
}
