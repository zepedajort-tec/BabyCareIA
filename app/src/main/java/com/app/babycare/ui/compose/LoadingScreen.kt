package com.app.babycare.ui.compose

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Primary = Color(0xFFA2D2FF)
private val PastelPink = Color(0xFFFFC8DD)
private val LightYellow = Color(0xFFFFF2B2)
private val TextMain = Color(0xFF111318)
private val TextSecondary = Color(0xFF636f88)

/**
 * LoadingScreen con aro giratorio y pulso central sincronizado.
 *
 * @param sizeOuter tamaño del contenedor del conjunto (aro + círculo).
 * @param ringStroke grosor del aro externo.
 * @param rotationDurationMs duración de una rotación completa (ms).
 * @param pulseEveryRotations cuántas rotaciones corresponden a un pulso pronunciado (ej. 2 = pulso cada 2 giros).
 */
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    sizeOuter: Int = 140,
    ringStroke: Float = 10f,
    rotationDurationMs: Int = 800,
    pulseEveryRotations: Int = 2,
) {
    val transition = rememberInfiniteTransition(label = "loadingTransition")

    // Rotación del aro: 0..360 repetidamente (una vuelta en rotationDurationMs)
    val rotationAngle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = rotationDurationMs, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationAngle"
    )

    // Pulso: queremos un pulso pronunciado cada pulseEveryRotations * rotationDurationMs
    val pulseDuration = rotationDurationMs * pulseEveryRotations

    // pulseScale: con keyframes para dar "latido" pronunciado en el 60% del ciclo y volver
    val pulseScale by transition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.0f, // ignored by keyframes but required by signature
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = pulseDuration
                // empieza pequeño
                0.98f at 0
                // crecimiento rápido a 1.12 en ~40% del ciclo
                1.12f at (pulseDuration * 40 / 100)
                // volver a leve 1.03 en 60%
                1.03f at (pulseDuration * 60 / 100)
                // pequeño reposo
                1.0f at pulseDuration
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseScale"
    )

    // pulseAlpha: ligera variación de brillo para acompañar el pulso
    val pulseAlpha by transition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = pulseDuration
                0.7f at 0
                0.95f at (pulseDuration * 45 / 100)
                0.85f at (pulseDuration * 70 / 100)
                0.7f at pulseDuration
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseAlpha"
    )

    Box(
        modifier = modifier.padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Contenedor central (aro + pulso)
            Box(
                modifier = Modifier
                    .size(sizeOuter.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Aro exterior: dibujado en Canvas y rotado por rotationAngle
                Canvas(modifier = Modifier.size(sizeOuter.dp)) {
                    val stroke = ringStroke
                    val radius = (size.minDimension / 2f) - stroke / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)

                    // Fondo del aro
                    drawArc(
                        color = PastelPink.copy(alpha = 0.22f),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = stroke, cap = StrokeCap.Round)
                    )

                    // Aro animado (segmento giratorio)
                    val sweep = 90f
                    val start = rotationAngle
                    drawArc(
                        brush = Brush.sweepGradient(listOf(Primary, Primary.copy(alpha = 0.6f), PastelPink)),
                        startAngle = start,
                        sweepAngle = sweep,
                        useCenter = false,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = stroke, cap = StrokeCap.Round)
                    )
                }

                // Círculo interno con pulso sincronizado
                Box(
                    modifier = Modifier
                        .size((sizeOuter * 0.6).dp)
                        .scale(pulseScale)
                        .background(LightYellow.copy(alpha = pulseAlpha), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "\uD83D\uDC76", // icono baby (puedes sustituir por Icon)
                        color = Primary,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Verificando tus datos...",
                color = TextMain,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Un momento por favor, estamos preparando todo para ti.",
                color = TextSecondary,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    MaterialTheme {
        LoadingScreen()
    }
}
