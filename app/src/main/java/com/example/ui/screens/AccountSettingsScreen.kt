package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CyberLinkViewModel
import com.example.ui.viewmodel.CyberScreen

@Composable
fun AccountSettingsScreen(
    viewModel: CyberLinkViewModel,
    user: UserProfile?,
    isSimulationMode: Boolean,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("account_settings_screen")
    ) {
        CyberHeaderBar(
            title = "CONFIGURACIÓN DEL NODO",
            subtitle = "CUENTA & PARÁMETROS",
            canNavigateBack = true,
            onNavigateBack = { viewModel.navigateBack() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Card
        CyberCard(glowEffect = true) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(CyberNeonGreen.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user?.avatarInitials ?: "CL",
                        color = CyberNeonGreen,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = user?.name ?: "Administrador",
                        color = CyberTextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = user?.email ?: "admin@cyberlink.ai",
                        color = CyberElectricCyan,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    CyberBadge(
                        text = "GRUPO: ${user?.familyGroupId ?: "CYBER-FAM"}",
                        color = CyberNeonGreen
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Security Parameters
        Text(
            text = "PARÁMETROS DE SEGURIDAD",
            color = CyberTextSecondary,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        CyberCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Autenticación de Dos Factores (2FA)",
                        color = CyberTextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Exige token TOTP cada vez que se vincula o desvincula un dispositivo.",
                        color = CyberTextSecondary,
                        fontSize = 11.sp
                    )
                }
                CyberBadge(text = "ACTIVADO", color = CyberNeonGreen)
            }

            HorizontalDivider(color = CyberBorder, modifier = Modifier.padding(vertical = 12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Modo Demostración / Simulación de Nodos",
                        color = CyberTextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Muestra nodos remotos simulados para validar flujos antes del despliegue en producción.",
                        color = CyberTextSecondary,
                        fontSize = 11.sp
                    )
                }
                Switch(
                    checked = isSimulationMode,
                    onCheckedChange = { viewModel.toggleSimulationMode() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = CyberBackground,
                        checkedTrackColor = CyberElectricCyan
                    ),
                    modifier = Modifier.testTag("toggle_simulation_mode_switch")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation shortcuts to compliance and documentation
        CyberCard {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Política de Privacidad & Consentimiento",
                    color = CyberTextPrimary,
                    fontSize = 13.sp
                )
                IconButton(onClick = { viewModel.navigateTo(CyberScreen.PRIVACY_CONSENT) }) {
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = CyberNeonGreen)
                }
            }

            HorizontalDivider(color = CyberBorder, modifier = Modifier.padding(vertical = 4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Manual de Arquitectura & Despliegue Web",
                    color = CyberTextPrimary,
                    fontSize = 13.sp
                )
                IconButton(onClick = { viewModel.navigateTo(CyberScreen.HELP_SUPPORT) }) {
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = CyberElectricCyan)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Logout Button
        CyberButton(
            text = "Cerrar Sesión Segura",
            onClick = { viewModel.logout() },
            icon = Icons.Default.Logout,
            modifier = Modifier.fillMaxWidth(),
            isDestructive = true
        )
    }
}
