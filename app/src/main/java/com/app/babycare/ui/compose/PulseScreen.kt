package com.app.babycare.ui.compose

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

private val Primary = Color(0xFFA2D2FF)
private val LightYellow = Color(0xFFFFF2B2)
private val PastelPink = Color(0xFFFFC8DD)

/**
 * PulseCircle: círculo con animación de pulso.
 *
 * @param size tamaño base del círculo (sin escala).
 * @param minScale escala mínima del pulso.
 * @param maxScale escala máxima del pulso.
 * @param durationMs duración de cada ciclo (ms).
 * @param color color del círculo central.
 * @param showIconText texto para mostrar dentro del círculo (puede ser un emoji).
 */
@Composable
fun PulseCircle(
    size: Dp = 80.dp,
    minScale: Float = 0.95f,
    maxScale: Float = 1.12f,
    durationMs: Int = 900,
    color: Color = LightYellow,
    showIconText: String = "\uD83D\uDC76" // emoji bebé por defecto
) {
    // transición infinita para el pulso
    val transition = rememberInfiniteTransition(label = "pulseTransition")

    // escala (crece y se reduce)
    val scale by transition.animateFloat(
        initialValue = minScale,
        targetValue = maxScale,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMs, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    // alpha secundario para dar sensación de pulso al brillo
    val alpha by transition.animateFloat(
        initialValue = 0.6f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMs, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    // sombra dinámica (convertimos dp según escala para que se vea natural)
    val baseShadowDp = 6.dp
    val shadowDp = with(LocalDensity.current) { (baseShadowDp.value * (scale)).dp }

    Box(
        modifier = Modifier
            .size(size)
            .scale(scale) // aplica la escala
            .clip(CircleShape)
            .shadow(elevation = shadowDp, shape = CircleShape)
            .background(color.copy(alpha = alpha)),
        contentAlignment = Alignment.Center
    ) {
        // Contenido central (emoji / icono / texto)
        Text(
            text = showIconText,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            color = Primary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PulseCirclePreview() {
    PulseCircle()
}
