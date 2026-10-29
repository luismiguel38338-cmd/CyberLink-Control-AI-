package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CyberLinkViewModel
import com.example.ui.viewmodel.CyberScreen

@Composable
fun LandingScreen(
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
            .testTag("landing_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Hero Graphic with cyber aesthetic
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(14.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.cyberlink_hero_banner_1793298358769),
                contentDescription = "CyberLink Command Center Banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        androidx.compose.ui.graphics.Brush.verticalGradient(
                            listOf(
                                androidx.compose.ui.graphics.Color.Transparent,
                                CyberBackground.copy(alpha = 0.85f),
                                CyberBackground
                            )
                        )
                    )
            )
            CyberBadge(
                text = "SISTEMA CIBERNÉTICO v2.4",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
                color = CyberNeonGreen,
                icon = Icons.Default.Shield
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Title and Mission
        Text(
            text = "CYBERLINK CONTROL AI",
            color = CyberNeonGreen,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Plataforma de Administración Remota Autorizada, Ciberseguridad Digital y Control Parental Familiar",
            color = CyberTextSecondary,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CyberButton(
                text = "Ingresar al Panel",
                onClick = { viewModel.navigateTo(CyberScreen.DASHBOARD) },
                icon = Icons.Default.Dashboard,
                modifier = Modifier.weight(1f),
                isPrimary = true
            )
            CyberButton(
                text = "Iniciar Sesión",
                onClick = { viewModel.navigateTo(CyberScreen.AUTH) },
                icon = Icons.Default.Lock,
                modifier = Modifier.weight(1f),
                isPrimary = false
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Value Proposition Cards
        Text(
            text = "ARQUITECTURA DE PROTECCIÓN",
            color = CyberElectricCyan,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        CyberCard(glowEffect = true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberNeonGreen.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = CyberNeonGreen)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Consentimiento Informado & Transparencia",
                        color = CyberTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Sin funciones ocultas ni espionaje. Cada nodo supervisado sabe cuándo y qué permisos están activos.",
                        color = CyberTextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        CyberCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberElectricCyan.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = CyberElectricCyan)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Control Parental Inteligente",
                        color = CyberTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Horarios nocturnos, límites de pantalla diarios, descansos visuales y filtro web DNS seguro.",
                        color = CyberTextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        CyberCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberPurple.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Radar, contentDescription = null, tint = CyberPurple)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Radar de Ubicación & Geocercas",
                        color = CyberTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Coordenadas GNSS compartidas voluntariamente con alertas perimetrales (Hogar, Colegio).",
                        color = CyberTextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        CyberCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberWarningAmber.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = CyberWarningAmber)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Vinculación Criptográfica QR",
                        color = CyberTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Códigos efímeros temporales de 10 minutos con confirmación explícita del tutor legal.",
                        color = CyberTextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Quick Navigation to docs & compliance
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = { viewModel.navigateTo(CyberScreen.PRIVACY_CONSENT) }) {
                Text("Privacidad & Cero-Spyware", color = CyberTextSecondary, fontSize = 12.sp)
            }
            TextButton(onClick = { viewModel.navigateTo(CyberScreen.HELP_SUPPORT) }) {
                Text("Documentación & Despliegue", color = CyberElectricCyan, fontSize = 12.sp)
            }
        }
    }
}
