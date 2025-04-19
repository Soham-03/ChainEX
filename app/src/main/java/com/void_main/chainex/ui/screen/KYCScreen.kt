import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.content.Intent
import com.void_main.chainex.activity.AddFundsActivity

@Composable
fun ForeignUserKycScreen() {
    // Define the app colors - light theme
    val lightBackground = Color(0xFFF8F9FA)
    val accentGreen = Color(0xFF4CAF50)
    val buttonBlue = Color(0xFF1A73E8)
    val cardBg = Color(0xFFFFFFFF)
    val borderGray = Color(0xFFE0E0E0)
    val textColor = Color(0xFF202124)
    val secondaryTextColor = Color(0xFF5F6368)

    // State variables
    var passportUploaded by remember { mutableStateOf(false) }
    var visaUploaded by remember { mutableStateOf(false) }
    var verificationStarted by remember { mutableStateOf(false) }
    var countdownSeconds by remember { mutableStateOf(60) } // 1 minute countdown

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // Background task for countdown
    LaunchedEffect(verificationStarted) {
        if (verificationStarted) {
            while (countdownSeconds > 0) {
                delay(1000) // wait for 1 second
                countdownSeconds--
            }

            // When countdown reaches 0, start the Add Funds activity
            if (countdownSeconds == 0) {
                // Start the Add Funds activity
                val intent = Intent(context, AddFundsActivity::class.java)
                context.startActivity(intent)

                // Reset verification state
                verificationStarted = false
                passportUploaded = false
                visaUploaded = false
                countdownSeconds = 60
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBackground)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "eKYC Verification",
                fontSize = 24.sp,
                color = textColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = "For International Users",
                fontSize = 16.sp,
                color = accentGreen,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (!verificationStarted) {
                // Document upload section
                DocumentUploadSection(
                    title = "Passport Copy",
                    description = "Upload a clear copy of your passport's information page",
                    isUploaded = passportUploaded,
                    onUpload = { passportUploaded = true },
                    accentGreen = accentGreen,
                    cardBg = cardBg,
                    borderGray = borderGray,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                DocumentUploadSection(
                    title = "Visa Copy",
                    description = "Upload a copy of your valid Indian visa",
                    isUploaded = visaUploaded,
                    onUpload = { visaUploaded = true },
                    accentGreen = accentGreen,
                    cardBg = cardBg,
                    borderGray = borderGray,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Verify button
                Button(
                    onClick = {
                        if (passportUploaded && visaUploaded) {
                            verificationStarted = true
                        }
                    },
                    enabled = passportUploaded && visaUploaded,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonBlue,
                        disabledContainerColor = buttonBlue.copy(alpha = 0.3f)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "VERIFY DOCUMENTS",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            } else {
                // Verification in progress section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    VerificationProgressSection(
                        secondsRemaining = countdownSeconds,
                        accentGreen = accentGreen,
                        buttonBlue = buttonBlue,
                        cardBg = cardBg,
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor,
                        borderGray = borderGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Information text
            Text(
                text = "Your data is secure and encrypted. We comply with all data protection regulations.",
                color = secondaryTextColor,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun DocumentUploadSection(
    title: String,
    description: String,
    isUploaded: Boolean,
    onUpload: () -> Unit,
    accentGreen: Color,
    cardBg: Color,
    borderGray: Color,
    textColor: Color,
    secondaryTextColor: Color
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = cardBg
        ),
        border = BorderStroke(1.dp, borderGray),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isUploaded) Icons.Default.CheckCircle else Icons.Default.InsertDriveFile,
                    contentDescription = title,
                    tint = if (isUploaded) accentGreen else secondaryTextColor,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = title,
                    fontSize = 18.sp,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                if (isUploaded) {
                    Text(
                        text = "Uploaded",
                        color = accentGreen,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                color = secondaryTextColor,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (!isUploaded) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, borderGray, RoundedCornerShape(8.dp))
                        .background(Color(0xFFF5F5F5)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudUpload,
                            contentDescription = "Upload",
                            tint = secondaryTextColor,
                            modifier = Modifier.size(36.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Tap to upload",
                            color = secondaryTextColor,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onUpload,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentGreen
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FileUpload,
                        contentDescription = "Upload",
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "SELECT FILE",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            } else {
                // Document preview placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(accentGreen.copy(alpha = 0.1f))
                        .border(1.dp, accentGreen.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.InsertDriveFile,
                            contentDescription = "Document",
                            tint = accentGreen,
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "Document verified",
                            color = accentGreen,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VerificationProgressSection(
    secondsRemaining: Int,
    accentGreen: Color,
    buttonBlue: Color,
    cardBg: Color,
    textColor: Color,
    secondaryTextColor: Color,
    borderGray: Color
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulseAnimation by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Pulsing circle
            Box(
                modifier = Modifier
                    .size(100.dp * pulseAnimation)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(buttonBlue.copy(alpha = 0.2f * pulseAnimation))
            )

            // Inner circle
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(buttonBlue.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$secondsRemaining",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Verification in Progress",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Estimated time: ${formatTime(secondsRemaining)}",
            fontSize = 16.sp,
            color = secondaryTextColor
        )

        Spacer(modifier = Modifier.height(24.dp))

        LinearProgressIndicator(
            progress = { 1f - (secondsRemaining / 60f) },
            color = buttonBlue,
            trackColor = buttonBlue.copy(alpha = 0.2f),
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = cardBg
            ),
            border = BorderStroke(1.dp, borderGray),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Verification Steps",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                VerificationStep(
                    step = "Passport Authentication",
                    isCompleted = true,
                    accentColor = accentGreen,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                VerificationStep(
                    step = "Visa Validation",
                    isCompleted = true,
                    accentColor = accentGreen,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                VerificationStep(
                    step = "Biometric Processing",
                    isCompleted = secondsRemaining <= 40,
                    accentColor = accentGreen,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                VerificationStep(
                    step = "Document Cross-verification",
                    isCompleted = secondsRemaining <= 20,
                    accentColor = accentGreen,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                VerificationStep(
                    step = "Final Approval",
                    isCompleted = secondsRemaining <= 5,
                    accentColor = accentGreen,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor
                )
            }
        }
    }
}

@Composable
fun VerificationStep(
    step: String,
    isCompleted: Boolean,
    accentColor: Color,
    textColor: Color,
    secondaryTextColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = if (isCompleted) "Completed" else "Pending",
            tint = if (isCompleted) accentColor else secondaryTextColor.copy(alpha = 0.5f),
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = step,
            fontSize = 14.sp,
            color = if (isCompleted) textColor else secondaryTextColor
        )
    }
}

// Helper function to format time
fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "$minutes:${remainingSeconds.toString().padStart(2, '0')}"
}

@Preview(showBackground = true)
@Composable
fun KycScreenPreview() {
    ForeignUserKycScreen()
}