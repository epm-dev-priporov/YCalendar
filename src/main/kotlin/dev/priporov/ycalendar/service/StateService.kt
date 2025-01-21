package dev.priporov.ycalendar.service

import com.intellij.openapi.components.*
import dev.priporov.ycalendar.dto.state.ConfigStateDto
import dev.priporov.ycalendar.panel.settings.SettingsPanel

@State(
    name = "dev.priporov.ycalendar.state.ConfigState",
    storages = [Storage("ycalendar.xml")]
)
@Service
class StateService: PersistentStateComponent<ConfigStateDto> {

    private var state = ConfigStateDto()

    override fun getState() = state

    override fun loadState(loadedState: ConfigStateDto) {
        service<SettingsPanel>().applyState(loadedState)
        this.state = loadedState
    }
}

