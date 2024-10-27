package com.adi.magicspacex.models.api.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

/**
 * Json adapter that returns empty string when encountered null object.
 */
class NullStringAdapter : JsonAdapter<String>() {
    @FromJson
    override fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }

    override fun toJson(jsonWriter: JsonWriter, value: String?) {
        jsonWriter.value(value)
    }
}