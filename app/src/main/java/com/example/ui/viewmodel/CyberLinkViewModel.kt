package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.*
import com.example.data.repository.CyberLinkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class CyberScreen(val title: String, val iconName: String) {
    LANDING("Portal", "home"),
    AUTH("Acceso", "lock"),
    DASHBOARD("Panel Central", "dashboard"),
    DEVICES("Dispositivos", "devices"),
    PAIRING_QR("Vincular QR", "qr_code"),
    PARENTAL_CONTROL("Control Parental", "family_restroom"),
    LOCATION_RADAR("Radar GPS", "radar"),
    SECURITY_CENTER("Centro de Seguridad", "security"),
    EVENT_HISTORY("Historial Eventos", "history"),
    PERMISSIONS_MANAGER("Permisos", "admin_panel_settings"),
    ACCOUNT_SETTINGS("Configuración", "settings"),
    PRIVACY_CONSENT("Privacidad", "privacy_tip"),
    HELP_SUPPORT("Ayuda & Docs", "help")
}

data class CyberUiState(
    val currentScreen: CyberScreen = CyberScreen.LANDING,
    val navigationStack: List<CyberScreen> = listOf(CyberScreen.LANDING),
    val user: UserProfile? = null,
    val devices: List<LinkedDevice> = emptyList(),
    val rules: List<ParentalRule> = emptyList(),
    val location: SharedLocation? = null,
    val geofences: List<GeofenceZone> = emptyList(),
    val securityEvents: List<SecurityEvent> = emptyList(),
    val sessions: List<ActiveSession> = emptyList(),
    val permissions: List<CyberPermission> = emptyList(),
    val pairingToken: PairingToken? = null,
    val notificationMessage: String? = null,
    val isSystemArmed: Boolean = true,
    val isSimulationModeActive: Boolean = true
)

class CyberLinkViewModel(
    private val repository: CyberLinkRepository = CyberLinkRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(CyberUiState())
    val uiState: StateFlow<CyberUiState> = _uiState.asStateFlow()

    init {
        // Observe repository flows
        viewModelScope.launch {
            repository.currentUser.collect { user ->
                _uiState.value = _uiState.value.copy(
                    user = user,
                    currentScreen = if (user != null) CyberScreen.DASHBOARD else CyberScreen.LANDING
                )
            }
        }
        viewModelScope.launch {
            repository.devices.collect { devs ->
                _uiState.value = _uiState.value.copy(devices = devs)
            }
        }
        viewModelScope.launch {
            repository.rules.collect { rulesList ->
                _uiState.value = _uiState.value.copy(rules = rulesList)
            }
        }
        viewModelScope.launch {
            repository.location.collect { loc ->
                _uiState.value = _uiState.value.copy(location = loc)
            }
        }
        viewModelScope.launch {
            repository.geofences.collect { geofencesList ->
                _uiState.value = _uiState.value.copy(geofences = geofencesList)
            }
        }
        viewModelScope.launch {
            repository.securityEvents.collect { events ->
                _uiState.value = _uiState.value.copy(securityEvents = events)
            }
        }
        viewModelScope.launch {
            repository.sessions.collect { sess ->
                _uiState.value = _uiState.value.copy(sessions = sess)
            }
        }
        viewModelScope.launch {
            repository.permissions.collect { perms ->
                _uiState.value = _uiState.value.copy(permissions = perms)
            }
        }
        viewModelScope.launch {
            repository.activePairingToken.collect { token ->
                _uiState.value = _uiState.value.copy(pairingToken = token)
            }
        }
    }

    fun navigateTo(screen: CyberScreen) {
        val current = _uiState.value.currentScreen
        if (current == screen) return
        val newStack = _uiState.value.navigationStack + screen
        _uiState.value = _uiState.value.copy(
            currentScreen = screen,
            navigationStack = newStack
        )
    }

    fun navigateBack(): Boolean {
        val stack = _uiState.value.navigationStack
        return if (stack.size > 1) {
            val updatedStack = stack.dropLast(1)
            _uiState.value = _uiState.value.copy(
                currentScreen = updatedStack.last(),
                navigationStack = updatedStack
            )
            true
        } else {
            false
        }
    }

    fun login(email: String, role: UserRole) {
        repository.login(email, role)
        showNotification("Sesión iniciada con éxito para $email")
        navigateTo(CyberScreen.DASHBOARD)
    }

    fun logout() {
        repository.logout()
        showNotification("Sesión cerrada")
        _uiState.value = _uiState.value.copy(
            currentScreen = CyberScreen.LANDING,
            navigationStack = listOf(CyberScreen.LANDING)
        )
    }

    fun generateNewPairingCode() {
        repository.generateNewPairingCode()
        showNotification("Nuevo código de vinculación generado")
    }

    fun pairDevice(name: String, model: String, assignedProfile: String) {
        repository.pairNewDevice(name, model, assignedProfile)
        showNotification("Dispositivo '$name' vinculado y verificado")
        navigateTo(CyberScreen.DEVICES)
    }

    fun unlinkDevice(deviceId: String) {
        repository.unlinkDevice(deviceId)
        showNotification("Dispositivo desvinculado con éxito")
    }

    fun renameDevice(deviceId: String, newName: String) {
        repository.renameDevice(deviceId, newName)
        showNotification("Nombre de dispositivo actualizado")
    }

    fun toggleDeviceScreenLock(deviceId: String) {
        repository.toggleDeviceScreenLock(deviceId)
    }

    fun updateDailyLimit(deviceId: String, limitMinutes: Int) {
        repository.updateDailyLimit(deviceId, limitMinutes)
        showNotification("Límite diario establecido en $limitMinutes min")
    }

    fun toggleRule(ruleId: String) {
        repository.toggleRule(ruleId)
    }

    fun toggleLocationTracking() {
        repository.toggleLocationTracking()
    }

    fun refreshLocationPing() {
        repository.refreshLocationPing()
        showNotification("Coordenadas GPS sincronizadas")
    }

    fun terminateSession(sessionId: String) {
        repository.terminateSession(sessionId)
        showNotification("Sesión remota finalizada")
    }

    fun togglePermission(key: String) {
        repository.togglePermission(key)
    }

    fun toggleSimulationMode() {
        val next = !_uiState.value.isSimulationModeActive
        _uiState.value = _uiState.value.copy(isSimulationModeActive = next)
        showNotification(if (next) "Modo Simulación / Nodos Demo Activo" else "Modo Estricto Producción Activo")
    }

    fun toggleSystemArm() {
        val next = !_uiState.value.isSystemArmed
        _uiState.value = _uiState.value.copy(isSystemArmed = next)
        showNotification(if (next) "Escudo Cibernético ARMADO" else "Escudo Cibernético EN PAUSA")
    }

    fun exportDataJson(): String {
        return repository.exportUserDataJson()
    }

    fun wipeAllData() {
        repository.wipeAllUserData()
        showNotification("Todos los datos locales fueron eliminados")
        navigateTo(CyberScreen.DASHBOARD)
    }

    fun showNotification(msg: String) {
        _uiState.value = _uiState.value.copy(notificationMessage = msg)
    }

    fun clearNotification() {
        _uiState.value = _uiState.value.copy(notificationMessage = null)
    }
}
