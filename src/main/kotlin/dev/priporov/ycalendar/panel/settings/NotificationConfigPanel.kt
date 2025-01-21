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

    val notificationPeriodTextField = JFormattedTextField().apply {
        toolTipText = "Notify before the meeting. Value 0 means beginning of the meeting,"
        formatterFactory = DefaultFormatterFactory(NumberFormatter(NumberFormat.getNumberInstance()))
        text = "0"
    }

    val enablePopUpNotificationCheckBox = JBCheckBox("Enable popup dialog notification", true).apply {
        setHorizontalTextPosition(SwingConstants.RIGHT)
    }

    init {
        enableNotificationCheckBox.addChangeListener {
            enablePopUpNotificationCheckBox.isEnabled = enableNotificationCheckBox.isSelected
            notificationPeriodTextField.isEnabled = enableNotificationCheckBox.isSelected
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
        add(JBLabel("Notify before the meeting:"), constraints)
        constraints.apply {
            gridx = 1
            gridy = 1
        }
        add(notificationPeriodTextField, constraints)
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
        val newNotificationPeriodTextField = notificationPeriodTextField.text.toLong()
        if (newNotificationPeriodTextField != state.notificationFrequencyTime) {
            state.notificationFrequencyTime = newNotificationPeriodTextField
            service<SchedulerService>().startNotification(state.notificationFrequencyTime)
        }
    }

    fun applyState(state: ConfigStateDto) {
        enableNotificationCheckBox.isSelected = state.enabled
        notificationPeriodTextField.text = state.notificationFrequencyTime.toString()
    }
}
