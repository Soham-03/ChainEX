package com.void_main.chainex.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.void_main.chainex.ui.screen.PaymentScreen

class PaymentActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Checkout.preload(applicationContext)

        setContent {
            PaymentScreen()
        }
    }
}