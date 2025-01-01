package dev.priporov.yandexcalendar.client

import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import java.net.URI

class HttpReport(url: String, body: String, headers: Map<String, String>) : HttpPost() {
    init {
        this.uri = URI(url)
        headers.forEach {
            this.addHeader(it.key, it.value)
        }
        this.entity = StringEntity(body)
    }

    override fun getMethod() = "REPORT"

}