package dev.priporov.ycalendar.listener.mouse

import com.intellij.ide.browsers.BrowserLauncher
import dev.priporov.ycalendar.dto.EventDataDto
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class LinkMouseListener(private val event: EventDataDto) : MouseListener {
    override fun mouseClicked(e: MouseEvent) {
        val uri = event.conference
        if (uri == null) {
            return
        }
        BrowserLauncher.instance.browse(uri)
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