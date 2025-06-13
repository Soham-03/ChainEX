@file:OptIn(ExperimentalMaterial3Api::class)

package com.void_main.chainex.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.void_main.chainex.activity.VideoKycActivity
import kotlinx.coroutines.delay

// Theme colors
val lightBackground = Color(0xFFF8F9FA)
val accentGreen = Color(0xFF4CAF50)
val buttonBlue = Color(0xFF1A73E8)
val cardBg = Color(0xFFEAEEF1)
val textColor = Color(0xFF202124)
val secondaryTextColor = Color(0xFF5F6368)

data class DocumentRequirement(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val isRequired: Boolean = true
)

data class SetupRequirement(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val iconColor: Color
)

@Composable
fun KYCPreparationScreen(
    onStartVideoKYC: () -> Unit = {}
) {
    val context = LocalContext.current
    var isReady by remember { mutableStateOf(false) }
    var showAnimation by remember { mutableStateOf(false) }

    // Start animation after a short delay
    LaunchedEffect(Unit) {
        delay(300)
        showAnimation = true
    }

    val documentRequirements = listOf(
        DocumentRequirement(
            title = "Valid Passport",
            description = "Original passport with clear photos and readable text",
            icon = Icons.Default.CreditCard
        ),
        DocumentRequirement(
            title = "Valid Visa",
            description = "Current visa stamp or e-visa document for India",
            icon = Icons.Default.Assignment
        ),
        DocumentRequirement(
            title = "Proof of Address",
            description = "Hotel booking confirmation or local address proof",
            icon = Icons.Default.Home,
            isRequired = false
        )
    )

    val setupRequirements = listOf(
        SetupRequirement(
            title = "Good Lighting",
            description = "Sit in a well-lit area with light falling on your face",
            icon = Icons.Default.WbSunny,
            iconColor = Color(0xFFFF9800)
        ),
        SetupRequirement(
            title = "Stable Internet",
            description = "Ensure you have a stable internet connection",
            icon = Icons.Default.Wifi,
            iconColor = buttonBlue
        ),
        SetupRequirement(
            title = "Clear Camera",
            description = "Use a good quality camera and clean the lens",
            icon = Icons.Default.CameraAlt,
            iconColor = accentGreen
        ),
        SetupRequirement(
            title = "Quiet Environment",
            description = "Choose a quiet place for clear audio communication",
            icon = Icons.Default.VolumeUp,
            iconColor = Color(0xFF9C27B0)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBackground)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                AnimatedVisibility(
                    visible = showAnimation,
                    enter = slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(800)
                    ) + fadeIn(animationSpec = tween(800))
                ) {
                    HeaderSection()
                }
            }

            // Important Notice
            item {
                AnimatedVisibility(
                    visible = showAnimation,
                    enter = slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(800, delayMillis = 200)
                    ) + fadeIn(animationSpec = tween(800, delayMillis = 200))
                ) {
                    ImportantNoticeCard()
                }
            }

            // Document Requirements
            item {
                AnimatedVisibility(
                    visible = showAnimation,
                    enter = slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(800, delayMillis = 400)
                    ) + fadeIn(animationSpec = tween(800, delayMillis = 400))
                ) {
                    DocumentRequirementsSection(documentRequirements)
                }
            }

            // Setup Requirements
            item {
                AnimatedVisibility(
                    visible = showAnimation,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(800, delayMillis = 600)
                    ) + fadeIn(animationSpec = tween(800, delayMillis = 600))
                ) {
                    SetupRequirementsSection(setupRequirements)
                }
            }

            // Video KYC Info
            item {
                AnimatedVisibility(
                    visible = showAnimation,
                    enter = scaleIn(
                        animationSpec = tween(800, delayMillis = 800)
                    ) + fadeIn(animationSpec = tween(800, delayMillis = 800))
                ) {
                    VideoKYCInfoCard()
                }
            }

            // Ready Checkbox and Button
            item {
                AnimatedVisibility(
                    visible = showAnimation,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(800, delayMillis = 1000)
                    ) + fadeIn(animationSpec = tween(800, delayMillis = 1000))
                ) {
                    ReadySection(
                        isReady = isReady,
                        onReadyChanged = { isReady = it },
                        onStartVideoKYC = {
                            // Start VideoKYC Activity
                            try {
                                val intent = Intent(context, VideoKycActivity::class.java)
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                // Fallback to callback
                                onStartVideoKYC()
                            }
                        }
                    )
                }
            }

            // Add bottom padding
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun HeaderSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Animated logo/icon
        val infiniteTransition = rememberInfiniteTransition(label = "logo_animation")
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "rotation"
        )

        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(accentGreen.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Security,
                contentDescription = "Security",
                modifier = Modifier
                    .size(48.dp)
                    .graphicsLayer { rotationZ = rotation },
                tint = accentGreen
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Video KYC Preparation",
            style = MaterialTheme.typography.headlineMedium,
            color = textColor,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Get ready for your secure verification",
            style = MaterialTheme.typography.bodyLarge,
            color = secondaryTextColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@Composable
fun ImportantNoticeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEBEE)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = "Important",
                tint = Color(0xFFE53935),
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Important Notice",
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "This is a live video call with an RBI-certified agent. The session will be recorded for verification and compliance purposes.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = secondaryTextColor
                )
            }
        }
    }
}

