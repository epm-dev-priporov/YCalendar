package dev.priporov.ycalendar.listener.app

import com.intellij.ide.AppLifecycleListener
import com.intellij.openapi.components.service
import dev.priporov.ycalendar.service.SchedulerService
import dev.priporov.ycalendar.service.StateService

class AppActivationListener : AppLifecycleListener {

    override fun appFrameCreated(commandLineArgs: MutableList<String>) {

        service<SchedulerService>().startNotification(
            service<StateService>().state.notificationFrequencyTime
        )
        service<SchedulerService>().startCalendarSynchronization(
            service<StateService>().state.synchronizationFrequencyTime
        )
    }

}