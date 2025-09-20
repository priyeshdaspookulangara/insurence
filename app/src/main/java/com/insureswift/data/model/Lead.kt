package com.insureswift.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leads")
data class Lead(
    @PrimaryKey(autoGenerate = true)
    val leadId: Long = 0L,
    val name: String,
    val contactInfo: String,
    val status: String, // e.g., New, Contacted, Qualified, Converted
    val notes: String
)
