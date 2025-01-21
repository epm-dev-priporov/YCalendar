package dev.priporov.ycalendar.service

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.util.concurrency.AppExecutorUtil
import java.time.LocalDateTime
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

@Service
class SchedulerService {
    private var calendarSyncFuture: ScheduledFuture<*>? = null
    private var notificationFeature: ScheduledFuture<*>? = null

    fun startCalendarSynchronization(synchronizationFrequencyTime: Long) {
        calendarSyncFuture?.cancel(true)
        calendarSyncFuture = AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(
            {
                service<CalendarService>().updateCalendar()
            },
            0,
            synchronizationFrequencyTime,
            TimeUnit.MINUTES
        )
    }

    fun startNotification(notificationFrequencyTime: Long) {
        notificationFeature?.cancel(true)
        notificationFeature = AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(
            {
                service<NotificationService>().run()
            },
            5,
            notificationFrequencyTime,
            TimeUnit.SECONDS
        )
    }
}
