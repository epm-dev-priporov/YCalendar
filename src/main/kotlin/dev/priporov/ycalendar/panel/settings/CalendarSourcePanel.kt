package dev.priporov.ycalendar.panel.settings

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.JBTextField
import com.intellij.ui.util.preferredWidth
import dev.priporov.ycalendar.dto.state.ConfigStateDto
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel

class CalendarSourcePanel : JPanel() {

    val urlField = JBTextField().apply {
        toolTipText = "Url"
        preferredWidth = 370
    }
    val urlLabel = JBLabel("Url")

    init {
        layout = GridBagLayout()
        val constraints = GridBagConstraints()

        constraints.apply {
            anchor = GridBagConstraints.LINE_START
            gridx = 0
            gridy = 1
            ipadx = 5
        }
        add(urlLabel, constraints)
        constraints.apply {
            gridx = 1
            gridy = 1
            ipadx = 0
        }
        add(urlField, constraints)
        constraints.weighty = 1.0
        constraints.weightx = 1.0
        constraints.gridx = 2
        constraints.gridy = 2
        add(JLabel(" "), constraints)
    }

    fun apply(state: ConfigStateDto) {
        state.url = urlField.text
    }

    fun applyState(state: ConfigStateDto) {
        urlField.text = state.url
    }

}