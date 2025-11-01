package dev.priporov.ycalendar.client.yandex

import ai.grazie.nlp.utils.dropWhitespaces
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import dev.priporov.ycalendar.client.CaldavRequestTemplate
import dev.priporov.ycalendar.dto.ConferenceType
import dev.priporov.ycalendar.dto.EventDataDto
import dev.priporov.ycalendar.dto.MultistatusDto
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.data.ParserException
import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.Period
import net.fortuna.ical4j.model.Property
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property.Conference
import java.io.StringReader
import java.net.URI
import java.net.URISyntaxException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.*
import kotlin.jvm.optionals.getOrNull

object CaldavParser {
    private val zoneId = TimeZone.getDefault().toZoneId()

    private var mapper: XmlMapper = XmlMapper.builder()
        .defaultUseWrapper(false)
        .build()

    fun toEvents(content: String): Set<EventDataDto>? {
        if (content.isBlank()) {
            return emptySet()
        }
        val value: MultistatusDto? = mapper.readValue(content, MultistatusDto::class.java)

        val today = ZonedDateTime.now(zoneId)

        val now = ZonedDateTime.now().toLocalDate()
        val startDateTime = now.atTime(LocalTime.MIN).atZone(CaldavRequestTemplate.zoneId)
        val endDateTime = now.atTime(LocalTime.MAX).atZone(CaldavRequestTemplate.zoneId)


        return value?.response
            ?.asSequence()
            ?.map { it.propstat?.prop?.calendarData?.text }
            ?.filterNotNull()
            ?.map(this::toCalendar)
            ?.flatMap { it.getComponents<VEvent>("VEVENT").asSequence() }
            ?.map { toEventDataDto(it, startDateTime, endDateTime) }
            ?.filter { it.endDate?.isAfter(today) ?: false }
            ?.filter { it.startDate?.toLocalDate()?.equals(today.toLocalDate()) ?: false }
            ?.toSet()
    }

    private fun toCalendar(it: String): Calendar {
        try {
            return CalendarBuilder().build(StringReader(it.dropWhitespaces()))
        } catch (parserException: ParserException) {
            val fixedString = it.substringBeforeLast("END:VCALENDAR", it) + "END:VEVENT\n" + "END:VCALENDAR"
            return CalendarBuilder().build(StringReader(fixedString.dropWhitespaces()))
        }
    }

    fun toEventDataDto(event: VEvent, startDateTime: ZonedDateTime, endDateTime: ZonedDateTime): EventDataDto {
        return EventDataDto().apply {
            val recurrence = event.calculateRecurrenceSet<ZonedDateTime>(Period(startDateTime, endDateTime))

            if (!recurrence.isNullOrEmpty()) {
                val dateTimePeriod = recurrence.first()
                startDate = dateTimePeriod.start.withZoneSameInstant(zoneId)
                endDate = dateTimePeriod.end.withZoneSameInstant(zoneId)
            } else {
                startDate = event.getDateTimeStart<ZonedDateTime>().date.withZoneSameInstant(zoneId)
                endDate = event.getEndDate<ZonedDateTime>().get().date.withZoneSameInstant(zoneId)
            }
            name = event.getProperties<Property>()
                .asSequence()
                .filter { it.value.lowercase().contains("summary") }
                .map { it.value }
                .firstOrNull()?.substring(8)

            if(name.isNullOrBlank()){
                name = if(event.summary != null){
                    event.summary.value
                } else if (event.location != null){
                    event.location.value
                } else {
                    "Event"
                }
            }

            conference = getConferenceLink(event)
            conferenceType = getConferenceType(event)
            description = event.description?.value
            uid = event.uid.getOrNull()?.value
            url = event.url?.uri
        }
    }

    private fun getConferenceLink(event: VEvent): URI? {
        val conference = event.getProperty<Conference>("CONFERENCE")
        if (conference.isPresent) {
            return toUri(conference.get().value)
        }
        val description = event.description
        if (description != null && description != null) {
            val descriptionText = description.value
            val url = "https://" + descriptionText.substringAfter("https://")
                .substringBefore(' ')
                .substringBefore('\t')
                .substringBefore('\n')
            if (url.isNotEmpty()) {
                return toUri(url)
            }
        }
        return null
    }

    fun getConferenceType(event: VEvent): ConferenceType {
        val conference = event.getProperty<Conference>("CONFERENCE")
        if (conference.isPresent) {
            return ConferenceType.TELEMOST
        }
        val description = event.description
        if (description != null && description != null) {
            val descriptionText = description.value
            val url = descriptionText.substringAfter("https://").substringBefore(' ')
            if (url.isNotEmpty() && url.contains(".zoom.")) {
                return ConferenceType.ZOOM
            }
        }
        return ConferenceType.OTHER
    }


    fun toUri(url: String?): URI? {
        if (url.isNullOrBlank()) {
            return null
        }
        try {
            return URI(url)
        } catch (e: URISyntaxException) {
            println(e.message)
            return null
        }
    }
}