package dev.priporov.ycalendar.panel.settings

import com.intellij.openapi.components.Service
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.ui.TitledSeparator
import dev.priporov.ycalendar.dto.state.ConfigStateDto
import javax.swing.JPanel


@Service
class SettingsPanel : JPanel() {

    private val calendarCredentialsPanel = CalendarCredentialsPanel()
    private val notificationConfigPanel = NotificationConfigPanel()
    private val calendarConfigPanel = CalendarConfigPanel()
    private val calendarSourcePanel = CalendarSourcePanel()

    init {
        layout = VerticalFlowLayout()
        add(TitledSeparator("Calendar Configurations"))
        add(calendarSourcePanel)
        add(calendarCredentialsPanel)
        add(calendarConfigPanel)
        add(TitledSeparator("Notification Configurations"))
        add(notificationConfigPanel)
    }

    fun apply(state: ConfigStateDto) {
        calendarSourcePanel.apply(state)
        calendarCredentialsPanel.apply(state)
        notificationConfigPanel.apply(state)
        calendarConfigPanel.apply(state)
    }

    fun applyState(state: ConfigStateDto): JPanel {
        calendarSourcePanel.applyState(state)
        calendarCredentialsPanel.applyState(state)
        notificationConfigPanel.applyState(state)
        calendarConfigPanel.applyState(state)

        return this
    }

}
