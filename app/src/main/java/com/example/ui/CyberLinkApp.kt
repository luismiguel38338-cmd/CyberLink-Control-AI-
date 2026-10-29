package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.CyberBadge
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CyberLinkViewModel
import com.example.ui.viewmodel.CyberScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CyberLinkApp(
    viewModel: CyberLinkViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showNavigationDrawer by remember { mutableStateOf(false) }

    // System BackHandler
    BackHandler(enabled = uiState.currentScreen != CyberScreen.DASHBOARD && uiState.currentScreen != CyberScreen.LANDING) {
        viewModel.navigateBack()
    }

    // Floating notification message if present
    LaunchedEffect(uiState.notificationMessage) {
        if (uiState.notificationMessage != null) {
            kotlinx.coroutines.delay(3500)
            viewModel.clearNotification()
        }
    }

    val isAuthOrLanding = uiState.currentScreen == CyberScreen.LANDING || uiState.currentScreen == CyberScreen.AUTH

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(CyberBackground)
            .testTag("cyberlink_root_scaffold"),
        containerColor = CyberBackground,
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            if (!isAuthOrLanding) {
                NavigationBar(
                    containerColor = CyberSurface,
                    contentColor = CyberNeonGreen,
                    tonalElevation = 4.dp,
                    windowInsets = WindowInsets.navigationBars,
                    modifier = Modifier.testTag("cyber_bottom_nav")
                ) {
                    val navItems = listOf(
                        Triple(CyberScreen.DASHBOARD, "Panel", Icons.Default.Dashboard),
                        Triple(CyberScreen.DEVICES, "Nodos", Icons.Default.Devices),
                        Triple(CyberScreen.PARENTAL_CONTROL, "Parental", Icons.Default.FamilyRestroom),
                        Triple(CyberScreen.LOCATION_RADAR, "Radar", Icons.Default.Radar),
                        Triple(CyberScreen.SECURITY_CENTER, "Seguridad", Icons.Default.Security)
                    )

                    navItems.forEach { (screen, label, icon) ->
                        val isSelected = uiState.currentScreen == screen
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.navigateTo(screen) },
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = label,
                                    tint = if (isSelected) CyberNeonGreen else CyberTextSecondary
                                )
                            },
                            label = {
                                Text(
                                    text = label,
                                    color = if (isSelected) CyberNeonGreen else CyberTextSecondary,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = CyberNeonGreen.copy(alpha = 0.15f)
                            ),
                            modifier = Modifier.testTag("nav_item_${label.lowercase()}")
                        )
                    }

                    // Plus button for expanded all 13 modules menu
                    NavigationBarItem(
                        selected = showNavigationDrawer,
                        onClick = { showNavigationDrawer = true },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú Completo",
                                tint = CyberElectricCyan
                            )
                        },
                        label = {
                            Text(
                                text = "Menú",
                                color = CyberElectricCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = CyberElectricCyan.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_item_menu")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Screen router
            AnimatedContent(
                targetState = uiState.currentScreen,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "screen_transition"
            ) { targetScreen ->
                when (targetScreen) {
                    CyberScreen.LANDING -> LandingScreen(viewModel = viewModel)
                    CyberScreen.AUTH -> AuthScreen(viewModel = viewModel)
                    CyberScreen.DASHBOARD -> DashboardScreen(viewModel = viewModel, uiState = uiState)
                    CyberScreen.DEVICES -> DevicesScreen(viewModel = viewModel, devices = uiState.devices)
                    CyberScreen.PAIRING_QR -> PairingScreen(viewModel = viewModel, pairingToken = uiState.pairingToken)
                    CyberScreen.PARENTAL_CONTROL -> ParentalControlScreen(
                        viewModel = viewModel,
                        rules = uiState.rules,
                        devices = uiState.devices
                    )
                    CyberScreen.LOCATION_RADAR -> LocationRadarScreen(
                        viewModel = viewModel,
                        location = uiState.location,
                        geofences = uiState.geofences
                    )
                    CyberScreen.SECURITY_CENTER -> SecurityCenterScreen(
                        viewModel = viewModel,
                        sessions = uiState.sessions,
                        events = uiState.securityEvents
                    )
                    CyberScreen.EVENT_HISTORY -> EventHistoryScreen(
                        viewModel = viewModel,
                        events = uiState.securityEvents
                    )
                    CyberScreen.PERMISSIONS_MANAGER -> PermissionsScreen(
                        viewModel = viewModel,
                        permissions = uiState.permissions
                    )
                    CyberScreen.ACCOUNT_SETTINGS -> AccountSettingsScreen(
                        viewModel = viewModel,
                        user = uiState.user,
                        isSimulationMode = uiState.isSimulationModeActive
                    )
                    CyberScreen.PRIVACY_CONSENT -> PrivacyConsentScreen(viewModel = viewModel)
                    CyberScreen.HELP_SUPPORT -> HelpSupportScreen(viewModel = viewModel)
                }
            }

            // Notification Overlay Toast
            uiState.notificationMessage?.let { msg ->
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp, start = 20.dp, end = 20.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    color = CyberSurfaceElevated,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CyberNeonGreen),
                    shadowElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = CyberNeonGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = msg,
                            color = CyberTextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }

    // Modal Bottom Sheet with All 13 Modules
    if (showNavigationDrawer) {
        ModalBottomSheet(
            onDismissRequest = { showNavigationDrawer = false },
            containerColor = CyberSurface,
            scrimColor = CyberBackground.copy(alpha = 0.7f),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .width(40.dp)
                        .height(4.dp)
                        .background(CyberBorder, RoundedCornerShape(2.dp))
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "MÓDULOS DE CYBERLINK CONTROL AI",
                        color = CyberNeonGreen,
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    CyberBadge(text = "13 MÓDULOS", color = CyberElectricCyan)
                }

                Spacer(modifier = Modifier.height(14.dp))

                val allModules = listOf(
                    CyberScreen.LANDING to Icons.Default.Home,
                    CyberScreen.AUTH to Icons.Default.Lock,
                    CyberScreen.DASHBOARD to Icons.Default.Dashboard,
                    CyberScreen.DEVICES to Icons.Default.Devices,
                    CyberScreen.PAIRING_QR to Icons.Default.QrCodeScanner,
                    CyberScreen.PARENTAL_CONTROL to Icons.Default.FamilyRestroom,
                    CyberScreen.LOCATION_RADAR to Icons.Default.Radar,
                    CyberScreen.SECURITY_CENTER to Icons.Default.Security,
                    CyberScreen.EVENT_HISTORY to Icons.Default.History,
                    CyberScreen.PERMISSIONS_MANAGER to Icons.Default.AdminPanelSettings,
                    CyberScreen.ACCOUNT_SETTINGS to Icons.Default.Settings,
                    CyberScreen.PRIVACY_CONSENT to Icons.Default.PrivacyTip,
                    CyberScreen.HELP_SUPPORT to Icons.Default.HelpCenter
                )

                allModules.chunked(2).forEach { rowPair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowPair.forEach { (screen, icon) ->
                            val isSelected = uiState.currentScreen == screen
                            Surface(
                                onClick = {
                                    showNavigationDrawer = false
                                    viewModel.navigateTo(screen)
                                },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) CyberSurfaceElevated else CyberSurfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSelected) CyberNeonGreen else CyberBorder
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .heightIn(min = 48.dp)
                                    .testTag("drawer_item_${screen.name.lowercase()}")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = if (isSelected) CyberNeonGreen else CyberTextSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = screen.title,
                                        color = if (isSelected) CyberNeonGreen else CyberTextPrimary,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                        if (rowPair.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
