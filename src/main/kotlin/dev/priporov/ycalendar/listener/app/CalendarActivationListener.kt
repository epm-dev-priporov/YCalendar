package dev.priporov.ycalendar.listener.app

import com.intellij.openapi.application.ApplicationActivationListener
import com.intellij.openapi.components.service
import com.intellij.openapi.wm.IdeFrame
import com.intellij.util.concurrency.AppExecutorUtil
import dev.priporov.ycalendar.list.CalendarList
import dev.priporov.ycalendar.service.CalendarService

class CalendarActivationListener : ApplicationActivationListener {

override fun applicationActivated(ideFrame: IdeFrame) {
        AppExecutorUtil.getAppExecutorService().execute {
            service<CalendarService>().updateCalendar()
        }
    }

}