@Composable
fun DocumentRequirementsSection(documents: List<DocumentRequirement>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = cardBg
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Description,
                    contentDescription = "Documents",
                    tint = accentGreen,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Required Documents",
                    style = MaterialTheme.typography.titleLarge,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            documents.forEach { document ->
                DocumentItem(document = document)
                if (document != documents.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun DocumentItem(document: DocumentRequirement) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    if (document.isRequired)
                        accentGreen.copy(alpha = 0.1f)
                    else
                        buttonBlue.copy(alpha = 0.1f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                document.icon,
                contentDescription = document.title,
                tint = if (document.isRequired) accentGreen else buttonBlue,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = document.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor,
                    fontWeight = FontWeight.Medium
                )
                if (!document.isRequired) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                buttonBlue.copy(alpha = 0.1f),
                                RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Optional",
                            fontSize = 10.sp,
                            color = buttonBlue,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            Text(
                text = document.description,
                style = MaterialTheme.typography.bodyMedium,
                color = secondaryTextColor
            )
        }

        if (document.isRequired) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = "Required",
                tint = accentGreen,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun SetupRequirementsSection(requirements: List<SetupRequirement>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = cardBg
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Setup",
                    tint = accentGreen,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Environment Setup",
                    style = MaterialTheme.typography.titleLarge,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Grid layout for setup requirements
            val chunkedRequirements = requirements.chunked(2)
            chunkedRequirements.forEach { rowRequirements ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowRequirements.forEach { requirement ->
                        SetupItem(
                            requirement = requirement,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Fill empty space if odd number of items
                    if (rowRequirements.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                if (rowRequirements != chunkedRequirements.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun SetupItem(
    requirement: SetupRequirement,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(requirement.iconColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    requirement.icon,
                    contentDescription = requirement.title,
                    tint = requirement.iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = requirement.title,
                style = MaterialTheme.typography.titleSmall,
                color = textColor,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = requirement.description,
                style = MaterialTheme.typography.bodySmall,
                color = secondaryTextColor,
                textAlign = TextAlign.Center,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun VideoKYCInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = accentGreen.copy(alpha = 0.05f)
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, accentGreen.copy(alpha = 0.2f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.VideoCall,
                    contentDescription = "Video KYC",
                    tint = accentGreen,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "About Video KYC",
                    style = MaterialTheme.typography.titleLarge,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            val kycFeatures = listOf(
                "Live video call with RBI-certified agent",
                "Session will be recorded for compliance",
                "Real-time document verification",
                "Face matching with passport photo",
                "Liveness detection for security",
                "Typically takes 5-10 minutes"
            )

            kycFeatures.forEach { feature ->
                Row(
                    modifier = Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Check",
                        tint = accentGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = feature,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor
                    )
                }
            }
        }
    }
}

@Composable
fun ReadySection(
    isReady: Boolean,
    onReadyChanged: (Boolean) -> Unit,
    onStartVideoKYC: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = cardBg
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onReadyChanged(!isReady) }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isReady,
                    onCheckedChange = onReadyChanged,
                    colors = CheckboxDefaults.colors(
                        checkedColor = accentGreen,
                        uncheckedColor = secondaryTextColor,
                        checkmarkColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "I have all required documents and I'm ready for Video KYC",
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onStartVideoKYC,
                enabled = isReady,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accentGreen,
                    disabledContainerColor = secondaryTextColor.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Icon(
                    Icons.Default.VideoCall,
                    contentDescription = "Start",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Start Video KYC",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            if (!isReady) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Please confirm you're ready to proceed",
                    style = MaterialTheme.typography.bodySmall,
                    color = secondaryTextColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}