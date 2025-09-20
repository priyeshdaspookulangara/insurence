package com.insureswift.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class Client(
    @PrimaryKey(autoGenerate = true)
    val clientId: Long = 0L,
    val name: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val profilePictureUri: String? = null
)
