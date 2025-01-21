package dev.priporov.ycalendar.dialog

import com.intellij.ui.components.labels.LinkLabel
import dev.priporov.ycalendar.dto.EventDataDto
import dev.priporov.ycalendar.listener.mouse.LinkMouseListener
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.time.format.DateTimeFormatter
import javax.swing.JLabel
import javax.swing.JPanel

private val START_DATE_PATTERN = DateTimeFormatter.ofPattern("d MMMM yyyy  HH:mm")
private val END_DATE_PATTERN = DateTimeFormatter.ofPattern("HH:mm")
private val urlRegexp = Regex("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)")

class EventNotificationPanel(event: EventDataDto) : JPanel() {


    init {
        layout = GridBagLayout()
        val constraints = GridBagConstraints()

        constraints.gridx = 0
        constraints.gridy = 0
        constraints.anchor = GridBagConstraints.WEST
        constraints.ipady = 15
        add(JLabel("<html><b>${event.name}</b></html>"), constraints)

        constraints.gridx = 0
        constraints.gridy = 1
        constraints.anchor = GridBagConstraints.WEST



        val startDate = event.startDate?.format(START_DATE_PATTERN)
        val endDate = event.endDate?.format(END_DATE_PATTERN)

        add(JLabel("$startDate - $endDate"), constraints)

        if (event.conference != null) {
            constraints.gridx = 0
            constraints.gridy = 2
            constraints.anchor = GridBagConstraints.WEST
            constraints.ipady = 15

            val linkLabel = LinkLabel<Any>("${event.conference}", event.conferenceType.icon).apply {
                addMouseListener(LinkMouseListener(event))
            }

            add(linkLabel, constraints)
        }

        var description = event.description
        if (description != null) {
            when {
                description.length > 300 -> preferredSize = Dimension(600, 650)
                description.length > 200 -> preferredSize = Dimension(500, 525)
                description.length > 100 -> preferredSize = Dimension(400, 450)
            }

            constraints.gridx = 0
            constraints.gridy = 3
            constraints.weightx = 0.1
            constraints.weighty = 0.9
            description = urlRegexp.replace(description) { i -> "<a href=\"${i.value}\">${i.value}</a>" }
            constraints.fill = GridBagConstraints.HORIZONTAL
            add(JLabel("<html>${description.replace("\n", "<br>")}</html>"), constraints)
        } else {
            constraints.gridx = 0
            constraints.gridy = 3
            constraints.weightx = 0.1
            constraints.weighty = 0.1
            add(JLabel(), constraints)
        }
    }
}