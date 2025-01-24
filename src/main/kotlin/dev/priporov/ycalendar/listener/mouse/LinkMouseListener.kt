package dev.priporov.ycalendar.listener.mouse

import com.intellij.ide.browsers.BrowserLauncher
import dev.priporov.ycalendar.dto.EventDataDto
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.net.URI

class LinkMouseListener(private val uri: URI?) : MouseListener {
    override fun mouseClicked(e: MouseEvent) {

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