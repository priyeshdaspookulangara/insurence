package com.insureswift.ui.leads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.insureswift.data.model.Lead
import com.insureswift.databinding.FragmentAddLeadBinding

class AddLeadFragment : Fragment() {

    private var _binding: FragmentAddLeadBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LeadsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddLeadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSaveLead.setOnClickListener {
            saveLead()
        }
    }

    private fun saveLead() {
        val name = binding.editTextLeadName.text.toString().trim()
        val contact = binding.editTextContactInfo.text.toString().trim()
        val notes = binding.editTextNotes.text.toString().trim()

        if (name.isEmpty() || contact.isEmpty()) {
            Toast.makeText(context, "Name and Contact Info cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val newLead = Lead(
            name = name,
            contactInfo = contact,
            notes = notes,
            status = "New" // Default status
        )

        viewModel.insert(newLead)
        Toast.makeText(context, "Lead Saved", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
