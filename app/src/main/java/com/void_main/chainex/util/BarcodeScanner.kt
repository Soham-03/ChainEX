package com.void_main.chainex.util

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.void_main.chainex.activity.PaymentActivity
import kotlinx.coroutines.flow.MutableStateFlow

class BarcodeScanner(val context: Context) {
    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE,
            Barcode.FORMAT_AZTEC)
        .enableAutoZoom()
        .build()
    private val scanner = GmsBarcodeScanning.getClient(context, options)
    val barCodeRes = MutableStateFlow<String?>(null)
    suspend fun startScan(){
        try{
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    val rawValue = barcode.rawValue
                    System.out.println("GG:"+rawValue)
                    barCodeRes.value = barcode.rawValue
                    val intent = Intent( context, PaymentActivity::class.java)
                    context.startActivity(intent)
                }
                .addOnCanceledListener {
                    Toast.makeText(context, "Cancelled Scan", Toast.LENGTH_SHORT).show()
                    barCodeRes.value = "Cancelled"
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Invalid QR", Toast.LENGTH_SHORT).show()
                    barCodeRes.value = e.message
                }
        }catch(_: Exception){

        }

    }

}