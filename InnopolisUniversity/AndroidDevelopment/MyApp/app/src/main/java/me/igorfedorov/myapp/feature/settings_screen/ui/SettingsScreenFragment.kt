package me.igorfedorov.myapp.feature.settings_screen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import me.igorfedorov.myapp.R
import me.igorfedorov.myapp.common.Resource
import me.igorfedorov.myapp.common.autoCleared
import me.igorfedorov.myapp.common.textChangeFlow
import me.igorfedorov.myapp.databinding.FragmentSettingsScreenBinding
import me.igorfedorov.myapp.feature.settings_screen.di.VIEW_MODEL_SETTINGS
import me.igorfedorov.myapp.feature.settings_screen.domain.model.CityData
import me.igorfedorov.myapp.feature.settings_screen.ui.adapter.CityDataAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class SettingsScreenFragment : Fragment(R.layout.fragment_settings_screen) {

    private val screenViewModel: SettingsScreenViewModel by viewModel(
        qualifier = named(
            VIEW_MODEL_SETTINGS
        )
    )

    private var _binding: FragmentSettingsScreenBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Cannot access binding")

    private var cityDataAdapter: CityDataAdapter by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    //    @ExperimentalCoroutinesApi
//    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFlow()

        observeViewModel()

        initAdapter()
    }

    private fun observeViewModel() {
        screenViewModel.citiesData.onEach {
            updateProgressBarVisibility(it)
            updateErrorTextViewVisibility(it)
            when (it) {
                is Resource.Success -> {
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                        cityDataAdapter.items = it.data
                    }
                }
                is Resource.Error -> {
                    cityDataAdapter.items = emptyList()
                    binding.errorTextView.text = it.message
                }
                is Resource.Loading -> {

                }
                is Resource.Initialized -> {

                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun updateErrorTextViewVisibility(resource: Resource<List<CityData>>) {
        binding.errorTextView.isVisible = resource is Resource.Error
    }

    private fun updateProgressBarVisibility(resource: Resource<List<CityData>>) {
        binding.progressBarSettings.isVisible = resource is Resource.Loading
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun initFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            val cityNameFlow = binding.citiesNameEditText.textChangeFlow().onStart { emit("") }
            screenViewModel.getCitiesData(cityNameFlow)
        }
    }

    private fun initAdapter() {
        cityDataAdapter = CityDataAdapter { onCItyDataClick(it) }
        binding.citiesRecyclerView.apply {
            adapter = cityDataAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        cityDataAdapter.items = screenViewModel.citiesData.value.data
    }

    private fun onCItyDataClick(cityData: CityData) {
        findNavController().navigate(
            SettingsScreenFragmentDirections.actionSettingsScreenFragmentToWeatherScreenFragment(
                cityData.city
            )
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}