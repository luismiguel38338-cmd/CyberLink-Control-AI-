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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CyberLinkViewModel

@Composable
fun PrivacyConsentScreen(
    viewModel: CyberLinkViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var exportedJson by remember { mutableStateOf<String?>(null) }
    var showWipeConfirmDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("privacy_consent_screen")
    ) {
        CyberHeaderBar(
            title = "PRIVACIDAD & CUMPLIMIENTO",
            subtitle = "GDPR • TRANSPARENCIA TOTAL",
            canNavigateBack = true,
            onNavigateBack = { viewModel.navigateBack() }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Manifesto Banner: No Stalkerware / No Covert Actions
        CyberCard(borderColor = CyberNeonGreen, glowEffect = true) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = CyberNeonGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "MANIFIESTO ÉTICO CIBERNÉTICO",
                        color = CyberNeonGreen,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "CyberLink Control AI fue diseñado bajo los principios de protección parental legítima y administración remota autorizada.",
                        color = CyberTextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "• NUNCA se oculta el ícono de la aplicación en el dispositivo supervisado.\n• NUNCA se graban llamadas, mensajes de chat privados ni contraseñas.\n• NUNCA se activa la cámara ni el micrófono sin consentimiento explícito.\n• Toda orden administrativa genera un registro visible e inmutable en la bitácora familiar.",
                        color = CyberTextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Informed Consent Terms
        Text(
            text = "TÉRMINOS DE CONSENTIMIENTO INFORMADO",
            color = CyberElectricCyan,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        CyberCard {
            Text(
                text = "1. Rol de Autoridad Parental",
                color = CyberTextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "El uso de esta plataforma está limitado a tutores legales sobre menores de edad a su cargo, o a propietarios de dispositivos que han otorgado autorización recíproca mediante PIN o QR.",
                color = CyberTextSecondary,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )

            HorizontalDivider(color = CyberBorder, modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "2. Minimización de Datos",
                color = CyberTextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Solo se procesan los datos estrictamente indispensables: minutos de pantalla, nivel de batería, estado de conexión y coordenadas satelitales si el radar está activado.",
                color = CyberTextSecondary,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )

            HorizontalDivider(color = CyberBorder, modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "3. Derecho de Revocación Inmediata",
                color = CyberTextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "El tutor o el usuario del dispositivo pueden en cualquier momento desvincular el terminal o pausar el rastreo satelital desde el panel de control.",
                color = CyberTextSecondary,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Data Export & Right to be Forgotten (GDPR)
        Text(
            text = "PORTABILIDAD & BORRADO DE DATOS (GDPR)",
            color = CyberPurple,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        CyberCard {
            Text(
                text = "Exportación de Información",
                color = CyberTextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Genera un volcado estructurado en JSON con todos los dispositivos, reglas y registros vinculados a tu cuenta.",
                color = CyberTextSecondary,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            CyberButton(
                text = "Exportar Datos en Formato JSON",
                onClick = {
                    exportedJson = viewModel.exportDataJson()
                    viewModel.showNotification("Archivo JSON de datos generado con éxito")
                },
                icon = Icons.Default.Download,
                modifier = Modifier.fillMaxWidth(),
                isPrimary = false
            )

            if (exportedJson != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = CyberSurfaceElevated,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = exportedJson!!,
                        color = CyberNeonGreen,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            HorizontalDivider(color = CyberBorder, modifier = Modifier.padding(vertical = 12.dp))

            Text(
                text = "Derecho al Olvido (Eliminación Completa)",
                color = CyberAlertRed,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Elimina permanentemente todos los dispositivos vinculados, sesiones, reglas y registros de auditoría.",
                color = CyberTextSecondary,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            CyberButton(
                text = "Purgar y Borrar Todos Mis Datos",
                onClick = { showWipeConfirmDialog = true },
                icon = Icons.Default.DeleteForever,
                modifier = Modifier.fillMaxWidth(),
                isDestructive = true
            )
        }
    }

    if (showWipeConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showWipeConfirmDialog = false },
            title = { Text("¿Eliminar Definitivamente?", color = CyberAlertRed, fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "Esta acción purgará de forma irrevocable todos los dispositivos, perfiles familiares, reglas y registros de seguridad conforme al RGPD.",
                    color = CyberTextPrimary,
                    fontSize = 12.sp
                )
            },
            confirmButton = {
                CyberButton(
                    text = "Confirmar Purgado",
                    onClick = {
                        viewModel.wipeAllData()
                        showWipeConfirmDialog = false
                    },
                    isDestructive = true
                )
            },
            dismissButton = {
                TextButton(onClick = { showWipeConfirmDialog = false }) {
                    Text("Cancelar", color = CyberTextSecondary)
                }
            },
            containerColor = CyberSurface
        )
    }
}
