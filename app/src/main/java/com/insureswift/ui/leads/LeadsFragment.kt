package com.insureswift.ui.leads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.insureswift.R
import com.insureswift.databinding.FragmentLeadsBinding

class LeadsFragment : Fragment() {

    private var _binding: FragmentLeadsBinding? = null
    private val binding get() = _binding!!

    private val leadsViewModel: LeadsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = LeadListAdapter { lead ->
            val action = LeadsFragmentDirections.actionLeadsFragmentToLeadDetailsFragment(lead.leadId)
            findNavController().navigate(action)
        }
        binding.recyclerViewLeads.adapter = adapter

        leadsViewModel.allLeads.observe(viewLifecycleOwner) { leads ->
            leads?.let { adapter.submitList(it) }
        }

        binding.fabAddLead.setOnClickListener {
            findNavController().navigate(R.id.action_leadsFragment_to_addLeadFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
