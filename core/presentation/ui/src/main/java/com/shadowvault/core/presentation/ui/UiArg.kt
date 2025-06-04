package com.shadowvault.core.presentation.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class UiArg {
    @Serializable
    data class StringArg(val value: String) : UiArg()

    @Serializable
    data class IntArg(val value: Int) : UiArg()

    @Serializable
    data class FloatArg(val value: Float) : UiArg()

    @Serializable
    data class BooleanArg(val value: Boolean) : UiArg()
}

fun UiArg.unwrap(): Any {
    return when (this) {
        is UiArg.StringArg -> value
        is UiArg.IntArg -> value
        is UiArg.FloatArg -> value
        is UiArg.BooleanArg -> value
    }
}
