package dev.priporov.ycalendar

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import dev.priporov.ycalendar.list.CalendarList
import dev.priporov.ycalendar.list.CalendarListCellRenderer
import dev.priporov.ycalendar.list.CalendarListModel
import dev.priporov.ycalendar.listener.mouse.ListMouseListener
import dev.priporov.ycalendar.panel.CalendarPanel

class CalendarToolFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()
        val calendarList = project.getService(CalendarList::class.java)

        calendarList.apply {
            model = service<CalendarListModel>()
            cellRenderer = service<CalendarListCellRenderer>()
            addMouseListener(ListMouseListener(this))
        }

        val content = contentFactory.createContent(
            CalendarPanel(calendarList),
            "",
            false
        )
        toolWindow.contentManager.addContent(content)
    }

}