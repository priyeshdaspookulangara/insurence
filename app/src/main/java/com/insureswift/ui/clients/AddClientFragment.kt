package com.insureswift.ui.clients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.insureswift.data.model.Client
import com.insureswift.databinding.FragmentAddClientBinding

class AddClientFragment : Fragment() {

    private var _binding: FragmentAddClientBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ClientsViewModel by activityViewModels()
    private val args: AddClientFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddClientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextName.setText(args.leadName)
        binding.editTextPhone.setText(args.leadContact)

        binding.buttonSave.setOnClickListener {
            saveClient()
        }
    }

    private fun saveClient() {
        val name = binding.editTextName.text.toString().trim()
        val phone = binding.editTextPhone.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val address = binding.editTextAddress.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(context, "Client name cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val client = Client(name = name, phoneNumber = phone, email = email, address = address)
        viewModel.insert(client)

        Toast.makeText(context, "Client saved", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
