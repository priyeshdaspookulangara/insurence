package com.insureswift.data

import com.insureswift.data.db.PolicyDao
import com.insureswift.data.model.Policy
import kotlinx.coroutines.flow.Flow

class PolicyRepository(private val policyDao: PolicyDao) {

    val allPolicies: Flow<List<Policy>> = policyDao.getAllPolicies()

    fun getPolicyById(id: Long): Flow<Policy> {
        return policyDao.getPolicyById(id)
    }

    fun getPoliciesForClient(clientId: Long): Flow<List<Policy>> {
        return policyDao.getPoliciesForClient(clientId)
    }

    suspend fun insert(policy: Policy) {
        policyDao.insertPolicy(policy)
    }

    suspend fun update(policy: Policy) {
        policyDao.updatePolicy(policy)
    }

    suspend fun delete(policy: Policy) {
        policyDao.deletePolicy(policy)
    }
}
