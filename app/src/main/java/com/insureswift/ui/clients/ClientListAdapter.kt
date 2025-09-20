package com.insureswift.ui.clients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insureswift.data.model.Client
import com.insureswift.databinding.ClientListItemBinding

class ClientListAdapter : ListAdapter<Client, ClientListAdapter.ClientViewHolder>(ClientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val binding = ClientListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ClientViewHolder(private val binding: ClientListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(client: Client) {
            binding.textViewClientName.text = client.name
            binding.textViewClientPhone.text = client.phoneNumber
            // Here you could also load the profile picture using a library like Glide or Coil
            // For now, it will just show the placeholder icon.
        }
    }
}

class ClientDiffCallback : DiffUtil.ItemCallback<Client>() {
    override fun areItemsTheSame(oldItem: Client, newItem: Client): Boolean {
        return oldItem.clientId == newItem.clientId
    }

    override fun areContentsTheSame(oldItem: Client, newItem: Client): Boolean {
        return oldItem == newItem
    }
}
