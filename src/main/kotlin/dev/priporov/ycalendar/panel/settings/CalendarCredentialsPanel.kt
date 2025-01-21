package dev.priporov.ycalendar.panel.settings

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.JBTextField
import com.intellij.ui.util.preferredWidth
import dev.priporov.ycalendar.dto.state.ConfigStateDto
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel

class CalendarCredentialsPanel : JPanel() {

    fun apply(state: ConfigStateDto) {
        state.login = loginField.text

        PasswordSafe.instance.setPassword(
            CredentialAttributes(ConfigStateDto::class.java.name, state.login, ConfigStateDto::class.java),
            passwordField.password.joinToString("")
        )
    }

    fun applyState(state: ConfigStateDto) {
        loginField.text = state.login

        passwordField.text = PasswordSafe.instance.getPassword(
            credentialAttributes(state)
        )
    }

    private fun credentialAttributes(state: ConfigStateDto) = CredentialAttributes(
        ConfigStateDto::class.java.name,
        state.login,
        ConfigStateDto::class.java
    )

    // add link https://id.yandex.ru/security/app-passwords?utm_source=blog360&utm_medium=referral
    val passwordLabel = JBLabel("Password")
    val loginField = JBTextField().apply { toolTipText = "Yandex account login or email" }
    val passwordField = JBPasswordField().apply { toolTipText = "Password" }
    val loginLabel = JBLabel("Login")

    init {
        layout = GridBagLayout()
        val constraints = GridBagConstraints()
        passwordField.preferredWidth = 270
        loginField.preferredWidth = 270
        constraints.anchor = GridBagConstraints.LINE_START
        constraints.anchor = GridBagConstraints.WEST
        constraints.gridx = 0
        constraints.gridy = 1
        constraints.ipadx = 5
        add(passwordLabel, constraints)
        constraints.anchor = GridBagConstraints.WEST
        constraints.gridx = 0
        constraints.gridy = 2
        add(loginLabel, constraints)

        constraints.gridx = 1
        constraints.gridy = 1
        constraints.ipadx = 0
        add(passwordField, constraints)

        constraints.gridx = 1
        constraints.gridy = 2
        add(loginField, constraints)

        constraints.weighty = 1.0
        constraints.weightx = 1.0
        constraints.gridx = 2
        constraints.gridy = 2
        add(JLabel(" "), constraints)
    }
}