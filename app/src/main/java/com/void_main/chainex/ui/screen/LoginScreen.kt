import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.void_main.chainex.activity.KYCActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChainExLoginScreen() {
    // State for email and password
    var email by remember { mutableStateOf("sohamgg@gmail.com") }
    var password by remember { mutableStateOf("soham123") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Define the app colors - light theme
    val lightBackground = Color(0xFFF8F9FA)
    val accentGreen = Color(0xFF4CAF50)
    val buttonBlue = Color(0xFF1A73E8)
    val cardBg = Color(0xFFEAEEF1)
    val textColor = Color(0xFF202124)
    val secondaryTextColor = Color(0xFF5F6368)
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBackground)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo and app name
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(accentGreen.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "₹",
                    fontSize = 48.sp,
                    color = accentGreen,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "ChainEx",
                fontSize = 32.sp,
                color = textColor,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Secure Blockchain Payments",
                fontSize = 16.sp,
                color = secondaryTextColor,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Email field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        tint = accentGreen
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = accentGreen,
                    focusedBorderColor = accentGreen,
                    unfocusedBorderColor = secondaryTextColor.copy(alpha = 0.5f),
                    focusedLabelColor = accentGreen,
                    unfocusedLabelColor = secondaryTextColor
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password",
                        tint = accentGreen
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = accentGreen
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = accentGreen,
                    focusedBorderColor = accentGreen,
                    unfocusedBorderColor = secondaryTextColor.copy(alpha = 0.5f),
                    focusedLabelColor = accentGreen,
                    unfocusedLabelColor = secondaryTextColor
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Forgot password text
            Text(
                text = "Forgot Password?",
                color = accentGreen,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Login button
            Button(
                onClick = {
                    val intent = Intent(context, KYCActivity::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBlue
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "LOG IN",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Alternative login methods
            Text(
                text = "or login with",
                color = secondaryTextColor,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Social login buttons row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // These would normally have icons for social login options
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(cardBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text("G", color = textColor, fontWeight = FontWeight.Bold)
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(cardBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text("f", color = textColor, fontWeight = FontWeight.Bold)
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(cardBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text("in", color = textColor, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Sign up text
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = secondaryTextColor,
                    fontSize = 14.sp
                )

                Text(
                    text = "Sign Up",
                    color = accentGreen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    ChainExLoginScreen()
}