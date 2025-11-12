package com.app.babycare.ui.compose

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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.babycare.ui.viewmodel.RegisterEvent
import com.app.babycare.ui.viewmodel.RegisterViewModel
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
    onNavigateToAddBaby: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var relation by remember { mutableStateOf("") }
    var ageText by remember { mutableStateOf("") }
    var showRelationMenu by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Observe ViewModel UI state
    val state by viewModel.uiState.collectAsState()

    // Control de popup de error
    var showErrorPopup by remember { mutableStateOf(false) }
    var errorTitle by remember { mutableStateOf("Error de registro") }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { ev ->
            when (ev) {
                is RegisterEvent.NavigateToAddBaby -> onNavigateToAddBaby()
                is RegisterEvent.NavigateToLogin -> onNavigateToLogin()
                is RegisterEvent.ShowMessage -> {
                    errorTitle = "Error de registro"
                    errorMessage = ev.message
                    showErrorPopup = true
                }
            }
        }
    }

    LaunchedEffect(state.error) {
        if (state.error != null) {
            errorTitle = "Error de registro"
            errorMessage = state.error!!
            showErrorPopup = true
        }
    }

    // Función de validación de email
    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Función para validar todos los campos
    fun validateFields(): String? {
        // Validar nombre
        if (name.trim().isEmpty()) {
            return "Nombre incorrecto"
        }

        // Validar correo
        if (!isValidEmail(email.trim())) {
            return "Correo incorrecto"
        }

        // Validar teléfono
        if (phone.trim().length != 10) {
            return "Teléfono incorrecto"
        }

        // Validar edad
        val age = ageText.toIntOrNull()
        if (age == null || age <= 0) {
            return "Edad incorrecta"
        }

        // Validar relación
        if (relation.trim().isEmpty()) {
            return "Selecciona una relación (Padre/Madre/Otro)"
        }

        // Validar contraseña
        if (password.length <= 5) {
            return "Contraseña incorrecta, debe tener más de 5 caracteres"
        }

        return null
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

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            LoadingScreen()
        } else {
            // Pantalla de registro normal
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = BackgroundLight
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .wrapContentHeight()
                        .verticalScroll(rememberScrollState())
                        .imePadding(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    // Logo circle
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(PastelPink, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "\uD83D\uDC76",
                            fontSize = 40.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Title
                    Text(
                        text = "Crea tu cuenta",
                        color = TextMain,
                        fontSize = 28.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Name
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text("Nombre") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = fieldColors
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("Correo Electrónico") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = fieldColors,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Phone
                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            // Limitar a 10 dígitos
                            if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                                phone = it
                            }
                        },
                        placeholder = { Text("Teléfono") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = fieldColors,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Age and Relation row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Age
                        OutlinedTextField(
                            value = ageText,
                            onValueChange = { new ->
                                // Permitir solo dígitos y máximo 3 caracteres
                                if (new.all { it.isDigit() } && new.length <= 3) {
                                    ageText = new
                                }
                            },
                            placeholder = { Text("Edad") },
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = fieldColors,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        Box(modifier = Modifier.weight(1f)) {
                            // Campo readOnly que abre el menú
                            OutlinedTextField(
                                value = relation,
                                onValueChange = { /* no editable directamente */ },
                                placeholder = { Text("Padre/Madre") },
                                readOnly = true,
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.KeyboardArrowDown,
                                        contentDescription = "Abrir",
                                        tint = if (showRelationMenu) Primary else TextSecondary
                                    )
                                },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = fieldColors
                            )

                            // Capa clickable invisible sobre el TextField
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .clickable { showRelationMenu = !showRelationMenu }
                            )

                            DropdownMenu(
                                expanded = showRelationMenu,
                                onDismissRequest = { showRelationMenu = false },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .background(Color.White, RoundedCornerShape(12.dp))
                                    .shadow(4.dp, RoundedCornerShape(12.dp))
                            ) {
                                listOf("Padre", "Madre", "Otro").forEach { option ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = option,
                                                color = if (relation == option) Primary else TextMain
                                            )
                                        },
                                        onClick = {
                                            relation = option
                                            showRelationMenu = false
                                        },
                                        modifier = Modifier.background(
                                            if (relation == option) Primary.copy(alpha = 0.1f)
                                            else Color.Transparent
                                        )
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Password
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Contraseña") },
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Register button
                    Button(
                        onClick = {
                            // Validar campos antes de registrar
                            val validationError = validateFields()
                            if (validationError != null) {
                                // Mostrar error de validación
                                errorTitle = "Error de validación"
                                errorMessage = validationError
                                showErrorPopup = true
                            } else {
                                val ageInt = ageText.toInt()
                                viewModel.register(
                                    name = name.trim(),
                                    email = email.trim(),
                                    password = password,
                                    phone = phone.trim(),
                                    relation = relation.trim(),
                                    age = ageInt,
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(2.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !state.isLoading
                    ) {
                        Text(text = "Registrarse", color = Color.White)
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
                            modifier = Modifier
                                .clickable { onNavigateToLogin() }
                                .padding(start = 4.dp),
                            textDecoration = TextDecoration.Underline
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        ErrorPopup(
            visible = showErrorPopup,
            title = errorTitle,
            message = errorMessage,
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
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(
        onNavigateToLogin = {}
    )
}
