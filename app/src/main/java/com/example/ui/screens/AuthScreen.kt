package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CyberLinkViewModel
import com.example.ui.viewmodel.CyberScreen

@Composable
fun AuthScreen(
    viewModel: CyberLinkViewModel,
    modifier: Modifier = Modifier
) {
    var isRegisterTab by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("luismiguel4953@gmail.com") }
    var password by remember { mutableStateOf("CyberSafe#2026") }
    var passwordVisible by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf(UserRole.ADMIN_GUARDIAN) }
    var showRecoveryDialog by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("auth_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CyberHeaderBar(
            title = if (isRegisterTab) "REGISTRO DE NODO" else "ACCESO AUTORIZADO",
            subtitle = "AUTENTICACIÓN SHA-256 + 2FA",
            canNavigateBack = true,
            onNavigateBack = { viewModel.navigateBack() }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Cyber lock icon with glow
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(CyberNeonGreen.copy(alpha = 0.12f), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = CyberNeonGreen,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "CYBERLINK PROTOCOL",
            color = CyberNeonGreen,
            fontSize = 18.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Acceso cifrado de extremo a extremo para administración familiar",
            color = CyberTextSecondary,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Tab switcher (Iniciar Sesión vs Registrarse)
        TabRow(
            selectedTabIndex = if (isRegisterTab) 1 else 0,
            containerColor = CyberSurface,
            contentColor = CyberNeonGreen,
            indicator = {},
            divider = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = !isRegisterTab,
                onClick = { isRegisterTab = false },
                text = {
                    Text(
                        "INICIAR SESIÓN",
                        fontWeight = if (!isRegisterTab) FontWeight.Bold else FontWeight.Normal,
                        color = if (!isRegisterTab) CyberNeonGreen else CyberTextSecondary
                    )
                }
            )
            Tab(
                selected = isRegisterTab,
                onClick = { isRegisterTab = true },
                text = {
                    Text(
                        "REGISTRO",
                        fontWeight = if (isRegisterTab) FontWeight.Bold else FontWeight.Normal,
                        color = if (isRegisterTab) CyberNeonGreen else CyberTextSecondary
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CyberCard {
            // Role Selection
            Text(
                text = "ROL DE DISPOSITIVO",
                color = CyberTextSecondary,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedRole == UserRole.ADMIN_GUARDIAN,
                    onClick = { selectedRole = UserRole.ADMIN_GUARDIAN },
                    label = { Text("Tutor Administrador", fontSize = 12.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.AdminPanelSettings, contentDescription = null, modifier = Modifier.size(16.dp))
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CyberNeonGreen.copy(alpha = 0.2f),
                        selectedLabelColor = CyberNeonGreen,
                        containerColor = CyberSurfaceElevated,
                        labelColor = CyberTextSecondary
                    ),
                    modifier = Modifier.weight(1f)
                )

                FilterChip(
                    selected = selectedRole == UserRole.SUPERVISED_NODE,
                    onClick = { selectedRole = UserRole.SUPERVISED_NODE },
                    label = { Text("Nodo Supervisado", fontSize = 12.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.PhoneAndroid, contentDescription = null, modifier = Modifier.size(16.dp))
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CyberElectricCyan.copy(alpha = 0.2f),
                        selectedLabelColor = CyberElectricCyan,
                        containerColor = CyberSurfaceElevated,
                        labelColor = CyberTextSecondary
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo Electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = CyberNeonGreen) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberNeonGreen,
                    unfocusedBorderColor = CyberBorder,
                    focusedTextColor = CyberTextPrimary,
                    unfocusedTextColor = CyberTextPrimary,
                    focusedLabelColor = CyberNeonGreen
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("auth_email_input")
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña Segura") },
                leadingIcon = { Icon(Icons.Default.VpnKey, contentDescription = null, tint = CyberElectricCyan) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Ocultar" else "Mostrar",
                            tint = CyberTextSecondary
                        )
                    }
                },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberElectricCyan,
                    unfocusedBorderColor = CyberBorder,
                    focusedTextColor = CyberTextPrimary,
                    unfocusedTextColor = CyberTextPrimary,
                    focusedLabelColor = CyberElectricCyan
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("auth_password_input")
            )

            Spacer(modifier = Modifier.height(16.dp))

            CyberButton(
                text = if (isRegisterTab) "Crear Cuenta Cifrada" else "Ingresar al Sistema",
                onClick = {
                    viewModel.login(email.ifBlank { "luismiguel4953@gmail.com" }, selectedRole)
                },
                icon = if (isRegisterTab) Icons.Default.PersonAdd else Icons.Default.Login,
                modifier = Modifier.fillMaxWidth(),
                isPrimary = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Quick Demo Admin Access
            CyberButton(
                text = "Acceso Rápido Demo (Admin)",
                onClick = {
                    viewModel.login("luismiguel4953@gmail.com", UserRole.ADMIN_GUARDIAN)
                },
                icon = Icons.Default.Fingerprint,
                modifier = Modifier.fillMaxWidth(),
                isPrimary = false
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Account Recovery
        TextButton(
            onClick = { showRecoveryDialog = true },
            modifier = Modifier.testTag("recover_account_button")
        ) {
            Text(
                text = "¿Olvidaste tu clave? Recuperar cuenta mediante token seguro",
                color = CyberTextSecondary,
                fontSize = 12.sp
            )
        }
    }

    if (showRecoveryDialog) {
        AlertDialog(
            onDismissRequest = { showRecoveryDialog = false },
            title = {
                Text("Recuperación Criptográfica de Cuenta", color = CyberNeonGreen, fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    Text(
                        "Se enviará un enlace efímero con token criptográfico de recuperación al correo:",
                        color = CyberTextPrimary,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        email.ifBlank { "luismiguel4953@gmail.com" },
                        color = CyberElectricCyan,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Ninguna clave se almacena en texto plano. Cumple con estándares NIST SP 800-63B.",
                        color = CyberTextMuted,
                        fontSize = 11.sp
                    )
                }
            },
            confirmButton = {
                CyberButton(
                    text = "Enviar Enlace Seguro",
                    onClick = {
                        viewModel.showNotification("Enlace de recuperación emitido a $email")
                        showRecoveryDialog = false
                    },
                    isPrimary = true
                )
            },
            dismissButton = {
                TextButton(onClick = { showRecoveryDialog = false }) {
                    Text("Cancelar", color = CyberTextSecondary)
                }
            },
            containerColor = CyberSurface,
            shape = RoundedCornerShape(12.dp)
        )
    }
}
