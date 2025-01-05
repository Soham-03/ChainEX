package com.void_main.chainex

data class Transaction(
    val amount: Int,
    val receiver: String,
    val txHash: String,
    val status: TransactionStatus,
    val timestamp: Long = System.currentTimeMillis()
)
