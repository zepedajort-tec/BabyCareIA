package com.app.babycare.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.babycare.domain.model.BabySex
import com.app.babycare.ui.viewmodel.AddBabyUiState

// Paleta de colores del diseño
private val SoftBlue = Color(0xFFC9E4F9)
private val SoftPink = Color(0xFFFAD3E2)
private val TextPrimary = Color(0xFF2D3748)
private val TextSecondary = Color(0xFF718096)
private val BackgroundLight = Color(0xFFFEFCF8)
private val Accent = Color(0xFFF2B8C6)
private val Primary = Color(0xFF4A90E2)
private val PinkDark = Color(0xFFD96D8D)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBabyScreen(
    uiState: AddBabyUiState,
    onSaveBaby: (name: String, ageMonths: Int, sex: BabySex, weight: Float?, height: Float?) -> Unit,
    onBackClick: () -> Unit,
    onClearError: () -> Unit
) {
    var babyName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var selectedSex by remember { mutableStateOf<BabySex?>(null) }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var isBackVisible by remember { mutableStateOf(false) }

    // Control de popup de error
    var showErrorPopup by remember { mutableStateOf(false) }
    var errorTitle by remember { mutableStateOf("Error de validación") }
    var errorMessage by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    // Mostrar error del estado si existe
    if (uiState.error != null && !showErrorPopup) {
        errorTitle = "Error de registro"
        errorMessage = uiState.error
        showErrorPopup = true
    }

    // Función para validar todos los campos
    fun validateFields(): String? {
        // Validar nombre
        if (babyName.trim().isEmpty()) {
            return "El nombre del bebé no puede estar vacío"
        }

        // Validar edad
        val ageInt = age.toIntOrNull()
        if (age.isEmpty() || ageInt == null) {
            return "La edad no puede estar vacía"
        }
        if (ageInt < 0 || ageInt > 36) {
            return "La edad debe estar entre 0 y 36 meses (0-3 años)"
        }

        // Validar peso
        val weightFloat = weight.toFloatOrNull()
        if (weight.isEmpty() || weightFloat == null) {
            return "El peso no puede estar vacío"
        }
        if (weightFloat < 0 || weightFloat > 15) {
            return "El peso debe estar entre 0 y 15 kg"
        }

        // Validar altura
        val heightFloat = height.toFloatOrNull()
        if (height.isEmpty() || heightFloat == null) {
            return "La altura no puede estar vacía"
        }
        if (heightFloat < 0 || heightFloat > 100) {
            return "La altura debe estar entre 0 y 100 cm (0-1 metro)"
        }

        // Validar sexo
        if (selectedSex == null) {
            return "Debes seleccionar el sexo del bebé"
        }

        return null // Todo está correcto
    }

    val fieldColors = TextFieldDefaults.colors(
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedIndicatorColor = Accent,
        unfocusedIndicatorColor = Color(0xFFE5E7EB),
        focusedLabelColor = TextSecondary,
        unfocusedLabelColor = TextSecondary,
        focusedPlaceholderColor = TextSecondary.copy(alpha = 0.6f),
        unfocusedPlaceholderColor = TextSecondary.copy(alpha = 0.6f)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Mostrar loading si está cargando
        if (uiState.isLoading) {
            LoadingScreen()
        } else {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = BackgroundLight
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    // Header con botón de retroceso
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isBackVisible) {
                            IconButton(
                                onClick = onBackClick,
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Volver",
                                    tint = TextSecondary
                                )
                            }
                        }

                        Text(
                            text = "Registrar Bebé",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.size(40.dp))
                    }

                    // Contenido principal
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        // Título y descripción
                        Column(
                            modifier = Modifier.padding(vertical = 16.dp)
                        ) {
                            Text(
                                text = "Conozcamos a tu bebé",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                lineHeight = 36.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Completa los datos de tu pequeño. Podrás añadir más información luego.",
                                fontSize = 16.sp,
                                color = TextSecondary,
                                lineHeight = 24.sp,
                                modifier = Modifier.padding(bottom = 24.dp)
                            )
                        }

                        // Formulario
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Nombre del bebé
                            Column {
                                Text(
                                    text = "Nombre del Bebé",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextSecondary,
                                    modifier = Modifier.padding(bottom = 6.dp)
                                )

                                OutlinedTextField(
                                    value = babyName,
                                    onValueChange = { babyName = it },
                                    placeholder = { Text("Escribe su nombre") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = fieldColors,
                                    singleLine = true
                                )
                            }

                            // Edad
                            Column {
                                Text(
                                    text = "Edad (en meses)",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextSecondary,
                                    modifier = Modifier.padding(bottom = 6.dp)
                                )

                                OutlinedTextField(
                                    value = age,
                                    onValueChange = {
                                        if (it.isEmpty() || (it.all { char -> char.isDigit() } && it.length <= 2)) {
                                            age = it
                                        }
                                    },
                                    placeholder = { Text("0 - 36 meses") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = fieldColors,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true
                                )
                            }

                            // Peso y Altura
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // Peso
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Peso (kg)",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = TextSecondary,
                                        modifier = Modifier.padding(bottom = 6.dp)
                                    )

                                    OutlinedTextField(
                                        value = weight,
                                        onValueChange = {
                                            if (it.isEmpty() || it.matches(Regex("^\\d{0,2}(\\.\\d{0,2})?$"))) {
                                                weight = it
                                            }
                                        },
                                        placeholder = { Text("0 - 15 kg") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(56.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = fieldColors,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                        singleLine = true
                                    )
                                }

                                // Altura
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Altura (cm)",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = TextSecondary,
                                        modifier = Modifier.padding(bottom = 6.dp)
                                    )

                                    OutlinedTextField(
                                        value = height,
                                        onValueChange = {
                                            if (it.isEmpty() || it.matches(Regex("^\\d{0,3}(\\.\\d{0,2})?$"))) {
                                                height = it
                                            }
                                        },
                                        placeholder = { Text("0 - 100 cm") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(56.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = fieldColors,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                        singleLine = true
                                    )
                                }
                            }

                            // Sexo
                            Column {
                                Text(
                                    text = "Sexo",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextSecondary,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Masculino
                                    SexButton(
                                        label = "M",
                                        isSelected = selectedSex == BabySex.MALE,
                                        backgroundColor = SoftBlue,
                                        textColor = Primary,
                                        onClick = { selectedSex = BabySex.MALE },
                                        modifier = Modifier.weight(1f)
                                    )

                                    // Femenino
                                    SexButton(
                                        label = "F",
                                        isSelected = selectedSex == BabySex.FEMALE,
                                        backgroundColor = Accent,
                                        textColor = PinkDark,
                                        onClick = { selectedSex = BabySex.FEMALE },
                                        modifier = Modifier.weight(1f)
                                    )

                                    // Otro
                                    SexButton(
                                        label = "O",
                                        isSelected = selectedSex == BabySex.OTHER,
                                        backgroundColor = Color.White,
                                        textColor = TextSecondary,
                                        onClick = { selectedSex = BabySex.OTHER },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Botón de guardar con validación
                            Button(
                                onClick = {
                                    val validationError = validateFields()
                                    if (validationError != null) {
                                        // Mostrar error de validación
                                        errorTitle = "Error de validación"
                                        errorMessage = validationError
                                        showErrorPopup = true
                                    } else {
                                        // Todo correcto, llamar al callback
                                        val ageInt = age.toInt()
                                        val weightFloat = weight.toFloat()
                                        val heightFloat = height.toFloat()

                                        selectedSex?.let { sex ->
                                            onSaveBaby(
                                                babyName.trim(),
                                                ageInt,
                                                sex,
                                                weightFloat,
                                                heightFloat
                                            )
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(16.dp),
                                enabled = !uiState.isLoading
                            ) {
                                Text(
                                    "Guardar Bebé",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }
                }
            }
        }

        // Error popup
        ErrorPopup(
            visible = showErrorPopup,
            title = errorTitle,
            message = errorMessage,
            onConfirm = {
                showErrorPopup = false
                if (uiState.error != null) {
                    onClearError()
                }
            },
            onDismiss = {
                showErrorPopup = false
                if (uiState.error != null) {
                    onClearError()
                }
            }
        )
    }
}

@Composable
private fun SexButton(
    label: String,
    isSelected: Boolean,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .background(
                color = backgroundColor.copy(alpha = if (isSelected) 0.5f else 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .then(
                if (isSelected) {
                    Modifier.border(2.dp, textColor, RoundedCornerShape(12.dp))
                } else {
                    Modifier.border(1.dp, backgroundColor, RoundedCornerShape(12.dp))
                }
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddBabyScreenPreview() {
    AddBabyScreen(
        uiState = AddBabyUiState(),
        onSaveBaby = { _, _, _, _, _ -> },
        onBackClick = {},
        onClearError = {}
    )
}