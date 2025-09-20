package com.insureswift.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.insureswift.data.model.Lead
import kotlinx.coroutines.flow.Flow

@Dao
interface LeadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLead(lead: Lead)

    @Update
    suspend fun updateLead(lead: Lead)

    @Delete
    suspend fun deleteLead(lead: Lead)

    @Query("SELECT * FROM leads ORDER BY name ASC")
    fun getAllLeads(): Flow<List<Lead>>

    @Query("SELECT * FROM leads WHERE leadId = :id")
    fun getLeadById(id: Long): Flow<Lead>
}
