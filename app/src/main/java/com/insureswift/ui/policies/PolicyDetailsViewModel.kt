package com.insureswift.ui.policies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.insureswift.data.ClientRepository
import com.insureswift.data.PolicyRepository
import com.insureswift.data.db.AppDatabase
import androidx.lifecycle.viewModelScope
import com.insureswift.data.model.Client
import com.insureswift.data.model.Policy
import kotlinx.coroutines.launch

class PolicyDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val policyRepository: PolicyRepository
    private val clientRepository: ClientRepository

    init {
        val database = AppDatabase.getDatabase(application)
        policyRepository = PolicyRepository(database.policyDao())
        clientRepository = ClientRepository(database.clientDao())
    }

    fun getPolicy(policyId: Long): LiveData<Policy> {
        return policyRepository.getPolicyById(policyId).asLiveData()
    }

    fun getClient(clientId: Long): LiveData<Client> {
        return clientRepository.getClientById(clientId).asLiveData()
    }

    fun update(policy: Policy) = viewModelScope.launch {
        policyRepository.update(policy)
    }
}
