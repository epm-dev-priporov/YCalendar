package dev.priporov.ycalendar.panel.settings

import com.intellij.openapi.components.service
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import dev.priporov.ycalendar.dto.state.ConfigStateDto
import dev.priporov.ycalendar.service.SchedulerService
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.text.NumberFormat
import javax.swing.JFormattedTextField
import javax.swing.JPanel
import javax.swing.SwingConstants
import javax.swing.text.DefaultFormatterFactory
import javax.swing.text.NumberFormatter

class NotificationConfigPanel : JPanel() {

    val enableNotificationCheckBox = JBCheckBox("Enable notification", true).apply {
        setHorizontalTextPosition(SwingConstants.RIGHT)
    }

    val notificationFrequencyTextField = JFormattedTextField().apply {
        toolTipText = "Frequency of events checking."
        formatterFactory = DefaultFormatterFactory(NumberFormatter(NumberFormat.getNumberInstance()))
        text = "30"
    }

    val enablePopUpNotificationCheckBox = JBCheckBox("Enable popup dialog notification", true).apply {
        setHorizontalTextPosition(SwingConstants.RIGHT)
    }

    init {
        enableNotificationCheckBox.addChangeListener {
            enablePopUpNotificationCheckBox.isEnabled = enableNotificationCheckBox.isSelected
            notificationFrequencyTextField.isEnabled = enableNotificationCheckBox.isSelected
        }

        layout = GridBagLayout()
        val constraints = GridBagConstraints().apply {
            gridx = 0
            gridy = 0
            gridwidth = 2
            anchor = GridBagConstraints.LINE_START
        }
        add(enableNotificationCheckBox, constraints)

        constraints.apply {
            gridx = 2
            gridy = 0
            gridwidth = 2
        }
        add(enablePopUpNotificationCheckBox, constraints)

        constraints.apply {
            gridx = 0
            gridy = 1
            gridwidth = 1
            ipadx = 5
        }
        add(JBLabel("Frequency of events checking:"), constraints)
        constraints.apply {
            gridx = 1
            gridy = 1
        }
        add(notificationFrequencyTextField, constraints)
        constraints.apply {
            gridx = 2
            gridy = 1
            ipadx = 0
        }
        add(JBLabel("sec"), constraints)

        constraints.apply {
            gridx = 4
            gridy = 4
            weighty = 1.0
            weightx = 1.0
        }
        add(JPanel(), constraints)
    }

    fun apply(state: ConfigStateDto) {
        state.enabled = enableNotificationCheckBox.isSelected
        val newNotificationFrequencyTextField = notificationFrequencyTextField.text.toLong()
        if (newNotificationFrequencyTextField != state.notificationFrequencyTime) {
            state.notificationFrequencyTime = newNotificationFrequencyTextField
            service<SchedulerService>().startNotification(state.notificationFrequencyTime)
        }
    }

    fun applyState(state: ConfigStateDto) {
        enableNotificationCheckBox.isSelected = state.enabled
        notificationFrequencyTextField.text = state.notificationFrequencyTime.toString()
    }
}
