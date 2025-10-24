package dev.priporov.ycalendar.client

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import dev.priporov.ycalendar.service.StateService

@Service
class UrlProvider {

    fun getUlr(): String {
        return service<StateService>().state.url!!
    }

}