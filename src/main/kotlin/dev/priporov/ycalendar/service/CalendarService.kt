package dev.priporov.ycalendar.service

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import dev.priporov.ycalendar.client.yandex.YandexRestClient
import dev.priporov.ycalendar.list.CalendarListModel

@Service
class CalendarService {

    fun updateCalendar() {
        val enabled = service<StateService>().state.enabled

        if (enabled) {
            service<CalendarListModel>().syncElements(service<YandexRestClient>().getTodayEvents())
        } else {
            service<CalendarListModel>().removeAllElements()
        }
    }

}