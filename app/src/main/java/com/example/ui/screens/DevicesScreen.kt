package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DeviceConnectionStatus
import com.example.data.model.LinkedDevice
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CyberLinkViewModel
import com.example.ui.viewmodel.CyberScreen

@Composable
fun DevicesScreen(
    viewModel: CyberLinkViewModel,
    devices: List<LinkedDevice>,
    modifier: Modifier = Modifier
) {
    var deviceToRename by remember { mutableStateOf<LinkedDevice?>(null) }
    var renameInput by remember { mutableStateOf("") }
    var deviceToUnlink by remember { mutableStateOf<LinkedDevice?>(null) }
    var selectedDeviceDetail by remember { mutableStateOf<LinkedDevice?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBackground)
            .padding(16.dp)
            .testTag("devices_screen")
    ) {
        CyberHeaderBar(
            title = "DISPOSITIVOS VINCULADOS",
            subtitle = "${devices.size} NODOS AUTORIZADOS",
            canNavigateBack = true,
            onNavigateBack = { viewModel.navigateBack() },
            actions = {
                IconButton(
                    onClick = { viewModel.navigateTo(CyberScreen.PAIRING_QR) },
                    modifier = Modifier.testTag("add_device_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Vincular Nuevo Dispositivo",
                        tint = CyberNeonGreen
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Info notice banner regarding transparent telemetry
        CyberCard(borderColor = CyberElectricCyan.copy(alpha = 0.5f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = CyberElectricCyan,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Los datos mostrados provienen de nodos con consentimiento explícito. Los dispositivos de prueba están claramente etiquetados como 'DEMO'.",
                    color = CyberTextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (devices.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.DevicesOther,
                        contentDescription = null,
                        tint = CyberTextMuted,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No hay dispositivos vinculados",
                        color = CyberTextSecondary,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CyberButton(
                        text = "Vincular Dispositivo Ahora",
                        onClick = { viewModel.navigateTo(CyberScreen.PAIRING_QR) },
                        icon = Icons.Default.QrCode
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(devices) { dev ->
                    CyberCard(
                        borderColor = if (dev.isScreenLocked) CyberAlertRed else CyberBorder,
                        glowEffect = dev.connectionStatus == DeviceConnectionStatus.ONLINE
                    ) {
                        // Top row: Name, status badge, model
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = dev.name,
                                        color = CyberTextPrimary,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    IconButton(
                                        onClick = {
                                            deviceToRename = dev
                                            renameInput = dev.name
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Renombrar",
                                            tint = CyberTextMuted,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "${dev.modelName} • ${dev.osVersion}",
                                    color = CyberTextSecondary,
                                    fontSize = 11.sp
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                CyberBadge(
                                    text = if (dev.isDemoNode) "DEMO" else "LOCAL",
                                    color = if (dev.isDemoNode) CyberElectricCyan else CyberNeonGreen
                                )
                                val statusColor = when (dev.connectionStatus) {
                                    DeviceConnectionStatus.ONLINE -> CyberNeonGreen
                                    DeviceConnectionStatus.IDLE -> CyberWarningAmber
                                    DeviceConnectionStatus.OFFLINE -> CyberTextMuted
                                }
                                CyberBadge(
                                    text = dev.connectionStatus.name,
                                    color = statusColor
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Telemetry Row: Battery, IP, Screen time, Sincronización
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("BATERÍA", color = CyberTextMuted, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (dev.isCharging) Icons.Default.BatteryChargingFull else Icons.Default.BatteryStd,
                                        contentDescription = null,
                                        tint = if (dev.batteryPercent > 20) CyberNeonGreen else CyberAlertRed,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        "${dev.batteryPercent}%",
                                        color = CyberTextPrimary,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }

                            Column {
                                Text("TIEMPO PANTALLA", color = CyberTextMuted, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                Text(
                                    "${dev.screenTimeMinutesToday}m / ${dev.dailyLimitMinutes}m",
                                    color = CyberElectricCyan,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }

                            Column {
                                Text("ÚLTIMA SINCRONÍA", color = CyberTextMuted, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                                Text(
                                    dev.lastSyncTime,
                                    color = CyberTextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Authorized Permissions Chips
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            dev.authorizedPermissions.take(2).forEach { perm ->
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = CyberSurfaceElevated
                                ) {
                                    Text(
                                        text = perm,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        color = CyberTextSecondary,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                            if (dev.authorizedPermissions.size > 2) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = CyberSurfaceElevated
                                ) {
                                    Text(
                                        text = "+${dev.authorizedPermissions.size - 2} más",
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        color = CyberTextMuted,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Action Buttons: Lock screen & Unlink & Detail
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.toggleDeviceScreenLock(dev.id) },
                                modifier = Modifier
                                    .weight(1f)
                                    .heightIn(min = 44.dp)
                                    .testTag("lock_btn_${dev.id}"),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = if (dev.isScreenLocked) CyberAlertRed else CyberNeonGreen
                                ),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (dev.isScreenLocked) CyberAlertRed else CyberNeonGreen
                                )
                            ) {
                                Icon(
                                    imageVector = if (dev.isScreenLocked) Icons.Default.Lock else Icons.Default.LockOpen,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (dev.isScreenLocked) "Desbloquear" else "Bloquear",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            IconButton(
                                onClick = { selectedDeviceDetail = dev },
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(CyberSurfaceElevated)
                                    .testTag("detail_btn_${dev.id}")
                            ) {
                                Icon(Icons.Default.Info, contentDescription = "Detalles", tint = CyberElectricCyan)
                            }

                            IconButton(
                                onClick = { deviceToUnlink = dev },
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(CyberSurfaceElevated)
                                    .testTag("unlink_btn_${dev.id}")
                            ) {
                                Icon(Icons.Default.DeleteOutline, contentDescription = "Desvincular", tint = CyberAlertRed)
                            }
                        }
                    }
                }
            }
        }
    }

    // Rename Dialog
    if (deviceToRename != null) {
        AlertDialog(
            onDismissRequest = { deviceToRename = null },
            title = { Text("Renombrar Dispositivo", color = CyberNeonGreen, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Ingresa una etiqueta descriptiva para identificar este terminal:", color = CyberTextSecondary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = renameInput,
                        onValueChange = { renameInput = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberNeonGreen,
                            unfocusedBorderColor = CyberBorder,
                            focusedTextColor = CyberTextPrimary,
                            unfocusedTextColor = CyberTextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("rename_input_field")
                    )
                }
            },
            confirmButton = {
                CyberButton(
                    text = "Guardar",
                    onClick = {
                        deviceToRename?.let { dev ->
                            if (renameInput.isNotBlank()) {
                                viewModel.renameDevice(dev.id, renameInput)
                            }
                        }
                        deviceToRename = null
                    },
                    isPrimary = true
                )
            },
            dismissButton = {
                TextButton(onClick = { deviceToRename = null }) {
                    Text("Cancelar", color = CyberTextSecondary)
                }
            },
            containerColor = CyberSurface
        )
    }

    // Unlink Confirmation Dialog
    if (deviceToUnlink != null) {
        AlertDialog(
            onDismissRequest = { deviceToUnlink = null },
            title = { Text("¿Desvincular Nodo?", color = CyberAlertRed, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(
                        "Estás a punto de desvincular '${deviceToUnlink?.name}'.",
                        color = CyberTextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Se revocarán inmediatamente todos los permisos, certificados de acceso remoto y la sincronización con el panel familiar.",
                        color = CyberTextSecondary,
                        fontSize = 12.sp
                    )
                }
            },
            confirmButton = {
                CyberButton(
                    text = "Sí, Desvincular",
                    onClick = {
                        deviceToUnlink?.let { viewModel.unlinkDevice(it.id) }
                        deviceToUnlink = null
                    },
                    isDestructive = true
                )
            },
            dismissButton = {
                TextButton(onClick = { deviceToUnlink = null }) {
                    Text("Cancelar", color = CyberTextSecondary)
                }
            },
            containerColor = CyberSurface
        )
    }

    // Device Detail Bottom Sheet / Dialog
    if (selectedDeviceDetail != null) {
        val dev = selectedDeviceDetail!!
        AlertDialog(
            onDismissRequest = { selectedDeviceDetail = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Devices, contentDescription = null, tint = CyberElectricCyan)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(dev.name, color = CyberTextPrimary, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Modelo de Hardware: ${dev.modelName}", color = CyberTextSecondary, fontSize = 12.sp)
                    Text("Sistema Operativo: ${dev.osVersion}", color = CyberTextSecondary, fontSize = 12.sp)
                    Text("Versión de Agente CyberLink: ${dev.appVersion}", color = CyberTextSecondary, fontSize = 12.sp)
                    Text("Dirección IP de Red: ${dev.ipAddress}", color = CyberTextSecondary, fontSize = 12.sp)
                    Text("Fecha de Vinculación: ${dev.pairingDate}", color = CyberTextSecondary, fontSize = 12.sp)
                    Text("Perfil Asignado: ${dev.assignedProfileName}", color = CyberNeonGreen, fontSize = 12.sp)
                    HorizontalDivider(color = CyberBorder, modifier = Modifier.padding(vertical = 4.dp))
                    Text("Permisos Concedidos:", color = CyberElectricCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    dev.authorizedPermissions.forEach { perm ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = CyberNeonGreen, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(perm, color = CyberTextPrimary, fontSize = 11.sp)
                        }
                    }
                }
            },
            confirmButton = {
                CyberButton(
                    text = "Cerrar",
                    onClick = { selectedDeviceDetail = null },
                    isPrimary = false
                )
            },
            containerColor = CyberSurface
        )
    }
}
