package com.insureswift.ui.policies

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.insureswift.data.model.Client
import com.insureswift.data.model.Policy
import com.insureswift.databinding.FragmentAddPolicyBinding
import com.insureswift.ui.clients.ClientsViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddPolicyFragment : Fragment() {

    private var _binding: FragmentAddPolicyBinding? = null
    private val binding get() = _binding!!

    // Using activityViewModels to share ViewModels with other fragments
    private val policiesViewModel: PoliciesViewModel by activityViewModels()
    private val clientsViewModel: ClientsViewModel by activityViewModels()

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private var clientList: List<Client> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPolicyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClientSpinner()
        setupDatePickers()

        binding.buttonSavePolicy.setOnClickListener {
            savePolicy()
        }
    }

    private fun setupClientSpinner() {
        clientsViewModel.allClients.observe(viewLifecycleOwner) { clients ->
            clientList = clients
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, clients.map { it.name })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerClients.adapter = adapter
        }
    }

    private fun setupDatePickers() {
        binding.editTextEffectiveDate.setOnClickListener {
            showDatePickerDialog { timestamp ->
                binding.editTextEffectiveDate.setText(dateFormat.format(timestamp))
            }
        }
        binding.editTextExpirationDate.setOnClickListener {
            showDatePickerDialog { timestamp ->
                binding.editTextExpirationDate.setText(dateFormat.format(timestamp))
            }
        }
    }

    private fun showDatePickerDialog(onDateSelected: (Long) -> Unit) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
            onDateSelected(selectedCalendar.timeInMillis)
        }, year, month, day).show()
    }

    private fun savePolicy() {
        val selectedClientPosition = binding.spinnerClients.selectedItemPosition
        if (clientList.isEmpty() || selectedClientPosition < 0 || selectedClientPosition >= clientList.size) {
            Toast.makeText(context, "Please select a client", Toast.LENGTH_SHORT).show()
            return
        }
        val selectedClient = clientList[selectedClientPosition]

        val policyType = binding.editTextPolicyType.text.toString().trim()
        val policyNumber = binding.editTextPolicyNumber.text.toString().trim()
        val carrier = binding.editTextCarrier.text.toString().trim()
        val premiumText = binding.editTextPremium.text.toString().trim()
        val coverageText = binding.editTextCoverage.text.toString().trim()
        val frequencyText = binding.editTextFrequency.text.toString().trim()
        val effectiveDateText = binding.editTextEffectiveDate.text.toString().trim()
        val expirationDateText = binding.editTextExpirationDate.text.toString().trim()

        if (policyType.isEmpty() || policyNumber.isEmpty() || carrier.isEmpty() || premiumText.isEmpty() || effectiveDateText.isEmpty() || expirationDateText.isEmpty() || coverageText.isEmpty() || frequencyText.isEmpty()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val effectiveDate = dateFormat.parse(effectiveDateText)?.time ?: 0L
            val expirationDate = dateFormat.parse(expirationDateText)?.time ?: 0L
            val premium = premiumText.toDouble()
            val coverage = coverageText.toDouble()

            val newPolicy = Policy(
                policyNumber = policyNumber,
                policyType = policyType,
                carrier = carrier,
                coverageAmount = coverage,
                effectiveDate = effectiveDate,
                expirationDate = expirationDate,
                premiumAmount = premium,
                paymentFrequency = frequencyText,
                status = "Active", // Default status
                clientId = selectedClient.clientId
            )

            policiesViewModel.insert(newPolicy)
            Toast.makeText(context, "Policy Saved", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()

        } catch (e: Exception) {
            Toast.makeText(context, "Invalid data format", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
