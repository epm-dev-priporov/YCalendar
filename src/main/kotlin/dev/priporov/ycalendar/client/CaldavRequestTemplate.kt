package dev.priporov.ycalendar.client

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object CaldavRequestTemplate {
    private val PATTERN = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
    val zoneId = ZoneId.of("UTC")

    fun template(): String {
        val now = LocalDate.now()
        val today = now.atTime(LocalTime.MIN).atZone(zoneId)
        val tomorrow = now.atTime(LocalTime.MAX).atZone(zoneId)
        
        return """
            <?xml version="1.0" encoding="utf-8" ?>
            <C:calendar-query xmlns:D="DAV:"
                xmlns:C="urn:ietf:params:xml:ns:caldav">
                <D:prop>
                    <D:getetag/>
                    <C:calendar-data>
                        <C:comp name="VCALENDAR">
                            <C:prop name="VERSION"/>
                            <C:comp name="VEVENT">
                                <C:prop name="SUMMARY"/>
                                <C:prop name="DTSTART"/>
                                <C:prop name="DTEND"/>
                            </C:comp>
                            <C:comp name="VTIMEZONE"/>
                        </C:comp>
                    </C:calendar-data>
                </D:prop>
                <C:filter>
                    <C:comp-filter name="VCALENDAR">
                        <C:comp-filter name="VEVENT">
                            <C:time-range start="${today.format(PATTERN)}"
                                end="${tomorrow.format(PATTERN)}"/>
                        </C:comp-filter>
                    </C:comp-filter>
                </C:filter>
            </C:calendar-query>
        """.trim()
    }

}