package com.shadowvault.core.data.util

import com.shadowvault.core.domain.util.FlexibleValue
import kotlinx.serialization.Serializable

@Serializable(with = ObjectOrStringSerializer::class)
sealed class FlexibleValueDto<out T> {
    data class Object<T>(val value: T) : FlexibleValueDto<T>()
    data class StringVal(val value: String) : FlexibleValueDto<Nothing>()
    data object None : FlexibleValueDto<Nothing>()
}

fun <T, R> FlexibleValueDto<T>.toFlexibleValue(mapper: (T) -> R): FlexibleValue<R> = when (this) {
    is FlexibleValueDto.Object -> FlexibleValue.Object(mapper(value))
    is FlexibleValueDto.StringVal -> FlexibleValue.StringVal(value)
    FlexibleValueDto.None -> FlexibleValue.None
}