package com.example.controlredesapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.controlredesapp.R
import com.example.controlredesapp.core.Result
import com.example.controlredesapp.data.local.AppDatabase
import com.example.controlredesapp.data.local.LocalRedDataSource
import com.example.controlredesapp.databinding.FragmentHomeScreenBinding
import com.example.controlredesapp.domain.RedRepositoryImpl
import com.example.controlredesapp.presentation.RedViewModel
import com.example.controlredesapp.presentation.RedViewModelFactory
import com.juang.jplot.PlotBarritas

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding
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
        binding = FragmentHomeScreenBinding.bind(view)

        // Populando el Spinner para seleccionar redes:
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.redes_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.redesListSpinner.adapter = adapter
        }

        // Inicializando gráfica de barras para mostrar registros del usuasrio:
        val datosy = DoubleArray(10)
        val graph =
            PlotBarritas(this.context, "Uso de redes", "Horas de uso diarias de la red")

        val datosx: Array<String> = arrayOf(
            "Hace 9 día(s)",
            "Hace 8 día(s)",
            "Hace 7 día(s)",
            "Hace 6 día(s)",
            "Hace 5 día(s)",
            "Hace 4 día(s)",
            "Hace 3 día(s)",
            "Hace 2 día(s)",
            "Hace 1 día(s)",
            "Hace 0 día(s)"
        )

        datosy[0] = 0.0
        datosy[1] = 0.0
        datosy[2] = 0.0
        datosy[3] = 0.0
        datosy[4] = 0.0
        datosy[5] = 0.0
        datosy[6] = 0.0
        datosy[7] = 0.0
        datosy[8] = 0.0
        datosy[9] = 0.0

        // Configurando y mostrando la gráfica
        graph.Columna(datosx, datosy)
        graph.SetHD(true)
        graph.SetColorFondo(0, 255, 255, 255)
        graph.SetColorTextBarras(0, 0, 0)
        graph.SetColorTextX(0, 0, 0)
        graph.SetColorTitulo(0, 0, 0)
        graph.SetColorTituloY(0, 0, 0)

        binding.barGraphLogs.removeAllViews()
        binding.barGraphLogs.addView(graph)

        // Insertando y actualizando un registro de uso:
        binding.btnRegLog.setOnClickListener {
            val red = binding.redesListSpinner.selectedItem.toString()
            val horas = binding.editxtHoras.text.toString().toDouble()

            viewModel.fetchLogRedEntity(red).observe(
                viewLifecycleOwner,
                Observer { result -> //Se hace la consulta en primer lugar para saber si hay o no un registro en base de datos
                    when (result) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            viewModel.changeLogRedEntity(red, horas)
                                .observe(viewLifecycleOwner, Observer { result2 ->
                                    when (result2) {
                                        is Result.Loading -> {}
                                        is Result.Success -> {
                                            updateGraph(graph, datosx)
                                            viewModel.changeUsageGoalEntity(
                                                red,
                                                0.0
                                            ) //Una vez cambiados los datos en la base de datos de losg, también se actualiza la de usage
                                                .observe(viewLifecycleOwner, Observer { result3 ->
                                                    when (result3) {
                                                        is Result.Loading -> {}
                                                        is Result.Success -> {}
                                                        is Result.Failure -> {}
                                                    }
                                                })
                                        }

                                        is Result.Failure -> {}
                                    }
                                })
                        }

                        is Result.Failure -> {
                            viewModel.newLogRedEntity(red, horas)
                                .observe(viewLifecycleOwner, Observer { result2 ->
                                    when (result2) {
                                        is Result.Loading -> {}
                                        is Result.Success -> {
                                            updateGraph(graph, datosx)
                                            viewModel.changeUsageGoalEntity(
                                                red,
                                                horas
                                            ) //Una vez cambiados los datos en la base de datos de losg, también se actualiza la de usage
                                                .observe(viewLifecycleOwner, Observer { result3 ->
                                                    when (result3) {
                                                        is Result.Loading -> {}
                                                        is Result.Success -> {}
                                                        is Result.Failure -> {}
                                                    }
                                                })
                                        }

                                        is Result.Failure -> {}
                                    }
                                })
                        }
                    }
                })
        }

        //Botón ir a lista de objetivos:
        binding.btnGoals.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreenFragment_to_goalsFragment)
        }
    }

    // Realizando consulta y mostrando gráfica de barras con los datos de esa consulta:

    private fun updateGraph(graph: PlotBarritas, datosx: Array<String>) {
        val datosy = DoubleArray(10)
        val red = binding.redesListSpinner.selectedItem.toString()

        viewModel.fetchLogRedEntity(red).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {

                    // Estableciendo los datos para la gráfica
                    datosy[0] = result.data.usageLogs[0]
                    datosy[1] = result.data.usageLogs[1]
                    datosy[2] = result.data.usageLogs[2]
                    datosy[3] = result.data.usageLogs[3]
                    datosy[4] = result.data.usageLogs[4]
                    datosy[5] = result.data.usageLogs[5]
                    datosy[6] = result.data.usageLogs[6]
                    datosy[7] = result.data.usageLogs[7]
                    datosy[8] = result.data.usageLogs[8]
                    datosy[9] = result.data.usageLogs[9]

                    graph.Columna(datosx, datosy)

                    binding.barGraphLogs.removeAllViews()
                    binding.barGraphLogs.addView(graph)

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