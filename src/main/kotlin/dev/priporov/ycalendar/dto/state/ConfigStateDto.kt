package dev.priporov.ycalendar.dto.state

class ConfigStateDto {

    var login: String? = null
    var enabled: Boolean = true
    var notificationFrequencyTime: Long = 30      // sec
    var synchronizationFrequencyTime: Long = 5    // min

}