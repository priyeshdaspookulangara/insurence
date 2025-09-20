package com.insureswift.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "policies",
    foreignKeys = [ForeignKey(
        entity = Client::class,
        parentColumns = ["clientId"],
        childColumns = ["clientId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Policy(
    @PrimaryKey(autoGenerate = true)
    val policyId: Long = 0L,
    val policyNumber: String,
    val policyType: String,
    val carrier: String,
    val coverageAmount: Double,
    val effectiveDate: Long,
    val expirationDate: Long,
    val premiumAmount: Double,
    val paymentFrequency: String,
    val status: String,
    val clientId: Long
)
