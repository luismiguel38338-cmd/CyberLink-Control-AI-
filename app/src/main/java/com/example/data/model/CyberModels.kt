package com.example.data.model

enum class UserRole {
    ADMIN_GUARDIAN,
    SUPERVISED_NODE
}

data class UserProfile(
    val id: String = "usr_admin_01",
    val name: String = "Comandante Luis",
    val email: String = "admin@cyberlink.ai",
    val role: UserRole = UserRole.ADMIN_GUARDIAN,
    val familyGroupId: String = "CYBER-FAM-9821",
    val is2FAEnabled: Boolean = true,
    val avatarInitials: String = "CL"
)

enum class DeviceType {
    SMARTPHONE,
    TABLET,
    PC_TERMINAL
}

enum class DeviceConnectionStatus {
    ONLINE,
    IDLE,
    OFFLINE
}

data class LinkedDevice(
    val id: String,
    val name: String,
    val deviceType: DeviceType,
    val modelName: String,
    val osVersion: String,
    val appVersion: String = "v2.4.0-ai",
    val connectionStatus: DeviceConnectionStatus,
    val lastSyncTime: String,
    val batteryPercent: Int,
    val isCharging: Boolean = false,
    val isDemoNode: Boolean = false,
    val isScreenLocked: Boolean = false,
    val screenTimeMinutesToday: Int = 145,
    val dailyLimitMinutes: Int = 240,
    val assignedProfileName: String,
    val authorizedPermissions: List<String> = listOf("Ubicación GPS", "Uso de Aplicaciones", "Alertas del Sistema"),
    val ipAddress: String = "192.168.1.104",
    val pairingDate: String = "2026-10-15 14:20"
)

enum class RuleCategory {
    SCHEDULE,
    APP_FILTER,
    SAFE_BROWSING,
    HEALTH_BREAK
}

data class ParentalRule(
    val id: String,
    val title: String,
    val description: String,
    val category: RuleCategory,
    val isEnabled: Boolean,
    val requiresNativeAdmin: Boolean,
    val scheduleTime: String? = null,
    val detailValue: String? = null
)

data class SharedLocation(
    val deviceId: String,
    val deviceName: String,
    val latitude: Double,
    val longitude: Double,
    val accuracyMeters: Float,
    val addressName: String,
    val lastUpdated: String,
    val isTrackingActive: Boolean = true,
    val batteryAtPing: Int = 84
)

data class GeofenceZone(
    val id: String,
    val name: String,
    val type: String, // "Hogar", "Colegio", "Parque"
    val radiusMeters: Int,
    val isInside: Boolean
)

enum class SecuritySeverity {
    INFO,
    WARNING,
    CRITICAL
}

enum class SecurityStatusResult {
    SUCCESS,
    BLOCKED,
    ALERT
}

data class SecurityEvent(
    val id: String,
    val timestamp: String,
    val title: String,
    val description: String,
    val severity: SecuritySeverity,
    val result: SecurityStatusResult,
    val sourceIp: String = "189.204.12.8",
    val deviceAffected: String = "Todos"
)

data class ActiveSession(
    val id: String,
    val deviceName: String,
    val clientType: String,
    val ipAddress: String,
    val location: String,
    val lastActive: String,
    val isCurrent: Boolean
)

data class CyberPermission(
    val key: String,
    val name: String,
    val description: String,
    val whyNeeded: String,
    val isGranted: Boolean,
    val isSystemEnforced: Boolean,
    val isNativeAndroidOnly: Boolean = false
)

data class PairingToken(
    val code: String,
    val qrPayload: String,
    val validUntil: String,
    val minutesRemaining: Int = 10,
    val adminName: String,
    val familyGroupId: String
)
