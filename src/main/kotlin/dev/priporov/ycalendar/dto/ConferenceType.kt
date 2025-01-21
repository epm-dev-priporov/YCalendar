package dev.priporov.ycalendar.dto

import javax.swing.ImageIcon

enum class ConferenceType(image: String) {
    TELEMOST("telemost.16x16.png"),
    ZOOM("zoom.16x16.png"),
    OTHER("other-16.png");

    val icon = ImageIcon(ConferenceType::class.java.getResource("/icons/$image"))

}