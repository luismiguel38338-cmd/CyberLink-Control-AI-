package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.data.model.SecurityEvent
import com.example.data.model.SecuritySeverity
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CyberLinkViewModel

@Composable
fun EventHistoryScreen(
    viewModel: CyberLinkViewModel,
    events: List<SecurityEvent>,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf<SecuritySeverity?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredEvents = events.filter { ev ->
        (selectedFilter == null || ev.severity == selectedFilter) &&
                (searchQuery.isBlank() || ev.title.contains(searchQuery, ignoreCase = true) || ev.description.contains(searchQuery, ignoreCase = true))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBackground)
            .padding(16.dp)
            .testTag("event_history_screen")
    ) {
        CyberHeaderBar(
            title = "BITÁCORA DE AUDITORÍA",
            subtitle = "${filteredEvents.size} REGISTROS CERTIFICADOS",
            canNavigateBack = true,
            onNavigateBack = { viewModel.navigateBack() }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Filtrar por evento, nodo o IP...", fontSize = 12.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = CyberNeonGreen) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberNeonGreen,
                unfocusedBorderColor = CyberBorder,
                focusedTextColor = CyberTextPrimary,
                unfocusedTextColor = CyberTextPrimary
            ),
            modifier = Modifier.fillMaxWidth().testTag("events_search_field")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Severity Filter Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedFilter == null,
                onClick = { selectedFilter = null },
                label = { Text("Todos (${events.size})", fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = CyberNeonGreen.copy(alpha = 0.2f),
                    selectedLabelColor = CyberNeonGreen,
                    containerColor = CyberSurfaceElevated,
                    labelColor = CyberTextSecondary
                ),
                modifier = Modifier.weight(1f)
            )

            FilterChip(
                selected = selectedFilter == SecuritySeverity.CRITICAL,
                onClick = { selectedFilter = SecuritySeverity.CRITICAL },
                label = { Text("Críticos", fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = CyberAlertRed.copy(alpha = 0.2f),
                    selectedLabelColor = CyberAlertRed,
                    containerColor = CyberSurfaceElevated,
                    labelColor = CyberTextSecondary
                ),
                modifier = Modifier.weight(1f)
            )

            FilterChip(
                selected = selectedFilter == SecuritySeverity.WARNING,
                onClick = { selectedFilter = SecuritySeverity.WARNING },
                label = { Text("Alertas", fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = CyberWarningAmber.copy(alpha = 0.2f),
                    selectedLabelColor = CyberWarningAmber,
                    containerColor = CyberSurfaceElevated,
                    labelColor = CyberTextSecondary
                ),
                modifier = Modifier.weight(1f)
            )

            FilterChip(
                selected = selectedFilter == SecuritySeverity.INFO,
                onClick = { selectedFilter = SecuritySeverity.INFO },
                label = { Text("Info", fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = CyberElectricCyan.copy(alpha = 0.2f),
                    selectedLabelColor = CyberElectricCyan,
                    containerColor = CyberSurfaceElevated,
                    labelColor = CyberTextSecondary
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Events Terminal Stream
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredEvents) { ev ->
                val severityColor = when (ev.severity) {
                    SecuritySeverity.CRITICAL -> CyberAlertRed
                    SecuritySeverity.WARNING -> CyberWarningAmber
                    SecuritySeverity.INFO -> CyberNeonGreen
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(1.dp, severityColor.copy(alpha = 0.35f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CyberBadge(
                                    text = ev.severity.name,
                                    color = severityColor
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = ev.timestamp,
                                    color = CyberTextMuted,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }

                            CyberBadge(
                                text = ev.result.name,
                                color = if (ev.result.name == "SUCCESS") CyberNeonGreen else CyberAlertRed
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = ev.title,
                            color = CyberTextPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = ev.description,
                            color = CyberTextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Nodo: ${ev.deviceAffected}",
                                color = CyberElectricCyan,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "IP: ${ev.sourceIp}",
                                color = CyberTextMuted,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }
    }
}
