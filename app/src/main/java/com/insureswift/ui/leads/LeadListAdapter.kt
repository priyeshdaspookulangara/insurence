package com.insureswift.ui.leads

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insureswift.data.model.Lead
import com.insureswift.databinding.LeadListItemBinding

class LeadListAdapter(private val onItemClicked: (Lead) -> Unit) : ListAdapter<Lead, LeadListAdapter.LeadViewHolder>(LeadDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeadViewHolder {
        val binding = LeadListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeadViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class LeadViewHolder(private val binding: LeadListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lead: Lead) {
            binding.textViewLeadName.text = lead.name
            binding.textViewLeadContact.text = lead.contactInfo
            binding.chipStatus.text = lead.status
        }
    }
}

class LeadDiffCallback : DiffUtil.ItemCallback<Lead>() {
    override fun areItemsTheSame(oldItem: Lead, newItem: Lead): Boolean {
        return oldItem.leadId == newItem.leadId
    }

    override fun areContentsTheSame(oldItem: Lead, newItem: Lead): Boolean {
        return oldItem == newItem
    }
}
