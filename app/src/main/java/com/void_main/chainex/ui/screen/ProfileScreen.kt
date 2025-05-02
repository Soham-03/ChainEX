package com.void_main.chainex.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit = {}
) {
    // Define colors
    val lightBackground = Color(0xFFF8F9FA)
    val accentGreen = Color(0xFF4CAF50)
    val buttonBlue = Color(0xFF1A73E8)
    val cardBg = Color(0xFFFFFFFF)
    val textColor = Color(0xFF202124)
    val secondaryTextColor = Color(0xFF5F6368)
    val borderGray = Color(0xFFE0E0E0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightBackground)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Picture
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(accentGreen.copy(alpha = 0.3f))
                        .border(2.dp, accentGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "SP",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentGreen
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Name
                Text(
                    text = "Soham Parab",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                // Email
                Text(
                    text = "SohamParab69@gmail.com",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Document Status Section
            Card(
                colors = CardDefaults.cardColors(containerColor = cardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Document Status",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // KYC Status
                    DocumentStatusItem(
                        title = "KYC Verification",
                        status = DocumentStatus.COMPLETED,
                        icon = Icons.Default.VerifiedUser,
                        accentColor = accentGreen,
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )

                    Divider(
                        color = borderGray,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    // Visa Status
                    DocumentStatusItem(
                        title = "Visa",
                        status = DocumentStatus.COMPLETED,
                        icon = Icons.Default.Assignment,
                        accentColor = accentGreen,
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )

                    Divider(
                        color = borderGray,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    // Passport Status
                    DocumentStatusItem(
                        title = "Passport",
                        status = DocumentStatus.COMPLETED,
                        icon = Icons.Default.CardTravel,
                        accentColor = accentGreen,
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )
                }
            }

            // Account Settings
            Card(
                colors = CardDefaults.cardColors(containerColor = cardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Account Settings",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProfileMenuItem(
                        title = "Change Password",
                        icon = Icons.Default.Lock,
                        onClick = { /* Handle password change */ },
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )

                    ProfileMenuItem(
                        title = "Two-Factor Authentication",
                        icon = Icons.Default.Security,
                        onClick = { /* Handle 2FA */ },
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )

                    ProfileMenuItem(
                        title = "Notification Settings",
                        icon = Icons.Default.Notifications,
                        onClick = { /* Handle notifications */ },
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )

                    ProfileMenuItem(
                        title = "Privacy Settings",
                        icon = Icons.Default.Preview,
                        onClick = { /* Handle privacy */ },
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )
                }
            }

            // Support & About
            Card(
                colors = CardDefaults.cardColors(containerColor = cardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Support & About",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProfileMenuItem(
                        title = "Help Center",
                        icon = Icons.Default.Help,
                        onClick = { /* Handle help */ },
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )

                    ProfileMenuItem(
                        title = "Terms of Service",
                        icon = Icons.Default.Article,
                        onClick = { /* Handle terms */ },
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )

                    ProfileMenuItem(
                        title = "Privacy Policy",
                        icon = Icons.Default.Policy,
                        onClick = { /* Handle privacy policy */ },
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )

                    ProfileMenuItem(
                        title = "About ChainEx",
                        icon = Icons.Default.Info,
                        onClick = { /* Handle about */ },
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )
                }
            }

            // Logout Button
            Button(
                onClick = { /* Handle logout */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Logout",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

enum class DocumentStatus {
    COMPLETED, PENDING, FAILED
}

@Composable
fun DocumentStatusItem(
    title: String,
    status: DocumentStatus,
    icon: ImageVector,
    accentColor: Color,
    textColor: Color,
    secondaryTextColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (status == DocumentStatus.COMPLETED) accentColor else secondaryTextColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )

            Text(
                text = when (status) {
                    DocumentStatus.COMPLETED -> "Verified"
                    DocumentStatus.PENDING -> "Pending Verification"
                    DocumentStatus.FAILED -> "Verification Failed"
                },
                fontSize = 14.sp,
                color = when (status) {
                    DocumentStatus.COMPLETED -> accentColor
                    DocumentStatus.PENDING -> Color(0xFFF57F17)
                    DocumentStatus.FAILED -> Color(0xFFE53935)
                }
            )
        }

        // Status Icon
        when (status) {
            DocumentStatus.COMPLETED -> {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Completed",
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            DocumentStatus.PENDING -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color(0xFFF57F17),
                    strokeWidth = 2.dp
                )
            }
            DocumentStatus.FAILED -> {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Failed",
                    tint = Color(0xFFE53935),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileMenuItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    textColor: Color,
    secondaryTextColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = secondaryTextColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            color = textColor,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = secondaryTextColor,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}