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

class LeadsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LeadRepository
    val allLeads: LiveData<List<Lead>>

    init {
        val leadDao = AppDatabase.getDatabase(application).leadDao()
        repository = LeadRepository(leadDao)
        allLeads = repository.allLeads.asLiveData()
    }

    fun insert(lead: Lead) = viewModelScope.launch {
        repository.insert(lead)
    }

    fun update(lead: Lead) = viewModelScope.launch {
        repository.update(lead)
    }
}
