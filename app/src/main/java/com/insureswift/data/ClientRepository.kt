package com.insureswift.data

import com.insureswift.data.db.ClientDao
import com.insureswift.data.model.Client
import kotlinx.coroutines.flow.Flow

class ClientRepository(private val clientDao: ClientDao) {

    val allClients: Flow<List<Client>> = clientDao.getAllClients()

    suspend fun insert(client: Client) {
        clientDao.insertClient(client)
    }

    suspend fun update(client: Client) {
        clientDao.updateClient(client)
    }

    suspend fun delete(client: Client) {
        clientDao.deleteClient(client)
    }
}
