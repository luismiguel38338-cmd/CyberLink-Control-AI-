package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun CyberCard(
    modifier: Modifier = Modifier,
    borderColor: Color = CyberBorder,
    glowEffect: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("cyber_card"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = CyberSurface
        ),
        border = BorderStroke(
            width = if (glowEffect) 1.5.dp else 1.dp,
            brush = if (glowEffect) {
                Brush.horizontalGradient(
                    listOf(CyberNeonGreen, CyberElectricCyan)
                )
            } else {
                Brush.linearGradient(listOf(borderColor, borderColor.copy(alpha = 0.4f)))
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
fun CyberBadge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = CyberNeonGreen,
    icon: ImageVector? = null
) {
    Surface(
        modifier = modifier.testTag("cyber_badge"),
        shape = RoundedCornerShape(6.dp),
        color = color.copy(alpha = 0.15f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = text,
                color = color,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun CyberButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    isPrimary: Boolean = true,
    isDestructive: Boolean = false,
    enabled: Boolean = true
) {
    val baseColor = when {
        isDestructive -> CyberAlertRed
        isPrimary -> CyberNeonGreen
        else -> CyberElectricCyan
    }

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .heightIn(min = 48.dp)
            .testTag("cyber_btn_${text.lowercase().replace(" ", "_")}"),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPrimary) baseColor else Color.Transparent,
            contentColor = if (isPrimary) CyberBackground else baseColor,
            disabledContainerColor = CyberSurfaceElevated,
            disabledContentColor = CyberTextMuted
        ),
        border = if (!isPrimary) BorderStroke(1.2.dp, baseColor) else null,
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun CyberMetricCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    accentColor: Color = CyberNeonGreen
) {
    Card(
        modifier = modifier
            .testTag("metric_${title.lowercase().replace(" ", "_")}"),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
        border = BorderStroke(1.dp, CyberBorder)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title.uppercase(),
                    color = CyberTextSecondary,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Medium
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                color = CyberTextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                color = accentColor,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun CyberHeaderBar(
    title: String,
    subtitle: String? = null,
    canNavigateBack: Boolean = false,
    onNavigateBack: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("cyber_header_bar"),
        color = CyberSurface,
        border = BorderStroke(1.dp, CyberBorder.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (canNavigateBack) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        tint = CyberNeonGreen
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            } else {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberSurfaceElevated)
                        .border(1.dp, CyberNeonGreen.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "CyberLink Logo",
                        tint = CyberNeonGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = CyberTextPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = CyberNeonGreen,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                content = actions
            )
        }
    }
}

@Composable
fun CyberRadarScope(
    modifier: Modifier = Modifier,
    accuracyMeters: Float = 10f,
    deviceCount: Int = 2
) {
    val infiniteTransition = rememberInfiniteTransition(label = "radar_sweep")
    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sweep_angle"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(CyberSurface)
            .border(1.dp, CyberBorder, RoundedCornerShape(12.dp))
            .testTag("cyber_radar_scope"),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val maxRadius = minOf(size.width, size.height) * 0.42f

            // Concentric range circles
            val rings = 4
            for (i in 1..rings) {
                val r = maxRadius * (i.toFloat() / rings)
                drawCircle(
                    color = CyberBorder,
                    radius = r,
                    center = center,
                    style = Stroke(
                        width = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f)
                    )
                )
            }

            // Crosshairs
            drawLine(
                color = CyberBorder,
                start = Offset(center.x - maxRadius, center.y),
                end = Offset(center.x + maxRadius, center.y),
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                color = CyberBorder,
                start = Offset(center.x, center.y - maxRadius),
                end = Offset(center.x, center.y + maxRadius),
                strokeWidth = 1.dp.toPx()
            )

            // Radar sweep cone
            drawArc(
                brush = Brush.sweepGradient(
                    0.0f to Color.Transparent,
                    0.85f to Color.Transparent,
                    0.98f to CyberNeonGreen.copy(alpha = 0.25f),
                    1.0f to CyberNeonGreen.copy(alpha = 0.5f),
                    center = center
                ),
                startAngle = sweepAngle - 45f,
                sweepAngle = 45f,
                useCenter = true,
                topLeft = Offset(center.x - maxRadius, center.y - maxRadius),
                size = androidx.compose.ui.geometry.Size(maxRadius * 2, maxRadius * 2)
            )

            // Central Base Station
            drawCircle(
                color = CyberElectricCyan,
                radius = 6.dp.toPx(),
                center = center
            )
            drawCircle(
                color = CyberElectricCyan.copy(alpha = 0.3f),
                radius = 12.dp.toPx(),
                center = center
            )

            // Target Blips
            val blip1 = Offset(center.x + maxRadius * 0.45f, center.y - maxRadius * 0.35f)
            val blip2 = Offset(center.x - maxRadius * 0.55f, center.y + maxRadius * 0.25f)

            drawCircle(color = CyberNeonGreen, radius = 5.dp.toPx(), center = blip1)
            drawCircle(color = CyberNeonGreen.copy(alpha = 0.25f), radius = 14.dp.toPx(), center = blip1)

            drawCircle(color = CyberWarningAmber, radius = 4.dp.toPx(), center = blip2)
            drawCircle(color = CyberWarningAmber.copy(alpha = 0.25f), radius = 10.dp.toPx(), center = blip2)
        }

        // Radar Overlay Labels
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
        ) {
            Text(
                text = "RADAR GNSS MIL-STD",
                color = CyberNeonGreen,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "PRECISIÓN: ±${accuracyMeters.toInt()}m | NODOS: $deviceCount",
                color = CyberTextSecondary,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        Text(
            text = "CANAL SEGURO ENCRIPTADO",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            color = CyberElectricCyan,
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun CyberQrMatrixBox(
    code: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF03060A))
            .border(1.5.dp, CyberNeonGreen, RoundedCornerShape(12.dp))
            .padding(16.dp)
            .testTag("cyber_qr_box"),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val grid = 11
            val stepX = size.width / grid
            val stepY = size.height / grid

            // Draw QR-like pattern with corner finder markers
            for (i in 0 until grid) {
                for (j in 0 until grid) {
                    val isCornerTopLeft = i < 3 && j < 3
                    val isCornerTopRight = i > grid - 4 && j < 3
                    val isCornerBottomLeft = i < 3 && j > grid - 4

                    val shouldFill = when {
                        isCornerTopLeft -> (i == 0 || i == 2 || j == 0 || j == 2) || (i == 1 && j == 1)
                        isCornerTopRight -> (i == grid - 1 || i == grid - 3 || j == 0 || j == 2) || (i == grid - 2 && j == 1)
                        isCornerBottomLeft -> (i == 0 || i == 2 || j == grid - 1 || j == grid - 3) || (i == 1 && j == grid - 2)
                        (i + j * 3 + code.hashCode()) % 2 == 0 -> true
                        else -> false
                    }

                    if (shouldFill) {
                        drawRect(
                            color = if (isCornerTopLeft || isCornerTopRight || isCornerBottomLeft) CyberElectricCyan else CyberNeonGreen,
                            topLeft = Offset(i * stepX + 1.dp.toPx(), j * stepY + 1.dp.toPx()),
                            size = androidx.compose.ui.geometry.Size(stepX - 2.dp.toPx(), stepY - 2.dp.toPx())
                        )
                    }
                }
            }
        }

        // Inner glowing code badge
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = CyberSurfaceElevated.copy(alpha = 0.9f),
            border = BorderStroke(1.dp, CyberNeonGreen)
        ) {
            Text(
                text = code,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                color = CyberNeonGreen,
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
