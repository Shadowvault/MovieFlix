package com.shadowvault.core.data.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

class ObjectOrStringSerializer<T>(
    private val dataSerializer: KSerializer<T>
) : KSerializer<FlexibleValueDto<T>> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("FlexibleValue")

    override fun deserialize(decoder: Decoder): FlexibleValueDto<T> {
        val input = decoder as? JsonDecoder ?: error("Only JSON supported")
        val element = input.decodeJsonElement()

        return when {
            element is JsonObject -> {
                val obj = input.json.decodeFromJsonElement(dataSerializer, element)
                FlexibleValueDto.Object(obj)
            }
            element is JsonPrimitive && element.isString -> {
                FlexibleValueDto.StringVal(element.content)
            }
            element is JsonNull -> FlexibleValueDto.None
            else -> throw SerializationException("Unsupported type for FlexibleValue: $element")
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: FlexibleValueDto<T>) {
        val jsonEncoder = encoder as? JsonEncoder ?: error("Only JSON supported")
        when (value) {
            is FlexibleValueDto.Object -> jsonEncoder.encodeSerializableValue(dataSerializer, value.value)
            is FlexibleValueDto.StringVal -> jsonEncoder.encodeString(value.value)
            is FlexibleValueDto.None -> jsonEncoder.encodeNull()
        }
    }
}
