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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Color tokens from your tailwind config
private val Primary = Color(0xFFA2D2FF)
private val BackgroundLight = Color(0xFFFEFEFE)
private val PastelPink = Color(0xFFFFC8DD)
private val LightYellow = Color(0xFFFFF2B2)
private val SoftGray = Color(0xFFBDE0FE)
private val TextMain = Color(0xFF111318)
private val TextSecondary = Color(0xFF636f88)
private val BorderColor = Color(0xFFBDE0FE)

data class HealthRecordItem(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun HomeScreen(
    userGreeting: String = "Mamá de Lucía",
    onProfileClick: () -> Unit = {},
    onChildFriendlyClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onTipClick: () -> Unit = {},
    onViewAllTips: () -> Unit = {},
    onNewRecordClick: () -> Unit = {},
    onViewRecordsClick: () -> Unit = {},
    onRecordClick: (HealthRecordItem) -> Unit = {}
) {
    Surface(modifier = Modifier.fillMaxSize(), color = BackgroundLight) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Header(
                    userGreeting = userGreeting,
                    onChildFriendlyClick = onChildFriendlyClick,
                    onLogoutClick = onLogoutClick
                )
            }

            item {
                // Tip del Día
                Text(
                    text = "Tip del Día",
                    color = TextMain,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = LightYellow),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .clickable { onTipClick() }
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFFE08A)),
                            contentAlignment = Alignment.Center
                        ) {
                            /*Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Tip",
                                tint = Color(0xFFBB8C05),
                                modifier = Modifier.size(20.dp)
                            )*/
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "La importancia del tummy time",
                                color = TextMain,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Colocar a tu bebé boca abajo durante cortos períodos mientras está despierto ayuda a fortalecer los músculos del cuello y los hombros.",
                                color = TextSecondary,
                                style = MaterialTheme.typography.bodySmall
                            )

                            Text(
                                text = "Ver todos los tips",
                                color = Primary,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .padding(top = 8.dp)
                                    .clickable { onViewAllTips() }
                            )
                        }
                    }
                }
            }

            item {
                // Registro de Salud header
                Text(
                    text = "Registro de Salud",
                    color = TextMain,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                )
            }

            item {
                // Two action cards
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    CardAction(
                        label = "Nuevo Registro",
                        iconTint = Primary,
                        iconBg = Primary.copy(alpha = 0.2f),
                        modifier = Modifier.weight(1f),
                        onClick = onNewRecordClick,
                        icon = Icons.Default.Add
                    )
                    CardAction(
                        label = "Ver Registros",
                        iconTint = Color(0xFFD96D8D),
                        iconBg = PastelPink.copy(alpha = 0.25f),
                        icon = Icons.Default.Home,
                        modifier = Modifier.weight(1f),
                        onClick = onViewRecordsClick
                    )
                }
            }

            item {
                // Últimos Registros
                Text(
                    text = "Últimos Registros",
                    color = TextMain,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp)
                )
            }

            // Sample items
            val sampleRecords = listOf(
                HealthRecordItem("Vacuna", "Pentavalente - Hoy, 9:00 AM", Icons.Default.Add /* placeholder icon */),
                HealthRecordItem("Medición", "68cm, 7.5kg - Ayer", Icons.Default.Add),
                HealthRecordItem("Medicamento", "Paracetamol - Hace 2 días", Icons.Default.Add)
            )

            items(sampleRecords) { item ->
                HealthRecordRow(item = item, onClick = { onRecordClick(item) })
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun Header(
    userGreeting: String,
    onChildFriendlyClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(PastelPink),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "\uD83D\uDC76", // icono baby (puedes sustituir por Icon)
                    color = Primary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Column {
                Text(text = "Hola de nuevo,", color = TextSecondary, style = MaterialTheme.typography.bodySmall)
                Text(text = userGreeting, color = TextMain, fontWeight = FontWeight.Bold)
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = onChildFriendlyClick) {
                //Icon(imageVector = Icons.Default.ReceiptLong, contentDescription = "ChildFriendly", tint = TextSecondary)
            }
            IconButton(onClick = onLogoutClick) {
                Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Logout", tint = TextSecondary)
            }
        }
    }
}

@Composable
private fun CardAction(
    label: String,
    iconTint: Color = Primary,
    iconBg: Color = Primary.copy(alpha = 0.3f),
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .height(112.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = label, tint = iconTint)
            }
            Text(text = label, color = TextMain, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun HealthRecordRow(item: HealthRecordItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SoftGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = item.icon, contentDescription = item.title, tint = Primary)
                }

                Column {
                    Text(text = item.title, color = TextMain, fontWeight = FontWeight.Medium)
                    Text(text = item.subtitle, color = TextSecondary, style = MaterialTheme.typography.bodySmall)
                }
            }

            //Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Open", tint = TextSecondary)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFEFEFE)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
