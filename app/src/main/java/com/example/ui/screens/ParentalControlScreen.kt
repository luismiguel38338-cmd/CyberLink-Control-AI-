package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LinkedDevice
import com.example.data.model.ParentalRule
import com.example.data.model.RuleCategory
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CyberLinkViewModel

@Composable
fun ParentalControlScreen(
    viewModel: CyberLinkViewModel,
    rules: List<ParentalRule>,
    devices: List<LinkedDevice>,
    modifier: Modifier = Modifier
) {
    var selectedDevice by remember { mutableStateOf(devices.firstOrNull()) }
    var dailyLimitSlider by remember(selectedDevice) {
        mutableFloatStateOf((selectedDevice?.dailyLimitMinutes ?: 240).toFloat())
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBackground)
            .padding(16.dp)
            .testTag("parental_control_screen")
    ) {
        CyberHeaderBar(
            title = "CONTROL PARENTAL INTELIGENTE",
            subtitle = "HORARIOS & NORMAS FAMILIARES",
            canNavigateBack = true,
            onNavigateBack = { viewModel.navigateBack() }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Device Selector Bar
        if (devices.isNotEmpty()) {
            Text(
                text = "SELECCIONAR DISPOSITIVO SUPERVISADO:",
                color = CyberTextSecondary,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                devices.forEach { dev ->
                    val isSelected = selectedDevice?.id == dev.id
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedDevice = dev },
                        label = { Text(dev.name, fontSize = 11.sp, maxLines = 1) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CyberNeonGreen.copy(alpha = 0.2f),
                            selectedLabelColor = CyberNeonGreen,
                            containerColor = CyberSurfaceElevated,
                            labelColor = CyberTextSecondary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Native Android Policy Clarification Banner
        CyberCard(borderColor = CyberWarningAmber.copy(alpha = 0.6f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.WarningAmber,
                    contentDescription = null,
                    tint = CyberWarningAmber,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Diferenciación de Permisos del Sistema Operativo",
                        color = CyberWarningAmber,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Las restricciones de bloqueo de aplicaciones y toque de queda requieren el permiso Device Admin activo en el dispositivo Android para aplicarse de manera forzosa.",
                        color = CyberTextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Daily Limit Slider Card
            item {
                selectedDevice?.let { dev ->
                    CyberCard(glowEffect = true) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "LÍMITE DIARIO DE TIEMPO EN PANTALLA",
                                    color = CyberTextSecondary,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${dailyLimitSlider.toInt() / 60} horas y ${dailyLimitSlider.toInt() % 60} minutos",
                                    color = CyberNeonGreen,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            CyberBadge(
                                text = "HOY: ${dev.screenTimeMinutesToday}m",
                                color = if (dev.screenTimeMinutesToday > dailyLimitSlider) CyberAlertRed else CyberElectricCyan
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Slider(
                            value = dailyLimitSlider,
                            onValueChange = { dailyLimitSlider = it },
                            onValueChangeFinished = {
                                viewModel.updateDailyLimit(dev.id, dailyLimitSlider.toInt())
                            },
                            valueRange = 30f..480f,
                            steps = 14,
                            colors = SliderDefaults.colors(
                                thumbColor = CyberNeonGreen,
                                activeTrackColor = CyberNeonGreen,
                                inactiveTrackColor = CyberBorder
                            ),
                            modifier = Modifier.testTag("daily_limit_slider")
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("30m", color = CyberTextMuted, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                            Text("4h (Recomendado)", color = CyberTextSecondary, fontSize = 10.sp)
                            Text("8h", color = CyberTextMuted, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                        }
                    }
                }
            }

            // Rules List
            items(rules) { rule ->
                CyberCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = rule.title,
                                    color = CyberTextPrimary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                if (rule.requiresNativeAdmin) {
                                    CyberBadge(text = "ADMIN NATIVO", color = CyberPurple)
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = rule.description,
                                color = CyberTextSecondary,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )

                            if (rule.detailValue != null || rule.scheduleTime != null) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Parámetro: ${rule.scheduleTime ?: rule.detailValue}",
                                    color = CyberElectricCyan,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }

                        Switch(
                            checked = rule.isEnabled,
                            onCheckedChange = { viewModel.toggleRule(rule.id) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = CyberBackground,
                                checkedTrackColor = CyberNeonGreen,
                                uncheckedThumbColor = CyberTextMuted,
                                uncheckedTrackColor = CyberSurfaceElevated
                            ),
                            modifier = Modifier.testTag("rule_switch_${rule.id}")
                        )
                    }
                }
            }
        }
    }
}
