package com.app.babycare.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.babycare.ui.viewmodel.LoginUiState

private val Primary = Color(0xFFA2D2FF)        // primary
private val BackgroundLight = Color(0xFFFEFEFE) // background-light
private val PastelPink = Color(0xFFFFC8DD)     // pastel-pink
private val TextMain = Color(0xFF111318)       // text-main
private val TextSecondary = Color(0xFF636f88)  // text-secondary
private val BorderColor = Color(0xFFBDE0FE)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenWithViewModel(
    loginUiState: LoginUiState,
    onLogin: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onClearError: () -> Unit,
) {
    // Local input states (UI-owned)
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Control de popup de error
    var showErrorPopup by remember { mutableStateOf(false) }
    var errorTitle by remember { mutableStateOf("Error de inicio de sesión") }
    var errorMessage by remember { mutableStateOf("") }

    // Función de validación de email
    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Función para validar campos de login
    fun validateLoginFields(): String? {
        // Validar usuario/email
        if (username.trim().isEmpty()) {
            return "El campo de usuario no puede estar vacío"
        }

        if (!isValidEmail(username.trim())) {
            return "Correo incorrecto"
        }

        // Validar contraseña
        if (password.isEmpty()) {
            return "La contraseña no puede estar vacía"
        }

        if (password.length <= 5) {
            return "Contraseña incorrecta, debe tener más de 5 caracteres"
        }

        return null
    }

    // Mostrar error cuando cambia el estado
    if (loginUiState.error != null && !showErrorPopup) {
        errorTitle = "Error de inicio de sesión"
        errorMessage = loginUiState.error
        showErrorPopup = true
    }

    val fieldColors = TextFieldDefaults.colors(
        focusedTextColor = TextMain,
        unfocusedTextColor = TextMain,
        disabledTextColor = TextMain.copy(alpha = 0.6f),

        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        disabledContainerColor = Color.White,

        focusedIndicatorColor = Primary,
        unfocusedIndicatorColor = BorderColor,
        disabledIndicatorColor = BorderColor,

        focusedLeadingIconColor = Primary,
        unfocusedLeadingIconColor = TextSecondary,
        disabledLeadingIconColor = TextSecondary,

        focusedTrailingIconColor = Primary,
        unfocusedTrailingIconColor = TextSecondary,

        focusedLabelColor = Primary,
        unfocusedLabelColor = TextSecondary,
        focusedPlaceholderColor = Primary,
        unfocusedPlaceholderColor = TextSecondary
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (loginUiState.isLoading) {
            LoadingScreen()
        } else {
            // Pantalla de login normal
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = BackgroundLight
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                        .imePadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Logo
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(PastelPink, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "\uD83D\uDC76",
                            fontSize = 50.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Bienvenido/a de nuevo",
                        color = TextMain,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Usuario/Email
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Correo Electrónico") },
                        placeholder = { Text("Ingresa tu correo") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = fieldColors,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Contraseña
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        placeholder = { Text("Ingresa tu contraseña") },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            Text(
                                text = if (passwordVisible) "Ocultar" else "Mostrar",
                                modifier = Modifier
                                    .clickable { passwordVisible = !passwordVisible }
                                    .padding(8.dp),
                                color = Primary
                            )
                        },
                        visualTransformation = if (passwordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = fieldColors,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        color = TextSecondary,
                        textAlign = TextAlign.End,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* acción: navegar a forgot password */ }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón de iniciar sesión con validación
                    Button(
                        onClick = {
                            // Validar campos antes de hacer login
                            val validationError = validateLoginFields()
                            if (validationError != null) {
                                // Mostrar error de validación
                                errorTitle = "Error de validación"
                                errorMessage = validationError
                                showErrorPopup = true
                            } else {
                                onLogin(username.trim(), password)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !loginUiState.isLoading
                    ) {
                        Text(
                            "Iniciar sesión",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        buildAnnotatedString {
                            append("¿Aún no tienes cuenta? ")
                            withStyle(
                                style = SpanStyle(
                                    color = Primary,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Regístrate")
                            }
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clickable { onNavigateToRegister() }
                            .padding(top = 8.dp)
                    )
                }
            }
        }

        ErrorPopup(
            visible = showErrorPopup,
            title = errorTitle,
            message = errorMessage,
            onConfirm = {
                showErrorPopup = false
                onClearError()
            },
            onDismiss = {
                showErrorPopup = false
                onClearError()
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFEFEFE)
@Composable
private fun LoginScreenWithViewModelPreview() {
    MaterialTheme {
        LoginScreenWithViewModel(
            loginUiState = LoginUiState(),
            onLogin = { _, _ -> },
            onNavigateToRegister = {},
            onClearError = {}
        )
    }
}
