package com.void_main.chainex

import com.google.type.LatLng

object Utils {
    val transactions: ArrayList<Transaction> = arrayListOf(
        Transaction(1000, "Rahul Sharma", "0x1234...5678", TransactionStatus.SUCCESS),
        Transaction(500, "Priya Patel", "0x8765...4321", TransactionStatus.PENDING),
        Transaction(750, "Amit Kumar", "0x9876...5432", TransactionStatus.FAILED),
        Transaction(2000, "Deepika Singh", "0x3456...7890", TransactionStatus.SUCCESS),
        Transaction(1500, "Rajesh Verma", "0x6543...2109", TransactionStatus.SUCCESS),
        Transaction(300, "Anjali Gupta", "0x2345...6789", TransactionStatus.PENDING),
        Transaction(900, "Vikas Malhotra", "0x7654...3210", TransactionStatus.SUCCESS),
        Transaction(1200, "Neha Reddy", "0x4567...8901", TransactionStatus.FAILED),
        Transaction(600, "Suresh Iyer", "0x8901...2345", TransactionStatus.SUCCESS),
        Transaction(800, "Meera Krishnan", "0x5678...9012", TransactionStatus.SUCCESS)
    )
    var clickedLatLng: com.google.android.gms.maps.model.LatLng? = null
    var amountDeposited = 15801
}