package dev.priporov.ycalendar.list

import com.intellij.openapi.components.Service
import dev.priporov.ycalendar.dto.EventDataDto
import java.awt.Color
import java.awt.Component
import java.time.LocalDateTime
import javax.swing.DefaultListCellRenderer
import javax.swing.JLabel
import javax.swing.JList

private val selectedRed = Color(175, 0, 0)
private val selectedOrange = Color(175, 140, 0)

@Service
class CalendarListCellRenderer : DefaultListCellRenderer() {

    override fun getListCellRendererComponent(
        list: JList<*>?,
        value: Any?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {

        return (super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus) as JLabel).apply {
            val event = value as EventDataDto
            customize(event, isSelected)

            return this
        }
    }

    private fun customize(event: EventDataDto, isSelected: Boolean) {
        val now = LocalDateTime.now()

        setIcon(event.conferenceType.icon)

        val inProgress = isInProgress(now, event)
        val upcoming = isUpcoming(now.plusMinutes(30), event, now)

        if (inProgress) {
            if (isSelected) {
                background = selectedRed
            } else {
                background = Color.RED
            }
            foreground = Color.BLACK
        } else if (upcoming) {
            if (isSelected) {
                background = selectedOrange
            } else {
                background = Color.ORANGE
            }
            foreground = Color.BLACK
        }
    }

    private fun isUpcoming(time: LocalDateTime, event: EventDataDto, now: LocalDateTime) =
        time.toLocalTime().isAfter(event.startDate?.toLocalTime()) && now.toLocalTime().isBefore(event.endDate?.toLocalTime())

    private fun isInProgress(now: LocalDateTime, event: EventDataDto) =
        now.toLocalTime().isAfter(event.startDate?.toLocalTime()) && now.toLocalTime().isBefore(event.endDate?.toLocalTime())

//fun method(event: EventDataDto): JPanel {
//    return JPanel().apply {
//        val now = LocalDateTime.now()
//
//        setIcon(event.conferenceType.icon)
//
//        val inProgress = isInProgress(now, event)
//        val upcoming = isUpcoming(now.minusMinutes(30), event, now)
//
//        if (inProgress) {
//            background = Color.RED
//            foreground = Color.BLACK
//        } else if (upcoming) {
//            background = Color.ORANGE
//            foreground = Color.BLACK
//        }
//        val eventTextLabel = JLabel().apply {
//            text = event.toString()
//            if (inProgress || upcoming) {
//                foreground = Color.BLACK
//            }
//        }
//        if(event.conference != null) {
//
//        }
//        val joinLabel = HyperlinkLabel(event.conference.toString()).apply {
//            if (inProgress || upcoming) {
//                foreground = Color.BLUE
//            }
//        }
//
//        layout = VerticalLayout(5).apply {
//            add(eventTextLabel)
//            if(event.conference != null) {
//                add(joinLabel)
//            }
//        }
//        add(eventTextLabel)
//        if(event.conference != null) {
//            add(joinLabel)
//        }
//    }
//}


}


//