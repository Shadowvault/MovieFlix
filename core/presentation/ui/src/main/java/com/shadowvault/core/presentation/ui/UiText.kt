package com.shadowvault.core.presentation.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed interface UiText {
    @Serializable
    data class DynamicString(val value: String) : UiText
    @Serializable
    class StringResource(
        @StringRes val id: Int,
        @Contextual val args: List<UiArg> = listOf()
    ) : UiText

    @Suppress("SpreadOperator")
    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(id, *args.map { it.unwrap() }.toTypedArray())
        }
    }

    @Suppress("SpreadOperator")
    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args.map { it.unwrap() }.toTypedArray())
        }
    }
}

fun <T : Any> listOfUiArgs(vararg values: T): List<UiArg> {
    return values.map {
        when (it) {
            is String -> UiArg.StringArg(it)
            is Int -> UiArg.IntArg(it)
            is Float -> UiArg.FloatArg(it)
            is Boolean -> UiArg.BooleanArg(it)
            else -> throw IllegalArgumentException("Unsupported type: ${it::class}")
        }
    }
}
