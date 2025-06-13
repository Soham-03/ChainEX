@file:OptIn(ExperimentalMaterial3Api::class)

package com.void_main.chainex.screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.void_main.chainex.activity.KYCActivity
import kotlinx.coroutines.delay

// Theme colors
val lightBackground = Color(0xFFF8F9FA)
val accentGreen = Color(0xFF4CAF50)
val buttonBlue = Color(0xFF1A73E8)
val cardBg = Color(0xFFEAEEF1)
val textColor = Color(0xFF202124)
val secondaryTextColor = Color(0xFF5F6368)

enum class KYCStep {
    WAITING_FOR_AGENT,
    AGENT_CONNECTED,
    DOCUMENT_VERIFICATION,
    FACE_VERIFICATION,
    LIVELINESS_CHECK,
    VERIFICATION_COMPLETE,
    VERIFICATION_FAILED
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun VideoKYCScreen() {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    var currentStep by remember { mutableStateOf(KYCStep.WAITING_FOR_AGENT) }
    var isRecording by remember { mutableStateOf(false) }
    var connectionTime by remember { mutableStateOf(0) }
    var agentName by remember { mutableStateOf("") }

    // Simulate the KYC flow
    LaunchedEffect(Unit) {
        delay(3000)
        agentName = "Priya Sharma"
        currentStep = KYCStep.AGENT_CONNECTED
        isRecording = true

        delay(5000)
        currentStep = KYCStep.DOCUMENT_VERIFICATION

        delay(12000) // Extended time for both passport and visa (6 seconds each)
        currentStep = KYCStep.FACE_VERIFICATION
        delay(6000)
        currentStep = KYCStep.LIVELINESS_CHECK

        delay(18000) // Extended time for liveness check (6 instructions * 3 seconds each)
        currentStep = KYCStep.VERIFICATION_COMPLETE
        isRecording = false
    }

    val context = LocalContext.current

    // Timer for connection
    LaunchedEffect(currentStep) {
        if (currentStep != KYCStep.WAITING_FOR_AGENT) {
            while (currentStep != KYCStep.VERIFICATION_COMPLETE && currentStep != KYCStep.VERIFICATION_FAILED) {
                delay(1000)
                connectionTime++
            }
        }
    }

    LaunchedEffect(currentStep) {
        if (currentStep == KYCStep.VERIFICATION_COMPLETE) {
            delay(3000) // Show success message for 3 seconds
            try {
                val intent = Intent(context, KYCActivity::class.java)
                context.startActivity(intent)
            } catch (e: Exception) {
                Log.e("VideoKYC", "Failed to start KYCActivity", e)
            }
        }
    }

    // Request camera permission when the screen is shown
    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    when {
        cameraPermissionState.status.isGranted -> {
            // Camera permission granted, show the KYC screen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(lightBackground)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Header
                    VideoKYCHeader(
                        connectionTime = connectionTime,
                        isRecording = isRecording,
                        agentName = agentName
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Video Call with Live Camera
                    VideoCallWithLiveCamera(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(screenHeight * 0.4f),
                        currentStep = currentStep,
                        agentName = agentName
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // KYC Steps Progress
                    KYCStepsProgress(
                        currentStep = currentStep,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Instructions and Actions
                    KYCInstructions(
                        currentStep = currentStep,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Emergency Exit Button
                FloatingActionButton(
                    onClick = { /* Handle emergency exit */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = Color(0xFFE53935)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Emergency Exit",
                        tint = Color.White
                    )
                }

                // Camera controls overlay
                CameraControlsOverlay(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    isRecording = isRecording
                )
            }
        }
        cameraPermissionState.status.shouldShowRationale -> {
            // Show rationale for camera permission
            CameraPermissionRationale(
                onRequestPermission = { cameraPermissionState.launchPermissionRequest() }
            )
        }
        else -> {
            // Show permission request screen
            CameraPermissionRequest(
                onRequestPermission = { cameraPermissionState.launchPermissionRequest() }
            )
        }
    }
}

@Composable
fun VideoKYCHeader(
    connectionTime: Int,
    isRecording: Boolean,
    agentName: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = cardBg
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Zap UPI Video KYC",
                    style = MaterialTheme.typography.headlineSmall,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
                if (agentName.isNotEmpty()) {
                    Text(
                        text = "Agent: $agentName",
                        style = MaterialTheme.typography.bodyMedium,
                        color = secondaryTextColor
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isRecording) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE53935))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = String.format("%02d:%02d", connectionTime / 60, connectionTime % 60),
                        style = MaterialTheme.typography.bodyLarge,
                        color = textColor,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = if (isRecording) "RECORDING" else "STANDBY",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isRecording) Color(0xFFE53935) else secondaryTextColor
                )
            }
        }
    }
}

