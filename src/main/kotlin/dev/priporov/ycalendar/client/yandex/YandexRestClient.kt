package dev.priporov.ycalendar.client.yandex

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import dev.priporov.ycalendar.client.CaldavRequestTemplate
import dev.priporov.ycalendar.client.HttpReport
import dev.priporov.ycalendar.client.UrlProvider
import dev.priporov.ycalendar.dto.EventDataDto
import dev.priporov.ycalendar.dto.state.ConfigStateDto
import dev.priporov.ycalendar.service.StateService
import org.apache.http.HttpHeaders
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.HttpClientBuilder
import java.nio.charset.Charset
import java.util.*

@Service
class YandexRestClient {
    private val client = HttpClientBuilder.create().build()

    fun getTodayEvents(): Set<EventDataDto> {
        val email = getLogin()
        val password = getPassword()

        if (email.isNullOrBlank() || password.isNullOrBlank()) {
            return emptySet()
        }

        val secret = Base64.getEncoder().encodeToString(
            "$email:$password".toByteArray(Charset.forName("UTF-8"))
        )

        val url = service<UrlProvider>().getUlr()
        val request = HttpReport(
            url,
            CaldavRequestTemplate.template(),
            mapOf(
                Pair(HttpHeaders.AUTHORIZATION, "Basic ${secret}"),
                Pair("Depth", "1")
            )
        )

        val content = client.execute(request, BasicResponseHandler())

        return CaldavParser.toEvents(content) ?: emptySet()
    }

    private fun getLogin(): String? {
        val state = service<StateService>().state
        return state.login?.trim()
    }

    private fun getPassword(): String? {
        val state = service<StateService>().state

        return PasswordSafe.instance.getPassword(
            CredentialAttributes(ConfigStateDto::class.java.name, state.login, ConfigStateDto::class.java)
        )?.trim()
    }

}