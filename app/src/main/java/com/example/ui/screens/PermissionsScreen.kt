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
import com.example.data.model.CyberPermission
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CyberLinkViewModel

@Composable
fun PermissionsScreen(
    viewModel: CyberLinkViewModel,
    permissions: List<CyberPermission>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBackground)
            .padding(16.dp)
            .testTag("permissions_screen")
    ) {
        CyberHeaderBar(
            title = "SUPERVISIÓN DE PRIVILEGIOS",
            subtitle = "TRANSPARENCIA TOTAL DE PERMISOS",
            canNavigateBack = true,
            onNavigateBack = { viewModel.navigateBack() }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Anti-Stalkerware Declaration Banner
        CyberCard(borderColor = CyberNeonGreen, glowEffect = true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.VerifiedUser,
                    contentDescription = null,
                    tint = CyberNeonGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Garantía Cero-Spyware (Google Play Policy)",
                        color = CyberNeonGreen,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "CyberLink opera bajo consentimiento mutuo expreso. No contiene grabadores de teclado, acceso encubierto a cámara ni micrófono oculto.",
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
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(permissions) { perm ->
                CyberCard(
                    borderColor = if (perm.isGranted) CyberBorder else CyberWarningAmber.copy(alpha = 0.5f)
                ) {
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
                                    text = perm.name,
                                    color = CyberTextPrimary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                if (perm.isNativeAndroidOnly) {
                                    CyberBadge(text = "ANDROID NATIVO", color = CyberElectricCyan)
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = perm.description,
                                color = CyberTextSecondary,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Justificación legítima: ${perm.whyNeeded}",
                                color = CyberTextMuted,
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Switch(
                            checked = perm.isGranted,
                            onCheckedChange = { viewModel.togglePermission(perm.key) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = CyberBackground,
                                checkedTrackColor = CyberNeonGreen,
                                uncheckedThumbColor = CyberTextMuted,
                                uncheckedTrackColor = CyberSurfaceElevated
                            ),
                            modifier = Modifier.testTag("perm_switch_${perm.key}")
                        )
                    }
                }
            }
        }
    }
}