@Composable
fun VideoCallWithLiveCamera(
    modifier: Modifier = Modifier,
    currentStep: KYCStep,
    agentName: String
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var previewView: PreviewView? by remember { mutableStateOf(null) }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Main Camera View (User's camera)
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        previewView = this
                        startCamera(ctx, lifecycleOwner, this)
                    }
                }
            )

            // Agent Video (Small window - top right)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .size(120.dp, 90.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(accentGreen.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Agent",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                    if (agentName.isNotEmpty()) {
                        Text(
                            text = agentName,
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            fontSize = 9.sp
                        )
                        Text(
                            text = "RBI Agent",
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 8.sp
                        )
                    }
                }
            }

            // Connection Status Overlay
            if (currentStep == KYCStep.WAITING_FOR_AGENT) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.9f)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(24.dp)
                        ) {
                            CircularProgressIndicator(
                                color = accentGreen,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Connecting to Agent...",
                                color = textColor,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Please position yourself in front of the camera",
                                color = secondaryTextColor,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Step-specific overlays
            when (currentStep) {
                KYCStep.DOCUMENT_VERIFICATION -> {
                    DocumentGuideOverlay(modifier = Modifier.align(Alignment.Center))
                }
                KYCStep.FACE_VERIFICATION -> {
                    FaceGuideOverlay(modifier = Modifier.align(Alignment.Center))
                }
                KYCStep.LIVELINESS_CHECK -> {
                    LivenessGuideOverlay(modifier = Modifier.align(Alignment.Center))
                }
                else -> {}
            }
        }
    }
}

