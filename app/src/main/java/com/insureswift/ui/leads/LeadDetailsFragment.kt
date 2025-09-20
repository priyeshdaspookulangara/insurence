package com.insureswift.ui.leads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.insureswift.data.model.Lead
import com.insureswift.databinding.FragmentLeadDetailsBinding

class LeadDetailsFragment : Fragment() {

    private var _binding: FragmentLeadDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LeadDetailsViewModel by viewModels()
    private val args: LeadDetailsFragmentArgs by navArgs()

    private var currentLead: Lead? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeadDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupStatusSpinner()

        viewModel.getLead(args.leadId).observe(viewLifecycleOwner) { lead ->
            currentLead = lead
            if (lead != null) {
                binding.textLeadName.text = lead.name
                binding.textContactInfo.text = lead.contactInfo
                binding.textNotes.text = lead.notes

                // Set spinner selection without triggering the listener
                val statusArray = resources.getStringArray(com.insureswift.R.array.lead_statuses)
                val statusPosition = statusArray.indexOf(lead.status)
                if (statusPosition >= 0) {
                    binding.spinnerStatus.setSelection(statusPosition, false)
                }
            }
        }

        binding.buttonConvertToClient.setOnClickListener {
            currentLead?.let {
                val action = LeadDetailsFragmentDirections.actionLeadDetailsFragmentToAddClientFragment(it.name, it.contactInfo)
                findNavController().navigate(action)
            }
        }
    }

    private fun setupStatusSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            com.insureswift.R.array.lead_statuses,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerStatus.adapter = adapter
        }

        binding.spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val newStatus = parent.getItemAtPosition(position).toString()
                currentLead?.let {
                    if (it.status != newStatus) {
                        viewModel.updateLead(it.copy(status = newStatus))
                        Toast.makeText(context, "Status updated to $newStatus", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
