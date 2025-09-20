package com.insureswift.ui.policies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.insureswift.R
import com.insureswift.databinding.FragmentPoliciesBinding

class PoliciesFragment : Fragment() {

    private var _binding: FragmentPoliciesBinding? = null
    private val binding get() = _binding!!

    private val policiesViewModel: PoliciesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPoliciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PolicyListAdapter { policy ->
            val action = PoliciesFragmentDirections.actionPoliciesFragmentToPolicyDetailsFragment(policy.policyId)
            findNavController().navigate(action)
        }
        binding.recyclerViewPolicies.adapter = adapter

        policiesViewModel.allPolicies.observe(viewLifecycleOwner) { policies ->
            policies?.let { adapter.submitList(it) }
        }

        binding.fabAddPolicy.setOnClickListener {
            findNavController().navigate(R.id.action_policiesFragment_to_addPolicyFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
