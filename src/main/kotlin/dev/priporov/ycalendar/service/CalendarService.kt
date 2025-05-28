package dev.priporov.ycalendar.service

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.jetbrains.rd.util.getLogger
import com.jetbrains.rd.util.warn
import dev.priporov.ycalendar.client.yandex.YandexRestClient
import dev.priporov.ycalendar.list.CalendarListModel

@Service
class CalendarService {

    fun updateCalendar() {
        val enabled = service<StateService>().state.enabled

        if (enabled) {
            try {
                service<CalendarListModel>().syncElements(service<YandexRestClient>().getTodayEvents())
            } catch (e: Exception) {
                try {
                    service<CalendarListModel>().syncElements(service<YandexRestClient>().getTodayEvents())
                } catch (e: Exception) {
                    getLogger<CalendarService>().warn { "Error while updating calendar: '${e.message}'" }
                }
            }
        } else {
            service<CalendarListModel>().removeAllElements()
        }
    }

}