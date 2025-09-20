package com.insureswift.ui.leads

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.insureswift.data.LeadRepository
import com.insureswift.data.db.AppDatabase
import com.insureswift.data.model.Lead
import kotlinx.coroutines.launch

class LeadDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LeadRepository

    init {
        val leadDao = AppDatabase.getDatabase(application).leadDao()
        repository = LeadRepository(leadDao)
    }

    fun getLead(leadId: Long): LiveData<Lead> {
        return repository.getLeadById(leadId).asLiveData()
    }

    fun updateLead(lead: Lead) = viewModelScope.launch {
        repository.update(lead)
    }
}
