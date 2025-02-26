package dev.priporov.ycalendar.list

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.ui.components.JBList
import dev.priporov.ycalendar.dto.EventDataDto

@Service(Service.Level.PROJECT)
class CalendarList : JBList<EventDataDto>() {

}