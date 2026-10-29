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
import com.example.data.model.ActiveSession
import com.example.data.model.SecurityEvent
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CyberLinkViewModel

@Composable
fun SecurityCenterScreen(
    viewModel: CyberLinkViewModel,
    sessions: List<ActiveSession>,
    events: List<SecurityEvent>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBackground)
            .padding(16.dp)
            .testTag("security_center_screen")
    ) {
        CyberHeaderBar(
            title = "CENTRO DE SEGURIDAD & SESIONES",
            subtitle = "POSTURA DEFENSIVA: ÓPTIMA",
            canNavigateBack = true,
            onNavigateBack = { viewModel.navigateBack() }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Security Posture Card
        CyberCard(borderColor = CyberNeonGreen, glowEffect = true) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ÍNDICE DE SALUD DIGITAL",
                        color = CyberTextSecondary,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "99.8% PROTEGIDO",
                        color = CyberNeonGreen,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "Cifrado TLS 1.3 + Firma Criptográfica SHA-256",
                        color = CyberTextSecondary,
                        fontSize = 11.sp
                    )
                }

                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = CyberNeonGreen,
                    modifier = Modifier.size(42.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Active Sessions Section
        Text(
            text = "SESIONES ACTIVAS AUTORIZADAS (${sessions.size})",
            color = CyberTextSecondary,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(sessions) { session ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (session.isCurrent) CyberNeonGreen.copy(alpha = 0.5f) else CyberBorder
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (session.isCurrent) Icons.Default.Smartphone else Icons.Default.Laptop,
                                    contentDescription = null,
                                    tint = if (session.isCurrent) CyberNeonGreen else CyberElectricCyan,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = session.deviceName,
                                    color = CyberTextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            if (session.isCurrent) {
                                CyberBadge(text = "SESIÓN ACTUAL", color = CyberNeonGreen)
                            } else {
                                OutlinedButton(
                                    onClick = { viewModel.terminateSession(session.id) },
                                    shape = RoundedCornerShape(6.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, CyberAlertRed),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                    modifier = Modifier.heightIn(min = 36.dp)
                                ) {
                                    Text("Cerrar Sesión", color = CyberAlertRed, fontSize = 11.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "${session.clientType} • ${session.ipAddress} (${session.location})",
                            color = CyberTextSecondary,
                            fontSize = 11.sp
                        )

                        Text(
                            text = "Actividad: ${session.lastActive}",
                            color = CyberTextMuted,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "HISTORIAL RECIENTE DE ACCESOS",
                    color = CyberTextSecondary,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }

            items(events.take(4)) { ev ->
                CyberCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = ev.title,
                                color = CyberTextPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "IP Origen: ${ev.sourceIp} | ${ev.timestamp}",
                                color = CyberTextMuted,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        CyberBadge(
                            text = ev.result.name,
                            color = if (ev.result.name == "SUCCESS") CyberNeonGreen else CyberAlertRed
                        )
                    }
                }
            }
        }
    }
}
