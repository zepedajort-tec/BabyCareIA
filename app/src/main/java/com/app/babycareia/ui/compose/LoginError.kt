package com.app.babycareia.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

// Color tokens taken from your Tailwind config
private val Primary = Color(0xFFB3E5FC)       // primary
private val Accent = Color(0xFFFFC1E3)        // accent
private val MutedRed = Color(0xFFE57373)      // muted-red
private val TextLight = Color(0xFF546E7A)     // text-light
private val FieldBgLight = Color(0xFFFFFFFF)  // field-bg-light
private val FieldBorderLight = Color(0xFFD1D5DB) // field-border-light

/**
 * Error popup shown on top of the screen.
 *
 * @param visible show/hide the popup
 * @param title title text (default "Error de inicio de sesión")
 * @param message body message
 * @param onConfirm called when user taps the primary button
 * @param onDismiss called when background is clicked (optional)
 */
@Composable
fun ErrorLoginPopup(
    visible: Boolean,
    title: String = "Error de inicio de sesión",
    message: String = "Usuario o contraseña incorrectos. Por favor, inténtalo de nuevo.",
    onConfirm: () -> Unit,
    onDismiss: (() -> Unit)? = null
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        // Full screen overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable(enabled = onDismiss != null) { onDismiss?.invoke() },
            contentAlignment = Alignment.Center
        ) {
            // Card container
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = FieldBgLight,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .widthIn(max = 380.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Icon circle
                    Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = MutedRed.copy(alpha = 0.18f),
                        modifier = Modifier
                            .size(64.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            /*Icon(
                                imageVector = Icons.Default.Error,
                                contentDescription = "Error",
                                tint = MutedRed,
                                modifier = Modifier.size(30.dp)
                            )*/
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Title
                    Text(
                        text = title,
                        color = TextLight,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Message
                    Text(
                        text = message,
                        color = TextLight.copy(alpha = 0.78f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm button
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(containerColor = MutedRed),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                    ) {
                        Text(text = "Entendido", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF1F8FF)
@Composable
private fun ErrorLoginPopupPreview() {
    // Preview with popup visible
    ErrorLoginPopup(
        visible = true,
        onConfirm = {}
    )
}
