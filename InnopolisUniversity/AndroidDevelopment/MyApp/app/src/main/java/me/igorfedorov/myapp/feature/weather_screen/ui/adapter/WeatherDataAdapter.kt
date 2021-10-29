package me.igorfedorov.myapp.feature.weather_screen.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import me.igorfedorov.myapp.R
import me.igorfedorov.myapp.common.setThrottledClickListener
import me.igorfedorov.myapp.databinding.ItemWeatherDataBinding
import me.igorfedorov.myapp.feature.weather_screen.domain.model.WeatherMain

class WeatherDataAdapter(
    onItemClick: (weatherData: WeatherMain) -> Unit,
    onItemLongClick: (weatherData: WeatherMain) -> Unit
) :
    AsyncListDifferDelegationAdapter<WeatherMain>(WeatherDataDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(weatherDataAdapterDelegate(onItemClick, onItemLongClick))
    }

    private fun weatherDataAdapterDelegate(
        onItemClick: (weatherData: WeatherMain) -> Unit,
        onItemLongClick: (weatherData: WeatherMain) -> Unit
    ) =
        adapterDelegateViewBinding<WeatherMain, WeatherMain, ItemWeatherDataBinding>(
            { layoutInflater, parent ->
                ItemWeatherDataBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            }
        ) {

            binding.root.setThrottledClickListener {
                onItemClick(item)
            }
            binding.root.setOnLongClickListener {
                onItemLongClick(item)
                true
            }

            bind {
                binding.apply {
                    cityNameTextView.text = item.name
                    temperatureTextView.text = item.main.temp.toString()
                    cityImageViewWeather.load(R.drawable.ic_city)
                }
            }
        }

    class WeatherDataDiffUtilCallback : DiffUtil.ItemCallback<WeatherMain>() {
        override fun areItemsTheSame(oldItem: WeatherMain, newItem: WeatherMain): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: WeatherMain, newItem: WeatherMain): Boolean {
            return oldItem == newItem
        }
    }
}