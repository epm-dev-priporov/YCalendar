package dev.priporov.ycalendar.dialog

import com.intellij.openapi.ui.DialogWrapper
import dev.priporov.ycalendar.dto.EventDataDto
import org.jdesktop.swingx.VerticalLayout
import javax.swing.JPanel

class NotificationDialog(notNotifiedEvents: List<EventDataDto>) : DialogWrapper(true) {

    private val panel = JPanel().apply {

    }

    init {
        init()
        title = notNotifiedEvents.map { it.name }.joinToString(",")
        panel.layout = VerticalLayout(2)
        notNotifiedEvents.forEach { event ->
            panel.add(EventNotificationPanel(event))
        }
    }

    override fun createCenterPanel() = panel
}

