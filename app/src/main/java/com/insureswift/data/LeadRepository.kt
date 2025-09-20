package com.insureswift.data

import com.insureswift.data.db.LeadDao
import com.insureswift.data.model.Lead
import kotlinx.coroutines.flow.Flow

class LeadRepository(private val leadDao: LeadDao) {

    val allLeads: Flow<List<Lead>> = leadDao.getAllLeads()

    fun getLeadById(id: Long): Flow<Lead> {
        return leadDao.getLeadById(id)
    }

    suspend fun insert(lead: Lead) {
        leadDao.insertLead(lead)
    }

    suspend fun update(lead: Lead) {
        leadDao.updateLead(lead)
    }

    suspend fun delete(lead: Lead) {
        leadDao.deleteLead(lead)
    }
}
