package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PairingToken
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CyberLinkViewModel
import com.example.ui.viewmodel.CyberScreen

@Composable
fun PairingScreen(
    viewModel: CyberLinkViewModel,
    pairingToken: PairingToken?,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var simulateDeviceName by remember { mutableStateOf("Tablet Samsung Tab S10") }
    var simulateModel by remember { mutableStateOf("SM-X910") }
    var simulateProfile by remember { mutableStateOf("Lucas (8 años)") }
    var consentConfirmed by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("pairing_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CyberHeaderBar(
            title = "VINCULACIÓN CRIPTOGRÁFICA",
            subtitle = "AUTORIZACIÓN MUTUA DE NODOS",
            canNavigateBack = true,
            onNavigateBack = { viewModel.navigateBack() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Step 1: Cyber QR Code & Expiring Temporal PIN
        Text(
            text = "PASO 1: ESCANEAR CÓDIGO O INGRESAR PIN",
            color = CyberNeonGreen,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        CyberCard(glowEffect = true) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Apunta la cámara del dispositivo a vincular hacia este código QR seguro:",
                    color = CyberTextSecondary,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Render dynamic cyber QR matrix visualizer
                CyberQrMatrixBox(
                    code = pairingToken?.code ?: "CYBER-9482"
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Alpha-numeric PIN Display
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = CyberSurfaceElevated,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CyberNeonGreen.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PIN TEMPORAL: ",
                            color = CyberTextSecondary,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = pairingToken?.code ?: "CYBER-9482",
                            color = CyberNeonGreen,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = null,
                        tint = CyberWarningAmber,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = pairingToken?.validUntil ?: "Expira en 10 minutos",
                        color = CyberWarningAmber,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    TextButton(
                        onClick = { viewModel.generateNewPairingCode() },
                        modifier = Modifier.testTag("refresh_pairing_code_btn")
                    ) {
                        Text("Regenerar PIN", color = CyberElectricCyan, fontSize = 11.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Step 2: Protocol Identification & Permissions Transparency
        Text(
            text = "PASO 2: CONSENTIMIENTO INFORMADO & TUTORÍA",
            color = CyberElectricCyan,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        CyberCard {
            Text(
                text = "Identificación del Administrador Solicitante:",
                color = CyberTextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "• Tutor Legal: ${pairingToken?.adminName ?: "Luis Miguel (Admin)"}\n• Grupo Familiar: ${pairingToken?.familyGroupId ?: "CYBER-NODE-8891"}",
                color = CyberTextSecondary,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )

            HorizontalDivider(color = CyberBorder, modifier = Modifier.padding(vertical = 10.dp))

            Text(
                text = "Permisos Requeridos que se Concederán:",
                color = CyberElectricCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("✔ Coordenadas GNSS (Radar de ubicación familiar)", color = CyberTextPrimary, fontSize = 11.sp)
                Text("✔ Cómputo de Horas de Pantalla y descanso visual", color = CyberTextPrimary, fontSize = 11.sp)
                Text("✔ Bloqueo Nocturno y Administración de Horarios", color = CyberTextPrimary, fontSize = 11.sp)
                Text("✖ CERO acceso a chats privados, micrófonos o contraseñas", color = CyberNeonGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = consentConfirmed,
                    onCheckedChange = { consentConfirmed = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = CyberNeonGreen,
                        uncheckedColor = CyberBorder
                    ),
                    modifier = Modifier.testTag("consent_checkbox")
                )
                Text(
                    text = "Confirmo que soy el propietario o tutor legal autorizado del dispositivo supervisado.",
                    color = CyberTextPrimary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Step 3: Simulate Pairing from this Terminal
        Text(
            text = "PASO 3: SIMULAR ACEPTACIÓN EN EL DISPOSITIVO HIJO",
            color = CyberPurple,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        CyberCard {
            Text(
                text = "Ingresa los datos para registrar un nuevo nodo verificado en la red familiar:",
                color = CyberTextSecondary,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = simulateDeviceName,
                onValueChange = { simulateDeviceName = it },
                label = { Text("Nombre del Dispositivo") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberNeonGreen,
                    unfocusedBorderColor = CyberBorder,
                    focusedTextColor = CyberTextPrimary,
                    unfocusedTextColor = CyberTextPrimary
                ),
                modifier = Modifier.fillMaxWidth().testTag("pair_device_name_input")
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = simulateProfile,
                onValueChange = { simulateProfile = it },
                label = { Text("Perfil Familiar Asignado") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberNeonGreen,
                    unfocusedBorderColor = CyberBorder,
                    focusedTextColor = CyberTextPrimary,
                    unfocusedTextColor = CyberTextPrimary
                ),
                modifier = Modifier.fillMaxWidth().testTag("pair_profile_input")
            )

            Spacer(modifier = Modifier.height(14.dp))

            CyberButton(
                text = "Autorizar y Conectar Nodo",
                onClick = {
                    if (consentConfirmed) {
                        viewModel.pairDevice(
                            name = simulateDeviceName.ifBlank { "Nuevo Dispositivo" },
                            model = simulateModel,
                            assignedProfile = simulateProfile.ifBlank { "Hijo" }
                        )
                    } else {
                        viewModel.showNotification("Debes aceptar el consentimiento legal primero.")
                    }
                },
                enabled = consentConfirmed,
                icon = Icons.Default.Link,
                modifier = Modifier.fillMaxWidth(),
                isPrimary = true
            )
        }
    }
}
