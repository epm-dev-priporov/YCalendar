package dev.priporov.yandexcalendar.client

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object CaldavRequestTemplate {
    private val PATTERN = DateTimeFormatter.ofPattern("yyyyMMdd")

    fun template(): String {
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)
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
                                <C:prop name="UID"/>
                                <C:prop name="DTSTART"/>
                                <C:prop name="DTEND"/>
                                <C:prop name="DURATION"/>
                                <C:prop name="RRULE"/>
                                <C:prop name="RDATE"/>
                                <C:prop name="EXRULE"/>
                                <C:prop name="EXDATE"/>
                                <C:prop name="RECURRENCE-ID"/>
                            </C:comp>
                            <C:comp name="VTIMEZONE"/>
                        </C:comp>
                    </C:calendar-data>
                </D:prop>
                <C:filter>
                    <C:comp-filter name="VCALENDAR">
                        <C:comp-filter name="VEVENT">
                            <C:time-range start="${today.format(PATTERN)}T000000Z"
                                end="${tomorrow.format(PATTERN)}T000000Z"/>
                        </C:comp-filter>
                    </C:comp-filter>
                </C:filter>
            </C:calendar-query>
        """.trimIndent()
    }


}