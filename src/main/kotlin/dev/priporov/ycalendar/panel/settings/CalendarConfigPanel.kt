package dev.priporov.ycalendar.panel.settings

import com.intellij.openapi.components.service
import com.intellij.ui.components.JBLabel
import dev.priporov.ycalendar.dto.state.ConfigStateDto
import dev.priporov.ycalendar.service.SchedulerService
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.text.NumberFormat
import javax.swing.JFormattedTextField
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.text.DefaultFormatterFactory
import javax.swing.text.NumberFormatter

class CalendarConfigPanel : JPanel() {

    val synchronizationFrequencyTextField = JFormattedTextField().apply {
        toolTipText = "Synchronization frequency"
        formatterFactory = DefaultFormatterFactory(NumberFormatter(NumberFormat.getNumberInstance()))
        text = "5"
    }

    val synchronizationFrequencyLabel = JBLabel("Synchronization frequency")

    init {
        layout = GridBagLayout()
        val constraints = GridBagConstraints()
        constraints.apply {
            anchor = GridBagConstraints.WEST
            gridx = 0
            gridy = 0
        }
        add(synchronizationFrequencyLabel, constraints)
        constraints.apply {
            anchor = GridBagConstraints.WEST
            gridx = 1
            gridy = 0
        }
        add(synchronizationFrequencyTextField, constraints)

        constraints.apply {
            gridx = 2
            gridy = 0
            ipadx = 0
        }
        add(JBLabel("min"), constraints)

        constraints.weighty = 1.0
        constraints.weightx = 1.0
        constraints.gridx = 2
        constraints.gridy = 2
        add(JLabel(" "), constraints)
    }

    fun apply(state: ConfigStateDto) {
        val newValue = synchronizationFrequencyTextField.text.toLong()
        if (newValue != state.synchronizationFrequencyTime) {
            state.synchronizationFrequencyTime = newValue
            service<SchedulerService>().startCalendarSynchronization(state.synchronizationFrequencyTime)
        }
    }

    fun applyState(state: ConfigStateDto) {
        synchronizationFrequencyTextField.text = state.synchronizationFrequencyTime.toString()
    }

}