@Composable
fun CameraControlsOverlay(
    modifier: Modifier = Modifier,
    isRecording: Boolean
) {
    Card(
        modifier = modifier.padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.7f)
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Microphone toggle
            IconButton(
                onClick = { /* Toggle microphone */ }
            ) {
                Icon(
                    Icons.Default.Mic,
                    contentDescription = "Microphone",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Camera switch
            IconButton(
                onClick = { /* Switch camera */ }
            ) {
                Icon(
                    Icons.Default.Cameraswitch,
                    contentDescription = "Switch Camera",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Recording indicator
            if (isRecording) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE53935))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "REC",
                        color = Color(0xFFE53935),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun DocumentGuideOverlay(modifier: Modifier = Modifier) {
    var currentDocument by remember { mutableStateOf(0) }
    val documents = listOf("passport", "visa")

    // Auto-advance between passport and visa
    LaunchedEffect(currentDocument) {
        if (currentDocument == 0) {
            delay(6000) // Show passport guide for 6 seconds
            currentDocument = 1
        }
    }

    Box(
        modifier = modifier
            .size(280.dp, 180.dp)
            .background(
                Color.Transparent,
                RoundedCornerShape(12.dp)
            )
            .border(
                3.dp,
                Color(0xFFFF9800),
                RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (currentDocument == 0) "Place passport here" else "Now place visa here",
                color = Color(0xFFFF9800),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(
                        Color.Black.copy(alpha = 0.8f),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Progress indicator
            LinearProgressIndicator(
                progress = (currentDocument + 1).toFloat() / 2,
                modifier = Modifier
                    .width(120.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = Color(0xFFFF9800),
                trackColor = Color.Gray.copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${currentDocument + 1} of 2",
                color = Color(0xFFFF9800),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .background(
                        Color.Black.copy(alpha = 0.6f),
                        RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
fun FaceGuideOverlay(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(200.dp)
            .background(
                Color.Transparent,
                CircleShape
            )
            .border(
                4.dp,
                buttonBlue,
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Position your face here",
            color = buttonBlue,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(
                    Color.Black.copy(alpha = 0.8f),
                    RoundedCornerShape(8.dp)
                )
                .padding(12.dp)
        )
    }
}

@Composable
fun LivenessGuideOverlay(modifier: Modifier = Modifier) {
    var currentInstruction by remember { mutableStateOf(0) }
    var showInstruction by remember { mutableStateOf(true) }

    val livenessInstructions = listOf(
        "Look straight at the camera" to Icons.Default.CenterFocusStrong,
        "Turn your head to the left" to Icons.Default.TurnLeft,
        "Turn your head to the right" to Icons.Default.TurnRight,
        "Blink your eyes slowly" to Icons.Default.RemoveRedEye,
        "Smile for the camera" to Icons.Default.SentimentSatisfied,
        "Nod your head up and down" to Icons.Default.KeyboardArrowUp
    )

    // Auto-advance through instructions
    LaunchedEffect(currentInstruction) {
        delay(3000) // Show each instruction for 3 seconds
        if (currentInstruction < livenessInstructions.size - 1) {
            currentInstruction++
        } else {
            showInstruction = false
        }
    }

    if (showInstruction && currentInstruction < livenessInstructions.size) {
        val (instruction, icon) = livenessInstructions[currentInstruction]

        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = Color.Black.copy(alpha = 0.9f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Progress indicator
                LinearProgressIndicator(
                    progress = (currentInstruction + 1).toFloat() / livenessInstructions.size,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = accentGreen,
                    trackColor = Color.Gray.copy(alpha = 0.3f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Icon with animation
                val infiniteTransition = rememberInfiniteTransition(label = "icon_animation")
                val scale by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 1.2f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "scale_animation"
                )

                Icon(
                    icon,
                    contentDescription = instruction,
                    tint = accentGreen,
                    modifier = Modifier
                        .size(64.dp)
                        .graphicsLayer(scaleX = scale, scaleY = scale)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = instruction,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Step ${currentInstruction + 1} of ${livenessInstructions.size}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Countdown timer
                var countdown by remember { mutableStateOf(3) }
                LaunchedEffect(currentInstruction) {
                    countdown = 3
                    while (countdown > 0) {
                        delay(1000)
                        countdown--
                    }
                }

                if (countdown > 0) {
                    Text(
                        text = "$countdown",
                        color = accentGreen,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun CameraPermissionRequest(onRequestPermission: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBackground),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Camera,
                    contentDescription = "Camera",
                    modifier = Modifier.size(64.dp),
                    tint = accentGreen
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Camera Permission Required",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Zap UPI needs camera access for video KYC verification as per RBI guidelines.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = secondaryTextColor
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onRequestPermission,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentGreen
                    )
                ) {
                    Text("Grant Camera Permission", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun CameraPermissionRationale(onRequestPermission: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBackground),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Security,
                    contentDescription = "Security",
                    modifier = Modifier.size(64.dp),
                    tint = accentGreen
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Camera Access for KYC",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Camera access is mandatory for RBI-compliant video KYC verification. This ensures secure onboarding and compliance with Indian banking regulations.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = secondaryTextColor
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onRequestPermission,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentGreen
                    )
                ) {
                    Text("Allow Camera Access", color = Color.White)
                }
            }
        }
    }
}

// Camera setup function
private fun startCamera(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        try {
            val cameraProvider = cameraProviderFuture.get()

            // Preview use case
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            // Select front camera as default for KYC
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview
                )

            } catch (exc: Exception) {
                Log.e("VideoKYC", "Use case binding failed", exc)
            }

        } catch (exc: Exception) {
            Log.e("VideoKYC", "Camera initialization failed", exc)
        }
    }, ContextCompat.getMainExecutor(context))
}

@Composable
fun KYCStepsProgress(
    currentStep: KYCStep,
    modifier: Modifier = Modifier
) {
    val steps = listOf(
        "Agent Connect" to KYCStep.AGENT_CONNECTED,
        "Document Check" to KYCStep.DOCUMENT_VERIFICATION,
        "Face Match" to KYCStep.FACE_VERIFICATION,
        "Liveness Test" to KYCStep.LIVELINESS_CHECK,
        "Complete" to KYCStep.VERIFICATION_COMPLETE
    )

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = cardBg
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Verification Progress",
                style = MaterialTheme.typography.titleMedium,
                color = textColor,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                steps.forEachIndexed { index, (stepName, stepEnum) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        val isCompleted = when (currentStep) {
                            KYCStep.WAITING_FOR_AGENT -> false
                            KYCStep.AGENT_CONNECTED -> stepEnum == KYCStep.AGENT_CONNECTED
                            KYCStep.DOCUMENT_VERIFICATION -> stepEnum == KYCStep.AGENT_CONNECTED || stepEnum == KYCStep.DOCUMENT_VERIFICATION
                            KYCStep.FACE_VERIFICATION -> stepEnum in listOf(KYCStep.AGENT_CONNECTED, KYCStep.DOCUMENT_VERIFICATION, KYCStep.FACE_VERIFICATION)
                            KYCStep.LIVELINESS_CHECK -> stepEnum in listOf(KYCStep.AGENT_CONNECTED, KYCStep.DOCUMENT_VERIFICATION, KYCStep.FACE_VERIFICATION, KYCStep.LIVELINESS_CHECK)
                            KYCStep.VERIFICATION_COMPLETE -> true
                            KYCStep.VERIFICATION_FAILED -> false
                        }

                        val isCurrent = stepEnum == currentStep

                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        isCompleted -> accentGreen
                                        isCurrent -> buttonBlue
                                        else -> secondaryTextColor.copy(alpha = 0.3f)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isCompleted) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = "Completed",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            } else if (isCurrent) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    text = "${index + 1}",
                                    color = Color.White.copy(alpha = 0.7f),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = stepName,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isCompleted || isCurrent) textColor else secondaryTextColor,
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun KYCInstructions(
    currentStep: KYCStep,
    modifier: Modifier = Modifier
) {
    val (instruction, icon) = when (currentStep) {
        KYCStep.WAITING_FOR_AGENT -> "Please wait while we connect you to our RBI-certified verification agent." to Icons.Default.Person
        KYCStep.AGENT_CONNECTED -> "Hello! I'm your verification agent. Please have your passport and visa ready for verification." to Icons.Default.VideoCall
        KYCStep.DOCUMENT_VERIFICATION -> "Please show your passport clearly to the camera first, then your visa document. Hold each document steady for a few seconds." to Icons.Default.CreditCard
        KYCStep.FACE_VERIFICATION -> "Now please look directly at the camera. We'll match your face with your passport photo." to Icons.Default.Face
        KYCStep.LIVELINESS_CHECK -> "Please follow the on-screen instructions for liveness verification as per RBI guidelines." to Icons.Default.Visibility
        KYCStep.VERIFICATION_COMPLETE -> "✅ Verification successful! Your KYC is complete. Redirecting to document upload..." to Icons.Default.CheckCircle
        KYCStep.VERIFICATION_FAILED -> "Verification failed. Please try again or contact support." to Icons.Default.Error
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = when (currentStep) {
                KYCStep.VERIFICATION_COMPLETE -> accentGreen.copy(alpha = 0.1f)
                KYCStep.VERIFICATION_FAILED -> Color(0xFFE53935).copy(alpha = 0.1f)
                else -> cardBg
            }
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
                icon,
                contentDescription = null,
                tint = when (currentStep) {
                    KYCStep.VERIFICATION_COMPLETE -> accentGreen
                    KYCStep.VERIFICATION_FAILED -> Color(0xFFE53935)
                    else -> accentGreen
                },
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = instruction,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
                modifier = Modifier.weight(1f)
            )
        }
    }
}