package dev.priporov.ycalendar.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText


@JsonIgnoreProperties(ignoreUnknown = true)
class MultistatusResponseDto {
    @JsonProperty("multistatus")
    var multistatus: MultistatusDto? = null
}

@JsonIgnoreProperties(ignoreUnknown = true)
class MultistatusDto {
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

