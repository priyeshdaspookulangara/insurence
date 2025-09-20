package com.insureswift.ui.clients

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.insureswift.data.ClientRepository
import com.insureswift.data.db.AppDatabase
import com.insureswift.data.model.Client
import kotlinx.coroutines.launch

class ClientsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ClientRepository
    val allClients: LiveData<List<Client>>

    init {
        val clientDao = AppDatabase.getDatabase(application).clientDao()
        repository = ClientRepository(clientDao)
        allClients = repository.allClients.asLiveData()
    }

    fun insert(client: Client) = viewModelScope.launch {
        repository.insert(client)
    }
}
