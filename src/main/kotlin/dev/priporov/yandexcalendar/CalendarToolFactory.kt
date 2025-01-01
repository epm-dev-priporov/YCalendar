package dev.priporov.yandexcalendar

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import dev.priporov.yandexcalendar.panel.CalendarPanel

class CalendarToolFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()

        val content = contentFactory.createContent(CalendarPanel(), "", false)
        toolWindow.contentManager.addContent(content)
    }

}