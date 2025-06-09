package com.shadowvault.core.domain.util

sealed class FlexibleValue<out T> {
    data class Object<T>(val value: T) : FlexibleValue<T>()
    data class StringVal(val value: String) : FlexibleValue<Nothing>()
    data object None : FlexibleValue<Nothing>()
}
