package com.insureswift.ui.policies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insureswift.data.model.Policy
import com.insureswift.databinding.PolicyListItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PolicyListAdapter(private val onItemClicked: (Policy) -> Unit) : ListAdapter<Policy, PolicyListAdapter.PolicyViewHolder>(PolicyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PolicyViewHolder {
        val binding = PolicyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PolicyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PolicyViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class PolicyViewHolder(private val binding: PolicyListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        fun bind(policy: Policy) {
            binding.textViewPolicyType.text = policy.policyType
            binding.textViewPolicyNumber.text = policy.policyNumber
            binding.textViewExpirationDate.text = dateFormat.format(Date(policy.expirationDate))
        }
    }
}

class PolicyDiffCallback : DiffUtil.ItemCallback<Policy>() {
    override fun areItemsTheSame(oldItem: Policy, newItem: Policy): Boolean {
        return oldItem.policyId == newItem.policyId
    }

    override fun areContentsTheSame(oldItem: Policy, newItem: Policy): Boolean {
        return oldItem == newItem
    }
}
