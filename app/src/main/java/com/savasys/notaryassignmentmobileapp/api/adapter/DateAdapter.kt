package com.savasys.notaryassignmentmobileapp.api.adapter

import com.squareup.moshi.*
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter : JsonAdapter<Date>() {
    private val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            dateFormat.parse(dateAsString)
        } catch (e: Exception) {
            println("Unable to parse date from Json: $e")
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            writer.value(value.toString())
        }
    }

    companion object {
        const val SERVER_FORMAT = ("yyyy-MM-dd'T'HH:mm")
    }
}