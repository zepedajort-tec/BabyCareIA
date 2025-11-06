package com.app.babycareia.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.babycareia.ui.viewmodel.RegisterEvent
import com.app.babycareia.ui.viewmodel.RegisterViewModel
import kotlinx.coroutines.flow.collectLatest

// Color tokens mapped from your Tailwind config
private val Primary = Color(0xFFA2D2FF)        // primary
private val BackgroundLight = Color(0xFFFEFEFE) // background-light
private val PastelPink = Color(0xFFFFC8DD)     // pastel-pink
private val TextMain = Color(0xFF111318)       // text-main
private val TextSecondary = Color(0xFF636f88)  // text-secondary
private val BorderColor = Color(0xFFBDE0FE)    // border-color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit = {}
) {
    // UI-local states for inputs (kept in UI; move to VM if you want persistence)
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Observe ViewModel UI state
    val state by viewModel.uiState.collectAsState()

    // Popup control
    var showErrorPopup by remember { mutableStateOf(false) }

    // Collect ViewModel events (navigation / show message)
    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { ev ->
            when (ev) {
                is RegisterEvent.NavigateToLogin -> onNavigateToLogin()
                is RegisterEvent.ShowMessage -> {
                    showErrorPopup = true
                }
            }
        }
    }

    // If the ViewModel exposes an error string in uiState, show popup as well
    LaunchedEffect(state.error) {
        showErrorPopup = state.error != null
    }

    // Field colors for consistency (Material3)
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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundLight
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentHeight()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo circle
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(PastelPink, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    // You can place an Icon here
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Title
                Text(
                    text = "Crea tu cuenta",
                    color = TextMain,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Name input
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text("Ingresa tu nombre") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = fieldColors
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Email input
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("Ingresa tu correo electrónico") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = fieldColors
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Password input
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = {
                            Text("Crea tu contraseña")
                        },
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = fieldColors
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Register button — calls ViewModel
                Button(
                    onClick = { viewModel.register(name = name.trim(), email = email.trim(), password = password) },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(2.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text(text = "Registrarse", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Link to login
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "¿Ya tienes una cuenta? ", color = TextSecondary)
                    Text(
                        text = "Inicia sesión",
                        color = Primary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable { onNavigateToLogin() }
                            .padding(start = 4.dp),
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }

    // Error popup; when dismissed, clear error in VM
    ErrorLoginPopup(
        visible = showErrorPopup,
        title = "Error de registro",
        message = state.error ?: "No se pudo registrar",
        onConfirm = {
            showErrorPopup = false
            viewModel.clearError()
        },
        onDismiss = {
            showErrorPopup = false
            viewModel.clearError()
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(
        onNavigateToLogin = { /* Preview navigation action */ }
    )
}
