package me.igorfedorov.myapp.feature.weather_screen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import me.igorfedorov.myapp.R
import me.igorfedorov.myapp.common.setAdapterAndCleanupOnDetachFromWindow
import me.igorfedorov.myapp.databinding.FragmentWeatherScreenBinding
import me.igorfedorov.myapp.feature.weather_screen.di.VIEW_MODEL_WEATHER
import me.igorfedorov.myapp.feature.weather_screen.ui.adapter.WeatherDataAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class WeatherScreenFragment : Fragment(R.layout.fragment_weather_screen) {

    private val weatherViewModel: WeatherScreenViewModel by viewModel(
        qualifier = named(
            VIEW_MODEL_WEATHER
        )
    )

    private val binding: FragmentWeatherScreenBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private val weatherDataAdapter: WeatherDataAdapter by lazy {
        WeatherDataAdapter(
            onItemClick = weatherViewModel::showMoreWeather,
            onItemLongClick = weatherViewModel::deleteFromWeatherList
        )
    }

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

        getWeatherForCItyFromSettings()

        weatherViewModel.viewState.observe(viewLifecycleOwner, ::render)
    }

    private fun getWeatherForCItyFromSettings() {
        val cityName = navArgs<WeatherScreenFragmentArgs>().value.cityName ?: return
        weatherViewModel.requestWeatherByCity(cityName)
    }

    private fun render(weatherScreenState: WeatherScreenState) {

        updateWeatherAdapterItems(weatherScreenState)

        updateErrorText(weatherScreenState)

        updateProgressBar(weatherScreenState)

    }

    private fun updateWeatherAdapterItems(weatherScreenState: WeatherScreenState) {
        weatherDataAdapter.items = weatherScreenState.weatherList
    }

    private fun updateErrorText(weatherScreenState: WeatherScreenState) {
        binding.errorTextViewWeather.text = weatherScreenState.errorMessage
        binding.errorTextViewWeather.isVisible = weatherScreenState.isInErrorState
    }

    private fun updateProgressBar(weatherScreenState: WeatherScreenState) {
        binding.progressBarWeather.isVisible = weatherScreenState.isLoading
    }

    private fun initAdapter() {
        binding.weatherRecyclerView.apply {
            setAdapterAndCleanupOnDetachFromWindow(weatherDataAdapter)
            layoutManager = LinearLayoutManager(requireContext())
        }
        weatherDataAdapter.items = weatherViewModel.viewState.value?.weatherList
    }

    /*
    Method to help visualize adding another city to adapter
    **/
    private fun initWeatherButton() {
        binding.getWeatherButton.setOnClickListener {
            weatherViewModel.requestWeatherByCity("London")
        }
    }
}