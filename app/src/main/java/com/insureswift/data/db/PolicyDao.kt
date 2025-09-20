package com.insureswift.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.insureswift.data.model.Policy
import kotlinx.coroutines.flow.Flow

@Dao
interface PolicyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPolicy(policy: Policy)

    @Update
    suspend fun updatePolicy(policy: Policy)

    @Delete
    suspend fun deletePolicy(policy: Policy)

    @Query("SELECT * FROM policies ORDER BY expirationDate ASC")
    fun getAllPolicies(): Flow<List<Policy>>

    @Query("SELECT * FROM policies WHERE policyId = :id")
    fun getPolicyById(id: Long): Flow<Policy>

    @Query("SELECT * FROM policies WHERE clientId = :clientId ORDER BY expirationDate ASC")
    fun getPoliciesForClient(clientId: Long): Flow<List<Policy>>
}
