package com.example.controlredesapp.ui.obj

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.controlredesapp.R
import com.example.controlredesapp.core.Result
import com.example.controlredesapp.data.local.AppDatabase
import com.example.controlredesapp.data.local.LocalRedDataSource
import com.example.controlredesapp.databinding.FragmentGoalsBinding
import com.example.controlredesapp.domain.RedRepositoryImpl
import com.example.controlredesapp.presentation.RedViewModel
import com.example.controlredesapp.presentation.RedViewModelFactory
import com.example.controlredesapp.ui.adapters.GoalsAdapter

class GoalsFragment : Fragment(R.layout.fragment_goals) {

    private lateinit var binding: FragmentGoalsBinding
    private val viewModel by viewModels<RedViewModel> {
        RedViewModelFactory(
            RedRepositoryImpl(
                LocalRedDataSource(
                    AppDatabase.getDatabase(
                        requireContext()
                    ).logRedDao(), AppDatabase.getDatabase(requireContext()).usageGoalDao()
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGoalsBinding.bind(view)

        // Populando el Spinner para seleccionar redes:
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.redes_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.redesListSpinner.adapter = adapter
        }

        updateGoals()

        // Botón para añadir o actualizar un objetivo:
        binding.btnRegObj.setOnClickListener {
            val red = binding.redesListSpinner.selectedItem.toString()
            val horas = binding.editxtHoras.text.toString().toDouble()

            viewModel.newUsageGoalEntity(red, horas)
                .observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            updateGoals()
                        }

                        is Result.Failure -> {
                            viewModel.changeUsageGoalEntity(red, horas)
                                .observe(viewLifecycleOwner, Observer { result2 ->
                                    when (result2) {
                                        is Result.Loading -> {}
                                        is Result.Success -> {
                                            updateGoals()
                                        }

                                        is Result.Failure -> {}
                                    }
                                })
                        }
                    }
                })
        }
    }

    // Realizando las perticiones de consulta:

    private fun updateGoals() {
        viewModel.fetchUsageGoalEntity().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    binding.rvHome.adapter = GoalsAdapter(result.data)
                }

                is Result.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "Ocurrió un error ${result.e}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}