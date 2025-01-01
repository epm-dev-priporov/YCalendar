package dev.priporov.yandexcalendar.panel

import dev.priporov.yandexcalendar.dto.EventDataDto
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.component.VEvent
import java.io.StringReader
import java.time.ZonedDateTime
import java.util.*
import javax.swing.JPanel
import kotlin.jvm.optionals.asSequence


class CalendarPanel : JPanel() {

    init {
        val list: List<EventDataDto>? = value?.response?.asSequence()
            ?.map { it.propstat?.prop?.calendarData?.text }
            ?.filterNotNull()
            ?.map {
                CalendarBuilder()
                    .build(StringReader(it?.trim()?.replace(" ", "")))
            }
            ?.map { it.getComponent<VEvent>("VEVENT") }
            ?.filter { it.isPresent }
            ?.map { it.get() }
            ?.map {
                EventDataDto().apply {
                    date = it.getDateTimeStart<ZonedDateTime>().get().date
                    name = it.summary.get().value
                }
            }
            ?.toList()

        println(list)
    }
}