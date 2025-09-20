package com.insureswift.ui.policies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.insureswift.R
import com.insureswift.databinding.FragmentPolicyDetailsBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PolicyDetailsFragment : Fragment() {

    private var _binding: FragmentPolicyDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PolicyDetailsViewModel by viewModels()
    private val args: PolicyDetailsFragmentArgs by navArgs()

    private var currentPolicy: Policy? = null
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPolicyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()

        val policyId = args.policyId
        viewModel.getPolicy(policyId).observe(viewLifecycleOwner) { policy ->
            if (policy != null) {
                currentPolicy = policy
                bindPolicyData(policy)

                // Now get the client details
                viewModel.getClient(policy.clientId).observe(viewLifecycleOwner) { client ->
                    if (client != null) {
                        binding.textClientName.text = client.name
                    }
                }
            }
        }
    }

    private fun bindPolicyData(policy: Policy) {
        binding.textPolicyNumber.text = policy.policyNumber
        binding.textPolicyType.text = policy.policyType
        binding.textCarrier.text = policy.carrier
        binding.textStatus.text = policy.status

        val effectiveDate = dateFormat.format(Date(policy.effectiveDate))
        val expirationDate = dateFormat.format(Date(policy.expirationDate))
        binding.textDates.text = getString(R.string.term_dates_format, effectiveDate, expirationDate)

        binding.textPremium.text = getString(R.string.premium_format, currencyFormat.format(policy.premiumAmount), policy.paymentFrequency)
        binding.textCoverage.text = currencyFormat.format(policy.coverageAmount)
    }

    private fun setupClickListeners() {
        binding.buttonRenew.setOnClickListener {
            currentPolicy?.let {
                viewModel.update(it.copy(status = "Renewed"))
                Toast.makeText(context, "Policy marked as Renewed", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonCancel.setOnClickListener {
            currentPolicy?.let {
                viewModel.update(it.copy(status = "Cancelled"))
                Toast.makeText(context, "Policy marked as Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
