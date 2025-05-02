package com.void_main.chainex.activity

import com.void_main.chainex.MainActivity



import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.void_main.chainex.ui.screen.AddFundsScreen

class AddFundsActivity : ComponentActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Checkout.preload(applicationContext)

        setContent {
            AddFundsScreen()
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        // Handle successful payment
        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show()

        // Navigate back to MainActivity
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun onPaymentError(code: Int, response: String?) {
        // Handle payment error
        Toast.makeText(this, "Payment Failed: $response", Toast.LENGTH_LONG).show()

        // You might want to parse the error and show appropriate message
        when (code) {
            Checkout.NETWORK_ERROR -> {
                Toast.makeText(this, "Network error. Please check your connection.", Toast.LENGTH_LONG).show()
            }
            Checkout.INVALID_OPTIONS -> {
                Toast.makeText(this, "Invalid payment options.", Toast.LENGTH_LONG).show()
            }
            Checkout.PAYMENT_CANCELED -> {
                Toast.makeText(this, "Payment cancelled by user.", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, "Payment failed. Please try again.", Toast.LENGTH_LONG).show()
            }
        }
    }
}