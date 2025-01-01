package dev.priporov.yandexcalendar.client.yandex

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.intellij.openapi.components.Service
import dev.priporov.yandexcalendar.client.CaldavRequestTemplate
import dev.priporov.yandexcalendar.client.HttpReport
import dev.priporov.yandexcalendar.dto.MultistatusDto
import org.apache.http.HttpHeaders
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.HttpClientBuilder

private const val yandexUrl = "https://caldav.yandex.ru/"

@Service
class YandexRestClient {

    private val client = HttpClientBuilder.create().build()

    private var mapper: XmlMapper = XmlMapper.builder()
        .defaultUseWrapper(false) // enable/disable Features, change AnnotationIntrospector
        .build()

    fun getTodayEvents(): MultistatusDto? {

        val url = "${yandexUrl}calendars/$email/events-default"
        val request = HttpReport(
            url,
            CaldavRequestTemplate.template(),
            mapOf(
                Pair(HttpHeaders.AUTHORIZATION, authHeader),
                Pair(HttpHeaders.CONTENT_TYPE, "application/xml; charset=\"utf-8")
            )
        )

        val content = client.execute(request, BasicResponseHandler())

        return mapper.readValue(content, MultistatusDto::class.java)
    }
}