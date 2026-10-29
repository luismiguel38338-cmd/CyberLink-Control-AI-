package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CyberLinkViewModel

@Composable
fun HelpSupportScreen(
    viewModel: CyberLinkViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("help_support_screen")
    ) {
        CyberHeaderBar(
            title = "ARQUITECTURA & SOPORTE",
            subtitle = "DOCUMENTACIÓN TÉCNICA",
            canNavigateBack = true,
            onNavigateBack = { viewModel.navigateBack() }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // System Architecture Breakdown
        CyberCard(borderColor = CyberElectricCyan, glowEffect = true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Terminal,
                    contentDescription = null,
                    tint = CyberElectricCyan,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "TOPOLOGÍA DEL SISTEMA CYBERLINK",
                        color = CyberElectricCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "Arquitectura Híbrida Android Nativa + Portal Web Central",
                        color = CyberTextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "1. Aplicación Android (Jetpack Compose):\n   • Ejecuta el agente de supervisión con permisos del sistema operativo.\n   • Implementa DevicePolicyManager (Device Admin) para bloqueo seguro.\n   • Mide estadísticas precisas con UsageStatsManager y ubicación GNSS.\n\n2. Servidor Backend & Base de Datos:\n   • Coordina sincronización en tiempo real mediante tokens criptográficos efímeros.\n   • Administra reglas de seguridad Firestore con aislamiento estricto por grupo familiar.\n\n3. Portal Web de Administración:\n   • Permite al tutor consultar métricas, bitácoras y mapas desde navegadores de escritorio.",
                color = CyberTextSecondary,
                fontSize = 11.sp,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Deployment & Compilation Guide
        Text(
            text = "INSTRUCCIONES DE COMPILACIÓN & DESPLIEGUE",
            color = CyberNeonGreen,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        CyberCard {
            Text(
                text = "Compilación en Repositorio (Android Studio / CI)",
                color = CyberTextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = CyberSurfaceElevated,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$ git clone https://github.com/luismiguel4953/cyberlink-control-ai.git\n$ cd cyberlink-control-ai\n$ gradle :app:assembleDebug",
                    color = CyberNeonGreen,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Despliegue del Portal Web (Vercel / Firebase Hosting)",
                color = CyberTextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = CyberSurfaceElevated,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$ firebase init hosting\n$ firebase deploy --only hosting",
                    color = CyberElectricCyan,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Frequently Asked Questions
        Text(
            text = "PREGUNTAS FRECUENTES (FAQ)",
            color = CyberTextSecondary,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        CyberCard {
            Text(
                text = "¿Por qué un código de vinculación expira a los 10 minutos?",
                color = CyberTextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Para prevenir ataques de reutilización de tokens o intentos de conexión no autorizados por parte de terceros en la red local.",
                color = CyberTextSecondary,
                fontSize = 11.sp
            )

            HorizontalDivider(color = CyberBorder, modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "¿Qué sucede si el dispositivo pierde conexión a Internet?",
                color = CyberTextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Las reglas ya descargadas (como el toque de queda nocturno y los límites de tiempo) se aplican localmente en el dispositivo sin necesidad de conexión activa.",
                color = CyberTextSecondary,
                fontSize = 11.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Support contact
        CyberButton(
            text = "Canal de Soporte Cifrado: admin@cyberlink.ai",
            onClick = { viewModel.showNotification("Contacto copiado: admin@cyberlink.ai") },
            icon = Icons.Default.Email,
            modifier = Modifier.fillMaxWidth(),
            isPrimary = false
        )
    }
}
