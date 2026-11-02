package dev.priporov.ycalendar.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement


@JacksonXmlRootElement(localName = "multistatus")
@JsonIgnoreProperties(ignoreUnknown = true)
class MultistatusDto {
    @JacksonXmlProperty(localName = "response")
    @JsonProperty("response")
    @JacksonXmlElementWrapper(useWrapping = false)
    var response: List<ResponseDto>? = null
}


@JsonIgnoreProperties(ignoreUnknown = true)
class ResponseDto {
    @JsonProperty("propstat")
    var propstat: PropstatDto? = null
}

@JsonIgnoreProperties(ignoreUnknown = true)
class PropstatDto{
    @JsonProperty("prop")
    var prop:PropDto? = null
}

@JsonIgnoreProperties(ignoreUnknown = true)
class PropDto{
    @JsonProperty("calendar-data")
    var calendarData:CalendarDataDto? = null
}

@JsonIgnoreProperties(ignoreUnknown = true)
class CalendarDataDto{
    @JacksonXmlText
    var text: String? = null
}

