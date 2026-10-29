package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GeofenceZone
import com.example.data.model.SharedLocation
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CyberLinkViewModel

@Composable
fun LocationRadarScreen(
    viewModel: CyberLinkViewModel,
    location: SharedLocation?,
    geofences: List<GeofenceZone>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBackground)
            .padding(16.dp)
            .testTag("location_radar_screen")
    ) {
        CyberHeaderBar(
            title = "RADAR GNSS & GEOCERCAS",
            subtitle = if (location?.isTrackingActive == true) "RASTREO CONSENTIDO ACTIVO" else "RASTREO EN PAUSA",
            canNavigateBack = true,
            onNavigateBack = { viewModel.navigateBack() },
            actions = {
                IconButton(
                    onClick = { viewModel.refreshLocationPing() },
                    modifier = Modifier.testTag("refresh_location_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Sincronizar Ping",
                        tint = CyberNeonGreen
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Privacy and Opt-In Status Banner
        CyberCard(
            borderColor = if (location?.isTrackingActive == true) CyberNeonGreen else CyberWarningAmber
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (location?.isTrackingActive == true) "UBICACIÓN COMPARTIDA: ACTIVA" else "UBICACIÓN PAUSADA",
                        color = if (location?.isTrackingActive == true) CyberNeonGreen else CyberWarningAmber,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "El dispositivo supervisado muestra un indicador permanente en la barra de estado cuando la ubicación se consulta.",
                        color = CyberTextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = location?.isTrackingActive == true,
                    onCheckedChange = { viewModel.toggleLocationTracking() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = CyberBackground,
                        checkedTrackColor = CyberNeonGreen,
                        uncheckedThumbColor = CyberTextMuted,
                        uncheckedTrackColor = CyberSurfaceElevated
                    ),
                    modifier = Modifier.testTag("location_tracking_switch")
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Futuristic Radar Visualizer Canvas
        CyberRadarScope(
            accuracyMeters = location?.accuracyMeters ?: 8.5f,
            deviceCount = 2
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Coordinates & Telemetry Box
        location?.let { loc ->
            CyberCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = loc.deviceName,
                            color = CyberTextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = loc.addressName,
                            color = CyberElectricCyan,
                            fontSize = 12.sp
                        )
                    }

                    CyberBadge(
                        text = "±${loc.accuracyMeters.toInt()}m",
                        color = CyberNeonGreen,
                        icon = Icons.Default.GpsFixed
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "LAT: ${"%.4f".format(loc.latitude)} | LON: ${"%.4f".format(loc.longitude)}",
                        color = CyberTextSecondary,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "ACT: ${loc.lastUpdated}",
                        color = CyberTextMuted,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Geofences Safe Zones
        Text(
            text = "GEOCERCAS PERIMETRALES CONFIGURADAS",
            color = CyberTextSecondary,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(geofences) { geo ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (geo.type == "Hogar") Icons.Default.Home else Icons.Default.School,
                                contentDescription = null,
                                tint = CyberElectricCyan,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = geo.name,
                                    color = CyberTextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Radio perimetral: ${geo.radiusMeters} metros",
                                    color = CyberTextSecondary,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        CyberBadge(
                            text = if (geo.isInside) "DENTRO" else "FUERA",
                            color = if (geo.isInside) CyberNeonGreen else CyberTextMuted
                        )
                    }
                }
            }
        }
    }
}
