package dev.priporov.ycalendar.list

import com.intellij.openapi.components.Service
import dev.priporov.ycalendar.dto.EventDataDto
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import javax.swing.DefaultListModel

@Service
class CalendarListModel : DefaultListModel<EventDataDto>() {

    private val map = ConcurrentHashMap<String?, EventDataDto>()

    fun sortedSet() = map.values.distinctBy { it.uid }.sortedBy { it.startDate?.toLocalTime() }

    override fun addElement(element: EventDataDto?) {
        if (element == null) {
            return
        }

        if (!map.containsKey(element.uid)) {
            map[element.uid] = element
            super.clear()
            super.addAll(sortedSet())
        } else {
            val existingElement = map[element.uid]

            existingElement?.apply {
                startDate = element.startDate
                endDate = element.endDate
                description = element.description
                name = element.name
                conference = element.conference
                conferenceType = element.conferenceType
            }
            super.removeAllElements()
            super.addAll(sortedSet())
        }
    }

    override fun removeElement(obj: Any?): Boolean {
        if (obj !is EventDataDto) {
            return false
        }

        map.remove(obj.uid)

        return super.removeElement(obj)
    }

    fun getFinishedElements(): List<EventDataDto> {
        val now = LocalDateTime.now()
        return sortedSet().filter { now.toLocalTime().isAfter(it.endDate?.toLocalTime()) }
    }

    fun syncElements(collection: Collection<EventDataDto>) {
        sortedSet().filter { !collection.contains(it) }.forEach { removeElement(it) }
        collection.forEach { addElement(it) }
    }

    override fun addAll(collection: Collection<EventDataDto>?) {
        collection?.forEach { addElement(it) }
    }

    override fun removeAllElements() {
        map.clear()
        super.removeAllElements()
    }

    fun getStartedElements(): List<EventDataDto> {
        val now = LocalDateTime.now()
        return sortedSet().filter { now.toLocalTime().isAfter(it.startDate?.toLocalTime()) }
    }

}