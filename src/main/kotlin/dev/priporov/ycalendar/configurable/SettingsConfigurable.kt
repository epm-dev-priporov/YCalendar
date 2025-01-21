package dev.priporov.ycalendar.configurable

import com.intellij.openapi.components.service
import com.intellij.openapi.options.SearchableConfigurable
import dev.priporov.ycalendar.panel.settings.SettingsPanel
import dev.priporov.ycalendar.service.StateService
import javax.swing.JComponent


class SettingsConfigurable : SearchableConfigurable {

    override fun createComponent(): JComponent = service<SettingsPanel>()

    override fun isModified(): Boolean {
        return true
    }

    override fun apply() {
        val settingsPanel = service<SettingsPanel>()
        val state = service<StateService>().state
        settingsPanel.apply(state)
    }

    override fun getDisplayName(): String = "Yandex Calendar"

    override fun getId(): String = "dev.priporov.ycalendar.configurable.SettingsConfigurable"

}