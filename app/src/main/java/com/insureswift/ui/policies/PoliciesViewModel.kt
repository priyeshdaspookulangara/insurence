package com.insureswift.ui.policies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.insureswift.data.PolicyRepository
import com.insureswift.data.db.AppDatabase
import com.insureswift.data.model.Policy
import kotlinx.coroutines.launch

class PoliciesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PolicyRepository
    val allPolicies: LiveData<List<Policy>>

    init {
        val policyDao = AppDatabase.getDatabase(application).policyDao()
        repository = PolicyRepository(policyDao)
        allPolicies = repository.allPolicies.asLiveData()
    }

    fun insert(policy: Policy) = viewModelScope.launch {
        repository.insert(policy)
    }
}
