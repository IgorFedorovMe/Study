package me.igorfedorov.myapp.feature.weather_screen.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.igorfedorov.myapp.R
import me.igorfedorov.myapp.common.Resource
import me.igorfedorov.myapp.common.autoCleared
import me.igorfedorov.myapp.common.setAdapterAndCleanupOnDetachFromWindow
import me.igorfedorov.myapp.databinding.FragmentWeatherScreenBinding
import me.igorfedorov.myapp.feature.weather_screen.di.VIEW_MODEL_WEATHER
import me.igorfedorov.myapp.feature.weather_screen.domain.model.WeatherMain
import me.igorfedorov.myapp.feature.weather_screen.ui.adapter.WeatherDataAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import permissions.dispatcher.ktx.LocationPermission
import permissions.dispatcher.ktx.constructLocationPermissionRequest
import timber.log.Timber

class WeatherScreenFragment : Fragment(R.layout.fragment_weather_screen) {

    private val weatherViewModel: WeatherScreenViewModel by viewModel(
        qualifier = named(
            VIEW_MODEL_WEATHER
        )
    )

    private val binding: FragmentWeatherScreenBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private var weatherDataAdapter: WeatherDataAdapter by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWeatherButton()

        initAdapter()

        observeViewModel()

        getWeatherForCurrentLocation()
    }

    private fun initAdapter() {
        weatherDataAdapter = WeatherDataAdapter(
            onItemClick = ::showMoreWeather,
            onItemLongClick = ::deleteCity
        )
        binding.weatherRecyclerView.apply {
            setAdapterAndCleanupOnDetachFromWindow(weatherDataAdapter)
            layoutManager = LinearLayoutManager(requireContext())
        }
        weatherDataAdapter.items = weatherViewModel.weather.value.data
    }

    private fun showMoreWeather(weatherMain: WeatherMain) {
        // to do maybe expand card maybe open new fragment idk yet
    }

    /*
    Delete item in adapter by longClicking it
    **/
    private fun deleteCity(weatherMain: WeatherMain) {
        weatherViewModel.deleteFromWeatherList(weatherMain)
    }

    @SuppressLint("MissingPermission")
    private fun getWeatherForCurrentLocation() {
        viewLifecycleOwner.lifecycleScope.launch {
            constructLocationPermissionRequest(
                permissions = arrayOf(LocationPermission.FINE, LocationPermission.COARSE),
                requiresPermission = {
                    weatherViewModel.getCurrentLocation(
                        requireContext(),
                        navArgs<WeatherScreenFragmentArgs>().value.cityName
                    )
                }
            ).launch()
        }
    }

    private fun observeViewModel() {
        weatherViewModel.weather.onEach {
            observeProgressBarVisibility(it)
            when (it) {
                is Resource.Success -> {
                    updateWeatherAdapterItems(it.data)
                }
                is Resource.Error -> {
                    updateErrorText(it)
                }
                is Resource.Loading -> {
                }
                is Resource.Initialized -> {
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycle.coroutineScope)
    }

    private fun updateWeatherAdapterItems(data: List<WeatherMain>?) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            Timber.d(data?.toString())
            weatherDataAdapter.items = data
            weatherDataAdapter.notifyDataSetChanged()
        }
    }

    private fun updateErrorText(error: Resource.Error<List<WeatherMain>>) {
        binding.errorTextViewWeather.text = error.message
    }

    /*
    Method to help visualize adding another city to adapter
    **/
    private fun initWeatherButton() {
        binding.getWeatherButton.setOnClickListener {
            weatherViewModel.requestWeatherByCity("London")
        }
    }

    private fun observeProgressBarVisibility(resource: Resource<List<WeatherMain>>) {
        binding.progressBarWeather.isVisible = resource is Resource.Loading
    }
}