package com.app.babycareia.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.babycareia.ui.viewmodel.LoginEvent
import com.app.babycareia.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenWithViewModel(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    // Local input states (UI-owned)
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Theme / color tokens
    val primaryColor = Color(0xFFA2D2FF)
    val backgroundLight = Color(0xFFFEFEFE)
    val pastelPink = Color(0xFFFFC8DD)
    val textMain = Color(0xFF111318)
    val textSecondary = Color(0xFF636F88)

    // Observe ViewModel state
    val state by viewModel.uiState.collectAsState()

    // Show error popup when uiState.error != null
    var showErrorPopup by remember { mutableStateOf(false) }

    // Collect events (navigation / messages)
    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is LoginEvent.NavigateToHome -> onNavigateToHome()
                is LoginEvent.NavigateToRegister -> onNavigateToRegister()
                is LoginEvent.ShowMessage -> {
                    // show popup (or could use snackbar)
                    showErrorPopup = true
                }
            }
        }
    }

    // When state.error appears, enable popup (in case event wasn't emitted)
    LaunchedEffect(state.error) {
        showErrorPopup = state.error != null
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundLight
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(pastelPink, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                /*Icon(
                    imageVector = Icons.Filled.ChildCare,
                    contentDescription = "BabyCare",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )*/
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Bienvenido/a de nuevo",
                color = textMain,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Usuario
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario") },
                placeholder = { Text("Ingresa tu usuario") },
                leadingIcon = {
                    //Icon(imageVector = Icons.Filled.Person, contentDescription = null, tint = textSecondary)
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = primaryColor,
                    unfocusedIndicatorColor = textSecondary.copy(alpha = 0.4f),
                    focusedLabelColor = primaryColor,
                    unfocusedLabelColor = textSecondary
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                placeholder = { Text("Ingresa tu contraseña") },
                leadingIcon = {
                    //Icon(imageVector = Icons.Filled.Lock, contentDescription = null, tint = textSecondary)
                },
                trailingIcon = {
                    /*val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null, tint = textSecondary)
                    }*/
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = primaryColor,
                    unfocusedIndicatorColor = textSecondary.copy(alpha = 0.4f),
                    focusedLabelColor = primaryColor,
                    unfocusedLabelColor = textSecondary
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "¿Olvidaste tu contraseña?",
                color = textSecondary,
                textAlign = TextAlign.End,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* acción: podrías navegar a forgot password */ }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.login(username, password) },
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    // compact indicator
                    androidx.compose.material3.CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp, color = Color.White)
                } else {
                    Text("Iniciar sesión", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                buildAnnotatedString {
                    append("¿Aún no tienes cuenta? ")
                    withStyle(style = SpanStyle(color = primaryColor, fontWeight = FontWeight.Bold)) {
                        append("Regístrate")
                    }
                },
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable { viewModel.navigateToRegister() }
                    .padding(top = 8.dp)
            )
        }
    }

    // Error popup (reusable component). When dismissed, clear error in VM.
    ErrorLoginPopup(
        visible = showErrorPopup,
        title = "Error de inicio de sesión",
        message = state.error ?: "Credenciales inválidas",
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

@Preview(showBackground = true, backgroundColor = 0xFFFEFEFE)
@Composable
private fun LoginScreenWithViewModelPreview() {
    MaterialTheme {
        LoginScreenWithViewModel()
    }
}
