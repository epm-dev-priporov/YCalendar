package dev.priporov.ycalendar.service

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import dev.priporov.ycalendar.dialog.NotificationDialog
import dev.priporov.ycalendar.dto.EventDataDto
import dev.priporov.ycalendar.list.CalendarListModel
import javax.swing.SwingUtilities

@Service
class NotificationService {

    private val stateService = service<StateService>()
    private val calendarListModel = service<CalendarListModel>()

    fun run() {
        if (!stateService.state.enabled) {
            return
        }
        showNotification(calendarListModel.getStartedElements())
        hideOldEvents(calendarListModel.getFinishedElements())
    }

    private fun hideOldEvents(events: List<EventDataDto>) {
        events.forEach { service<CalendarListModel>().removeElement(it) }
    }

    private fun showNotification(events: List<EventDataDto>) {
        if (events.isNotEmpty()) {
            val notNotifiedEvents = events.asSequence().filter { !it.notified.get() }.toList()
            if (!notNotifiedEvents.stream().allMatch { it.notified.get() }) {

                SwingUtilities.invokeLater {
                    NotificationDialog(notNotifiedEvents).show()
                }

                events.forEach { it.notified.set(true) }
            }
        }
    }

}