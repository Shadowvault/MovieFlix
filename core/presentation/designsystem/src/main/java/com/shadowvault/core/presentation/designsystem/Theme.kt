package com.shadowvault.core.presentation.designsystem

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme

val LightColorScheme = lightColorScheme(
    primary = DeepRed,
    onPrimary = PureWhite,
    primaryContainer = MutedPeach,
    onPrimaryContainer = DarkMaroon,

    secondary = MutedTeal,
    onSecondary = DarkBackground,
    secondaryContainer = LightGrayVariant,
    onSecondaryContainer = DeepTeal,

    tertiary = MediumPurple,
    onTertiary = PureWhite,
    tertiaryContainer = SoftLavender,
    onTertiaryContainer = DarkLavender,

    background = PureWhite,
    onBackground = DarkGray,

    surface = PureWhite,
    onSurface = DarkGray,

    surfaceVariant = LightGray,
    onSurfaceVariant = MediumGrayShade,

    outline = MediumGray,
    inverseSurface = DarkCharcoal,
    inverseOnSurface = LightGrayVerySoft,
    inversePrimary = MutedPeach
)

val DarkColorScheme = darkColorScheme(
    primary = MutedPeach,
    onPrimary = DarkMaroon,
    primaryContainer = DeepRed,
    onPrimaryContainer = PureWhite,

    secondary = BrightTeal,
    onSecondary = DarkBackground,
    secondaryContainer = DeepTeal,
    onSecondaryContainer = LightGraySoft,

    tertiary = VibrantPurple,
    onTertiary = DarkLavender,
    tertiaryContainer = DarkLavender,
    onTertiaryContainer = SoftLavender,

    background = DarkBackground,
    onBackground = LightGraySoft,

    surface = DarkBackground,
    onSurface = LightGraySoft,

    surfaceVariant = MediumGrayShade,
    onSurfaceVariant = LightGrayVariant,

    outline = DarkGrayShade,
    inverseSurface = LightGraySoft,
    inverseOnSurface = DarkBackground,
    inversePrimary = DeepRed
)


@Composable
fun MovieFlixTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}