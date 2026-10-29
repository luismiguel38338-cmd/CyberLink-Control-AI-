package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CyberColorScheme = darkColorScheme(
    primary = CyberNeonGreen,
    onPrimary = CyberBackground,
    primaryContainer = CyberSurfaceElevated,
    onPrimaryContainer = CyberNeonGreen,
    secondary = CyberElectricCyan,
    onSecondary = CyberBackground,
    secondaryContainer = CyberSurfaceVariant,
    onSecondaryContainer = CyberElectricCyan,
    tertiary = CyberAlertRed,
    onTertiary = CyberBackground,
    background = CyberBackground,
    onBackground = CyberTextPrimary,
    surface = CyberSurface,
    onSurface = CyberTextPrimary,
    surfaceVariant = CyberSurfaceVariant,
    onSurfaceVariant = CyberTextSecondary,
    outline = CyberBorder,
    outlineVariant = CyberBorderGlow
)

@Composable
fun CyberLinkTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CyberColorScheme,
        typography = Typography,
        content = content
    )
}
