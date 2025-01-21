package dev.priporov.ycalendar.listener.mouse

import dev.priporov.ycalendar.dialog.NotificationDialog
import dev.priporov.ycalendar.dto.EventDataDto
import dev.priporov.ycalendar.list.CalendarList
import java.awt.event.MouseEvent
import java.awt.event.MouseListener


class ListMouseListener(private val list: CalendarList) : MouseListener {
    override fun mouseClicked(e: MouseEvent) {
        if (!isDoubledClickedByNode(e)) {
            return
        }
        val event = list.selectedValue as EventDataDto
        NotificationDialog(listOf(event)).show()
    }

    override fun mousePressed(e: MouseEvent?) {

    }

    override fun mouseReleased(e: MouseEvent?) {

    }

    override fun mouseEntered(e: MouseEvent?) {

    }

    override fun mouseExited(e: MouseEvent?) {

    }

    private fun isDoubledClickedByNode(e: MouseEvent): Boolean = e.clickCount == 2

}