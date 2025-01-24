package dev.priporov.ycalendar.dto

import java.net.URI
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class EventDataDto {
    var url: URI? = null
    var uid: String? = null
    var name: String? = null
    var description: String? = null
    var startDate: ZonedDateTime? = null
    var endDate: ZonedDateTime? = null
    var conference: URI? = null
    var conferenceType: ConferenceType = ConferenceType.OTHER
    var notified: AtomicBoolean = AtomicBoolean(false)

    private val pattern = DateTimeFormatter.ofPattern("HH:mm")

    override fun toString(): String {
        return "${startDate?.format(pattern)}-${endDate?.format(pattern)} ${name}"
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is EventDataDto) {
            return false
        }

        return name == other.name
                && startDate?.toLocalDate() == other.startDate?.toLocalDate()
                && endDate?.toLocalDate() == other.endDate?.toLocalDate()
                && conferenceType == other.conferenceType
    }

    override fun hashCode(): Int {
        return Objects.hash(uid)
    }

    fun startsIn5Minutes(): Boolean {
        val time = LocalTime.now().minusMinutes(5)
        return time.isAfter(this.startDate?.toLocalTime()) && time.isBefore(this.endDate?.toLocalTime())
    }

}
