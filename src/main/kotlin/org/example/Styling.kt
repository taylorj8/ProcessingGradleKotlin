package org.example

import processing.core.PApplet

class Styling {

    // draw dividing lines
    private fun PApplet.dividers() {
        stroke(0f, 0f, 0f)
        strokeWeight(2f)
        line(0f, 400f, width.toFloat(), 400f)
        line(0f, 660f, width.toFloat(), 660f)
        line(400f, 0f, 400f, 660f)
        line(0f, 133f, 400f, 134f)
        line(0f, 267f, 400f, 267f)
    }

    private fun PApplet.title() {
        fill(0f, 0f, 0f)
        textFont(createFont("Papyrus", 70f))
        text("Napoleon's March on Russia, 1812", width / 2f, 800f)
    }

    private fun PApplet.napoleonicFlag() {
        noStroke()
        fill(0f, 38f, 84f)
        rect(66.5f, 530f, 133f, 260f)

        fill(255f, 255f, 255f)
        rect(200f, 530f, 134f, 260f)

        fill(237f, 41f, 57f)
        rect(333.5f, 530f, 133f, 260f)

        val map = loadImage("resources/Coat_of_Arms.png")
        image(map, 200f, 530f, 180f, 200f)
    }

    fun draw(applet: PApplet) {
        applet.run {
            title()
            napoleonicFlag()
            dividers()
        }
    }
}