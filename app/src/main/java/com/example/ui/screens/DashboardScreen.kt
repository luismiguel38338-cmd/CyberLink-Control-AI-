package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DeviceConnectionStatus
import com.example.data.model.SecuritySeverity
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CyberLinkViewModel
import com.example.ui.viewmodel.CyberScreen
import com.example.ui.viewmodel.CyberUiState

@Composable
fun DashboardScreen(
    viewModel: CyberLinkViewModel,
    uiState: CyberUiState,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val onlineNodes = uiState.devices.count { it.connectionStatus == DeviceConnectionStatus.ONLINE }
    val totalScreenTimeMin = uiState.devices.sumOf { it.screenTimeMinutesToday }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("dashboard_screen")
    ) {
        CyberHeaderBar(
            title = "PANEL DE CONTROL CYBERLINK",
            subtitle = "GRUPO: ${uiState.user?.familyGroupId ?: "CYBER-FAM"}",
            actions = {
                IconButton(
                    onClick = { viewModel.toggleSystemArm() },
                    modifier = Modifier.testTag("toggle_system_arm_btn")
                ) {
                    Icon(
                        imageVector = if (uiState.isSystemArmed) Icons.Default.Shield else Icons.Default.ShieldMoon,
                        contentDescription = "Estado de Blindaje",
                        tint = if (uiState.isSystemArmed) CyberNeonGreen else CyberWarningAmber
                    )
                }
                IconButton(
                    onClick = { viewModel.navigateTo(CyberScreen.ACCOUNT_SETTINGS) },
                    modifier = Modifier.testTag("user_profile_btn")
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(CyberNeonGreen.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.user?.avatarInitials ?: "CL",
                            color = CyberNeonGreen,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // System Health Banner HUD
        CyberCard(
            borderColor = if (uiState.isSystemArmed) CyberNeonGreen else CyberWarningAmber,
            glowEffect = uiState.isSystemArmed
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(if (uiState.isSystemArmed) CyberNeonGreen else CyberWarningAmber)
                        )
                        Text(
                            text = if (uiState.isSystemArmed) "BLINDAJE DEFENSA ACTIVO" else "DEFENSA EN MANTENIMIENTO",
                            color = if (uiState.isSystemArmed) CyberNeonGreen else CyberWarningAmber,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Supervisión autorizada activa en $onlineNodes de ${uiState.devices.size} dispositivos familiares.",
                        color = CyberTextSecondary,
                        fontSize = 12.sp
                    )
                }

                CyberBadge(
                    text = if (uiState.isSimulationModeActive) "DEMO / NATIVE" else "PROD",
                    color = CyberElectricCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Metrics Grid (2x2)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CyberMetricCard(
                title = "Nodos Activos",
                value = "$onlineNodes / ${uiState.devices.size}",
                subtitle = "Conexión cifrada",
                icon = Icons.Default.Devices,
                accentColor = CyberNeonGreen,
                modifier = Modifier.weight(1f)
            )
            CyberMetricCard(
                title = "Tiempo Familiar",
                value = "${totalScreenTimeMin / 60}h ${totalScreenTimeMin % 60}m",
                subtitle = "Promedio diario",
                icon = Icons.Default.Schedule,
                accentColor = CyberElectricCyan,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CyberMetricCard(
                title = "Reglas Activas",
                value = "${uiState.rules.count { it.isEnabled }} / ${uiState.rules.size}",
                subtitle = "Filtro DNS + Horarios",
                icon = Icons.Default.Rule,
                accentColor = CyberPurple,
                modifier = Modifier.weight(1f)
            )
            CyberMetricCard(
                title = "Amenazas Bloqueadas",
                value = "14",
                subtitle = "Últimas 24 horas",
                icon = Icons.Default.Security,
                accentColor = CyberAlertRed,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Quick Actions Row
        Text(
            text = "ACCIONES OPERATIVAS RÁPIDAS",
            color = CyberTextSecondary,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { viewModel.navigateTo(CyberScreen.PAIRING_QR) },
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberNeonGreen),
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 48.dp)
                    .testTag("action_pair_qr_btn")
            ) {
                Icon(Icons.Default.QrCode, contentDescription = null, tint = CyberNeonGreen, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Vincular QR", color = CyberNeonGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = { viewModel.navigateTo(CyberScreen.LOCATION_RADAR) },
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberElectricCyan),
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 48.dp)
                    .testTag("action_radar_btn")
            ) {
                Icon(Icons.Default.Radar, contentDescription = null, tint = CyberElectricCyan, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Radar GPS", color = CyberElectricCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = { viewModel.navigateTo(CyberScreen.PARENTAL_CONTROL) },
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberPurple),
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 48.dp)
                    .testTag("action_parental_btn")
            ) {
                Icon(Icons.Default.FamilyRestroom, contentDescription = null, tint = CyberPurple, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Límites", color = CyberPurple, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Linked Devices Horizontal Carousel
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "DISPOSITIVOS SUPERVISADOS",
                color = CyberTextSecondary,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            TextButton(
                onClick = { viewModel.navigateTo(CyberScreen.DEVICES) },
                modifier = Modifier.testTag("see_all_devices_btn")
            ) {
                Text("Ver Todos (${uiState.devices.size})", color = CyberNeonGreen, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(uiState.devices) { dev ->
                Card(
                    modifier = Modifier
                        .width(220.dp)
                        .clickable { viewModel.navigateTo(CyberScreen.DEVICES) }
                        .testTag("device_card_${dev.id}"),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (dev.isScreenLocked) CyberAlertRed else CyberBorder
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CyberBadge(
                                text = if (dev.isDemoNode) "DEMO" else "LOCAL",
                                color = if (dev.isDemoNode) CyberElectricCyan else CyberNeonGreen
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (dev.isCharging) Icons.Default.BatteryChargingFull else Icons.Default.BatteryStd,
                                    contentDescription = null,
                                    tint = if (dev.batteryPercent > 20) CyberNeonGreen else CyberAlertRed,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${dev.batteryPercent}%",
                                    color = CyberTextSecondary,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = dev.name,
                            color = CyberTextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = dev.modelName,
                            color = CyberTextSecondary,
                            fontSize = 11.sp,
                            maxLines = 1
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (dev.isScreenLocked) "PANTALLA BLOQUEADA" else "ACTIVO",
                                color = if (dev.isScreenLocked) CyberAlertRed else CyberNeonGreen,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )

                            IconButton(
                                onClick = { viewModel.toggleDeviceScreenLock(dev.id) },
                                modifier = Modifier
                                    .size(32.dp)
                                    .testTag("lock_toggle_${dev.id}")
                            ) {
                                Icon(
                                    imageVector = if (dev.isScreenLocked) Icons.Default.Lock else Icons.Default.LockOpen,
                                    contentDescription = "Bloquear o Desbloquear",
                                    tint = if (dev.isScreenLocked) CyberAlertRed else CyberTextSecondary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Security Events Stream Preview
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "TELEMETRÍA & AUDITORÍA EN VIVO",
                color = CyberTextSecondary,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { viewModel.navigateTo(CyberScreen.EVENT_HISTORY) }) {
                Text("Ver Bitácora Completa", color = CyberElectricCyan, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        CyberCard {
            uiState.securityEvents.take(3).forEachIndexed { index, event ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    val severityColor = when (event.severity) {
                        SecuritySeverity.CRITICAL -> CyberAlertRed
                        SecuritySeverity.WARNING -> CyberWarningAmber
                        SecuritySeverity.INFO -> CyberNeonGreen
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(severityColor)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = event.title,
                                color = CyberTextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = event.timestamp.substringAfter(" "),
                                color = CyberTextMuted,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Text(
                            text = event.description,
                            color = CyberTextSecondary,
                            fontSize = 11.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                if (index < 2) {
                    HorizontalDivider(
                        color = CyberBorder.copy(alpha = 0.5f),
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
            }
        }
    }
}
