package dev.priporov.ycalendar.panel

import com.intellij.ui.components.JBList
import dev.priporov.ycalendar.dto.EventDataDto
import java.awt.BorderLayout
import javax.swing.JPanel


class CalendarPanel(private val list: JBList<EventDataDto>) : JPanel() {

    init {
        layout = BorderLayout().apply {
            addLayoutComponent(list, BorderLayout.CENTER)
        }
        add(list)
    }

}