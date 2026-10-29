package com.example.data.repository

import com.example.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class CyberLinkRepository {

    private val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private fun now(): String = timeFormat.format(Date())

    private val _currentUser = MutableStateFlow<UserProfile?>(
        UserProfile(
            id = "usr_admin_01",
            name = "Luis Miguel (Admin)",
            email = "luismiguel4953@gmail.com",
            role = UserRole.ADMIN_GUARDIAN,
            familyGroupId = "CYBER-NODE-8891",
            is2FAEnabled = true,
            avatarInitials = "LM"
        )
    )
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    private val _devices = MutableStateFlow<List<LinkedDevice>>(
        listOf(
            LinkedDevice(
                id = "dev_local_01",
                name = "Nodo Local (Este Dispositivo)",
                deviceType = DeviceType.SMARTPHONE,
                modelName = "Pixel 8 Pro (Simulador)",
                osVersion = "Android 15 (VanillaIceCream)",
                appVersion = "v2.4.0-ai",
                connectionStatus = DeviceConnectionStatus.ONLINE,
                lastSyncTime = "Hace 2 minutos",
                batteryPercent = 92,
                isCharging = true,
                isDemoNode = false,
                isScreenLocked = false,
                screenTimeMinutesToday = 95,
                dailyLimitMinutes = 240,
                assignedProfileName = "Supervisor Primario",
                authorizedPermissions = listOf("GPS Fino", "Alertas del Sistema", "Notificaciones de Seguridad"),
                ipAddress = "192.168.1.102",
                pairingDate = "2026-10-20 09:15"
            ),
            LinkedDevice(
                id = "dev_demo_02",
                name = "Galaxy S23 (Hijo - Mateo)",
                deviceType = DeviceType.SMARTPHONE,
                modelName = "Samsung SM-S911B",
                osVersion = "Android 14 (OneUI 6.1)",
                appVersion = "v2.4.0-ai",
                connectionStatus = DeviceConnectionStatus.ONLINE,
                lastSyncTime = "Hace 5 minutos",
                batteryPercent = 64,
                isCharging = false,
                isDemoNode = true,
                isScreenLocked = false,
                screenTimeMinutesToday = 180,
                dailyLimitMinutes = 210,
                assignedProfileName = "Mateo (13 años)",
                authorizedPermissions = listOf("Ubicación en Tiempo Real", "Estadísticas de Uso", "Filtro de Contenido"),
                ipAddress = "192.168.1.115",
                pairingDate = "2026-10-22 17:40"
            ),
            LinkedDevice(
                id = "dev_demo_03",
                name = "Tablet Escolar (Sofía)",
                deviceType = DeviceType.TABLET,
                modelName = "Samsung Galaxy Tab S9",
                osVersion = "Android 14",
                appVersion = "v2.3.9-ai",
                connectionStatus = DeviceConnectionStatus.IDLE,
                lastSyncTime = "Hace 42 minutos",
                batteryPercent = 38,
                isCharging = false,
                isDemoNode = true,
                isScreenLocked = false,
                screenTimeMinutesToday = 75,
                dailyLimitMinutes = 180,
                assignedProfileName = "Sofía (10 años)",
                authorizedPermissions = listOf("Límite de Tiempo", "Bloqueo Nocturno"),
                ipAddress = "192.168.1.121",
                pairingDate = "2026-10-25 11:05"
            )
        )
    )
    val devices: StateFlow<List<LinkedDevice>> = _devices.asStateFlow()

    private val _rules = MutableStateFlow<List<ParentalRule>>(
        listOf(
            ParentalRule(
                id = "rule_01",
                title = "Toque de Queda Nocturno (Dormir)",
                description = "Bloquea aplicaciones recreativas entre las 21:30 y 07:00 para asegurar descanso.",
                category = RuleCategory.SCHEDULE,
                isEnabled = true,
                requiresNativeAdmin = true,
                scheduleTime = "21:30 - 07:00",
                detailValue = "Activo Lun-Dom"
            ),
            ParentalRule(
                id = "rule_02",
                title = "Protección de Navegación Segura",
                description = "Filtro DNS cifrado contra contenido para adultos, phishing y dominios maliciosos.",
                category = RuleCategory.SAFE_BROWSING,
                isEnabled = true,
                requiresNativeAdmin = false,
                detailValue = "DNS Cloudflare Family + Quad9"
            ),
            ParentalRule(
                id = "rule_03",
                title = "Pausas de Salud Visual (Cada 45 min)",
                description = "Emite un aviso sonoro y overlay sutil sugiriendo descansar la vista 5 minutos.",
                category = RuleCategory.HEALTH_BREAK,
                isEnabled = true,
                requiresNativeAdmin = false,
                detailValue = "Intervalo: 45 min"
            ),
            ParentalRule(
                id = "rule_04",
                title = "Aprobación de Nuevas Aplicaciones",
                description = "Requiere confirmación expresa del administrador antes de instalar nuevos APKs o apps.",
                category = RuleCategory.APP_FILTER,
                isEnabled = true,
                requiresNativeAdmin = true,
                detailValue = "Vía Notificación Push Cifrada"
            ),
            ParentalRule(
                id = "rule_05",
                title = "Horario de Estudio Escolar",
                description = "Restringe redes sociales y juegos de lunes a viernes entre 08:00 y 14:00.",
                category = RuleCategory.SCHEDULE,
                isEnabled = false,
                requiresNativeAdmin = true,
                scheduleTime = "08:00 - 14:00",
                detailValue = "Modo Concentración"
            )
        )
    )
    val rules: StateFlow<List<ParentalRule>> = _rules.asStateFlow()

    private val _location = MutableStateFlow(
        SharedLocation(
            deviceId = "dev_demo_02",
            deviceName = "Galaxy S23 (Mateo)",
            latitude = 19.4326,
            longitude = -99.1332,
            accuracyMeters = 8.5f,
            addressName = "Av. Reforma 222, Zona Segura Centro",
            lastUpdated = "Hace 3 minutos",
            isTrackingActive = true,
            batteryAtPing = 64
        )
    )
    val location: StateFlow<SharedLocation> = _location.asStateFlow()

    private val _geofences = MutableStateFlow<List<GeofenceZone>>(
        listOf(
            GeofenceZone("geo_01", "Hogar Familiar", "Hogar", 150, true),
            GeofenceZone("geo_02", "Colegio Bilingüe", "Colegio", 250, false),
            GeofenceZone("geo_03", "Complejo Deportivo", "Parque", 300, false)
        )
    )
    val geofences: StateFlow<List<GeofenceZone>> = _geofences.asStateFlow()

    private val _securityEvents = MutableStateFlow<List<SecurityEvent>>(
        listOf(
            SecurityEvent(
                id = "ev_01",
                timestamp = "2026-10-29 11:15:20",
                title = "Inicio de sesión seguro verificado",
                description = "Autenticación 2FA exitosa para admin luismiguel4953@gmail.com desde nodo certificado.",
                severity = SecuritySeverity.INFO,
                result = SecurityStatusResult.SUCCESS,
                sourceIp = "189.204.12.8",
                deviceAffected = "Panel Web / Android"
            ),
            SecurityEvent(
                id = "ev_02",
                timestamp = "2026-10-29 10:45:11",
                title = "Intento de desinstalación mitigado",
                description = "Dispositivo 'Galaxy S23' intentó revocar permisos de administración sin PIN de tutor.",
                severity = SecuritySeverity.WARNING,
                result = SecurityStatusResult.BLOCKED,
                sourceIp = "192.168.1.115",
                deviceAffected = "Galaxy S23 (Mateo)"
            ),
            SecurityEvent(
                id = "ev_03",
                timestamp = "2026-10-29 09:30:00",
                title = "Toque de queda nocturno finalizado",
                description = "Se desbloquearon las aplicaciones recreativas programadas según la regla familiar.",
                severity = SecuritySeverity.INFO,
                result = SecurityStatusResult.SUCCESS,
                sourceIp = "Sistema Interno",
                deviceAffected = "Todos los nodos"
            ),
            SecurityEvent(
                id = "ev_04",
                timestamp = "2026-10-28 22:14:05",
                title = "Alerta de geocerca: Salida no programada",
                description = "Dispositivo 'Tablet Escolar' abandonó radio de geocerca 'Hogar Familiar'.",
                severity = SecuritySeverity.CRITICAL,
                result = SecurityStatusResult.ALERT,
                sourceIp = "GPS Satelital",
                deviceAffected = "Tablet Escolar (Sofía)"
            )
        )
    )
    val securityEvents: StateFlow<List<SecurityEvent>> = _securityEvents.asStateFlow()

    private val _sessions = MutableStateFlow<List<ActiveSession>>(
        listOf(
            ActiveSession(
                id = "ses_01",
                deviceName = "Terminal Local Android (AI Studio)",
                clientType = "Aplicación Android CyberLink Control v2.4",
                ipAddress = "189.204.12.8",
                location = "Ciudad de México, MX",
                lastActive = "En línea (Sesión Actual)",
                isCurrent = true
            ),
            ActiveSession(
                id = "ses_02",
                deviceName = "Panel Web CyberLink (Chrome en Linux)",
                clientType = "Portal Web Seguro HTTPS/TLS 1.3",
                ipAddress = "189.204.12.9",
                location = "Ciudad de México, MX",
                lastActive = "Hace 15 minutos",
                isCurrent = false
            ),
            ActiveSession(
                id = "ses_03",
                deviceName = "MacBook Pro Administrador",
                clientType = "CyberLink Desktop Hub",
                ipAddress = "201.144.89.2",
                location = "Guadalajara, MX",
                lastActive = "Ayer a las 20:30",
                isCurrent = false
            )
        )
    )
    val sessions: StateFlow<List<ActiveSession>> = _sessions.asStateFlow()

    private val _permissions = MutableStateFlow<List<CyberPermission>>(
        listOf(
            CyberPermission(
                key = "perm_location",
                name = "Ubicación en Tiempo Real (GPS)",
                description = "Permite compartir coordenadas precisas entre miembros autorizados de la familia.",
                whyNeeded = "Requerido para la función de radar de ubicación y geocercas seguras.",
                isGranted = true,
                isSystemEnforced = true
            ),
            CyberPermission(
                key = "perm_usage_stats",
                name = "Estadísticas de Uso de Aplicaciones",
                description = "Mide los minutos diarios dedicados a cada categoría sin leer datos internos.",
                whyNeeded = "Necesario para el cómputo de tiempo de pantalla y descanso familiar.",
                isGranted = true,
                isSystemEnforced = true,
                isNativeAndroidOnly = true
            ),
            CyberPermission(
                key = "perm_device_admin",
                name = "Administrador de Dispositivo (Device Admin)",
                description = "Otorga facultad legítima al tutor para bloquear pantalla y evitar borrado forzado.",
                whyNeeded = "Protege el dispositivo supervisado contra alteraciones no autorizadas.",
                isGranted = true,
                isSystemEnforced = true,
                isNativeAndroidOnly = true
            ),
            CyberPermission(
                key = "perm_overlay",
                name = "Mostrar sobre Otras Aplicaciones",
                description = "Permite desplegar avisos de descanso visual y pantallas de bloqueo educativo.",
                whyNeeded = "Despliega recordatorios ergonómicos y alertas críticas inmediatas.",
                isGranted = true,
                isSystemEnforced = false,
                isNativeAndroidOnly = true
            ),
            CyberPermission(
                key = "perm_notifications",
                name = "Alertas del Sistema y Notificaciones",
                description = "Envía eventos de seguridad críticos y solicitudes de permiso al administrador.",
                whyNeeded = "Mantener comunicación cifrada instantánea entre nodos familiares.",
                isGranted = true,
                isSystemEnforced = true
            )
        )
    )
    val permissions: StateFlow<List<CyberPermission>> = _permissions.asStateFlow()

    // Pairing token generator
    private val _activePairingToken = MutableStateFlow(
        PairingToken(
            code = "CYBER-9482",
            qrPayload = "cyberlink://pair?code=CYBER-9482&admin=LuisMiguel&group=CYBER-NODE-8891&exp=600",
            validUntil = "Expira en 9 min 40 seg",
            minutesRemaining = 10,
            adminName = "Luis Miguel",
            familyGroupId = "CYBER-NODE-8891"
        )
    )
    val activePairingToken: StateFlow<PairingToken> = _activePairingToken.asStateFlow()

    fun login(email: String, role: UserRole) {
        _currentUser.value = UserProfile(
            id = "usr_" + UUID.randomUUID().toString().take(6),
            name = if (email.contains("@")) email.substringBefore("@").replaceFirstChar { it.uppercase() } else "Usuario",
            email = email,
            role = role,
            familyGroupId = "CYBER-NODE-8891",
            is2FAEnabled = true,
            avatarInitials = if (email.length >= 2) email.take(2).uppercase() else "CL"
        )
        recordSecurityEvent(
            title = "Sesión iniciada exitosamente",
            description = "Acceso otorgado al usuario $email con rol ${role.name}.",
            severity = SecuritySeverity.INFO,
            result = SecurityStatusResult.SUCCESS
        )
    }

    fun logout() {
        recordSecurityEvent(
            title = "Cierre de sesión registrado",
            description = "El usuario ${_currentUser.value?.email} ha cerrado sesión en este terminal.",
            severity = SecuritySeverity.INFO,
            result = SecurityStatusResult.SUCCESS
        )
        _currentUser.value = null
    }

    fun generateNewPairingCode() {
        val rand = (1000..9999).random()
        val code = "CYBER-$rand"
        _activePairingToken.value = PairingToken(
            code = code,
            qrPayload = "cyberlink://pair?code=$code&admin=${_currentUser.value?.name ?: "Admin"}&group=${_currentUser.value?.familyGroupId ?: "CYBER-NODE"}&exp=600",
            validUntil = "Expira en 10 min",
            minutesRemaining = 10,
            adminName = _currentUser.value?.name ?: "Admin",
            familyGroupId = _currentUser.value?.familyGroupId ?: "CYBER-NODE"
        )
    }

    fun pairNewDevice(name: String, model: String, assignedProfile: String) {
        val newDev = LinkedDevice(
            id = "dev_node_" + UUID.randomUUID().toString().take(6),
            name = name,
            deviceType = DeviceType.SMARTPHONE,
            modelName = model,
            osVersion = "Android 14 (Vinculado)",
            appVersion = "v2.4.0-ai",
            connectionStatus = DeviceConnectionStatus.ONLINE,
            lastSyncTime = "Recién vinculado",
            batteryPercent = (60..98).random(),
            isCharging = false,
            isDemoNode = false,
            isScreenLocked = false,
            screenTimeMinutesToday = 0,
            dailyLimitMinutes = 240,
            assignedProfileName = assignedProfile,
            authorizedPermissions = listOf("Ubicación GPS", "Uso de Aplicaciones", "Alertas del Sistema"),
            ipAddress = "192.168.1." + (120..220).random(),
            pairingDate = now()
        )
        _devices.value = _devices.value + newDev
        recordSecurityEvent(
            title = "Nuevo dispositivo vinculado",
            description = "Se autorizó con consentimiento informado la vinculación de '$name' ($model).",
            severity = SecuritySeverity.INFO,
            result = SecurityStatusResult.SUCCESS,
            deviceAffected = name
        )
        generateNewPairingCode()
    }

    fun unlinkDevice(deviceId: String) {
        val dev = _devices.value.find { it.id == deviceId }
        dev?.let {
            _devices.value = _devices.value.filter { it.id != deviceId }
            recordSecurityEvent(
                title = "Dispositivo desvinculado",
                description = "Se revocó la autorización y credenciales del dispositivo '${it.name}'.",
                severity = SecuritySeverity.WARNING,
                result = SecurityStatusResult.SUCCESS,
                deviceAffected = it.name
            )
        }
    }

    fun renameDevice(deviceId: String, newName: String) {
        _devices.value = _devices.value.map {
            if (it.id == deviceId) it.copy(name = newName) else it
        }
    }

    fun toggleDeviceScreenLock(deviceId: String) {
        _devices.value = _devices.value.map {
            if (it.id == deviceId) {
                val nextLock = !it.isScreenLocked
                recordSecurityEvent(
                    title = if (nextLock) "Bloqueo remoto aplicado" else "Desbloqueo remoto aplicado",
                    description = "Comando de pantalla ${if (nextLock) "BLOQUEADA" else "DESBLOQUEADA"} enviado a '${it.name}'.",
                    severity = if (nextLock) SecuritySeverity.WARNING else SecuritySeverity.INFO,
                    result = SecurityStatusResult.SUCCESS,
                    deviceAffected = it.name
                )
                it.copy(isScreenLocked = nextLock)
            } else it
        }
    }

    fun updateDailyLimit(deviceId: String, newLimitMinutes: Int) {
        _devices.value = _devices.value.map {
            if (it.id == deviceId) it.copy(dailyLimitMinutes = newLimitMinutes) else it
        }
    }

    fun toggleRule(ruleId: String) {
        _rules.value = _rules.value.map {
            if (it.id == ruleId) {
                val next = !it.isEnabled
                recordSecurityEvent(
                    title = "Regla familiar modificada",
                    description = "Regla '${it.title}' cambiada a estado: ${if (next) "ACTIVA" else "DESACTIVADA"}.",
                    severity = SecuritySeverity.INFO,
                    result = SecurityStatusResult.SUCCESS
                )
                it.copy(isEnabled = next)
            } else it
        }
    }

    fun toggleLocationTracking() {
        val curr = _location.value
        val next = !curr.isTrackingActive
        _location.value = curr.copy(isTrackingActive = next)
        recordSecurityEvent(
            title = if (next) "Rastreo de ubicación reactivado" else "Rastreo de ubicación pausado",
            description = "El usuario administrador cambió el estado del radar familiar a: ${if (next) "ACTIVO" else "PAUSADO"}.",
            severity = SecuritySeverity.INFO,
            result = SecurityStatusResult.SUCCESS
        )
    }

    fun refreshLocationPing() {
        val curr = _location.value
        val latJitter = ((1..5).random() - 3) * 0.0005
        val lonJitter = ((1..5).random() - 3) * 0.0005
        _location.value = curr.copy(
            latitude = curr.latitude + latJitter,
            longitude = curr.longitude + lonJitter,
            lastUpdated = "Hace unos segundos",
            accuracyMeters = (5..12).random().toFloat()
        )
        recordSecurityEvent(
            title = "Ping de radar actualizado",
            description = "Telemetría de GPS sincronizada con éxito para ${curr.deviceName}.",
            severity = SecuritySeverity.INFO,
            result = SecurityStatusResult.SUCCESS
        )
    }

    fun terminateSession(sessionId: String) {
        _sessions.value = _sessions.value.filter { it.id != sessionId }
        recordSecurityEvent(
            title = "Sesión remota revocada",
            description = "Se cerró forzosamente el token de acceso para la sesión $sessionId.",
            severity = SecuritySeverity.WARNING,
            result = SecurityStatusResult.SUCCESS
        )
    }

    fun togglePermission(key: String) {
        _permissions.value = _permissions.value.map {
            if (it.key == key) it.copy(isGranted = !it.isGranted) else it
        }
    }

    fun recordSecurityEvent(
        title: String,
        description: String,
        severity: SecuritySeverity,
        result: SecurityStatusResult,
        deviceAffected: String = "Panel Central"
    ) {
        val event = SecurityEvent(
            id = "ev_" + UUID.randomUUID().toString().take(6),
            timestamp = now(),
            title = title,
            description = description,
            severity = severity,
            result = result,
            sourceIp = "189.204.12.8",
            deviceAffected = deviceAffected
        )
        _securityEvents.value = listOf(event) + _securityEvents.value.take(49)
    }

    fun exportUserDataJson(): String {
        val user = _currentUser.value
        val devs = _devices.value
        val rulesList = _rules.value
        return """
        {
          "cyberlink_export_version": "2.4.0",
          "exported_at": "${now()}",
          "account": {
            "id": "${user?.id}",
            "name": "${user?.name}",
            "email": "${user?.email}",
            "role": "${user?.role?.name}",
            "familyGroupId": "${user?.familyGroupId}"
          },
          "devices_count": ${devs.size},
          "devices": [
            ${devs.joinToString(",") { 
                """{"id":"${it.id}","name":"${it.name}","model":"${it.modelName}","status":"${it.connectionStatus}","battery":${it.batteryPercent}}"""
            }}
          ],
          "active_rules": [
            ${rulesList.filter { it.isEnabled }.joinToString(",") { 
                """{"id":"${it.id}","title":"${it.title}"}"""
            }}
          ],
          "gdpr_compliance": {
            "consent_revocable": true,
            "zero_surveillance_certified": true,
            "encrypted_transit": "TLS_1_3_AES_GCM"
          }
        }
        """.trimIndent()
    }

    fun wipeAllUserData() {
        _devices.value = emptyList()
        _rules.value = emptyList()
        _securityEvents.value = listOf(
            SecurityEvent(
                id = "ev_wipe",
                timestamp = now(),
                title = "Restablecimiento de fábrica y borrado seguro",
                description = "Todos los registros de dispositivos, perfiles y reglas fueron purgados conforme al Derecho al Olvido.",
                severity = SecuritySeverity.CRITICAL,
                result = SecurityStatusResult.SUCCESS
            )
        )
    }
}
