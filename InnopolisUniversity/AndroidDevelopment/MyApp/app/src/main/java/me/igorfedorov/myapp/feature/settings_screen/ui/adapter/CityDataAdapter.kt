package me.igorfedorov.myapp.feature.settings_screen.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import me.igorfedorov.myapp.R
import me.igorfedorov.myapp.common.setThrottledClickListener
import me.igorfedorov.myapp.databinding.ItemCityDataBinding
import me.igorfedorov.myapp.feature.settings_screen.domain.model.CityData

class CityDataAdapter(onItemClick: (cityData: CityData) -> Unit) :
    AsyncListDifferDelegationAdapter<CityData>(CitiesDataDiffUtilCallback()) {

    private fun citiesDataAdapterDelegate(onItemClick: (cityData: CityData) -> Unit) =
        adapterDelegateViewBinding<CityData, CityData, ItemCityDataBinding>(
            { layoutInflater, parent -> ItemCityDataBinding.inflate(layoutInflater, parent, false) }
        ) {

            bind {
                binding.root.setThrottledClickListener {
                    onItemClick(item)
                }
                binding.apply {
                    cityTextView.text = item.city
                    countryTextView.text = item.city
                    nameTextView.text = item.name
                    cityImageViewCity.load(R.drawable.ic_city)
                }
            }
        }

    init {
        delegatesManager.addDelegate(citiesDataAdapterDelegate(onItemClick))
    }


    class CitiesDataDiffUtilCallback : DiffUtil.ItemCallback<CityData>() {
        override fun areItemsTheSame(oldItem: CityData, newItem: CityData): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: CityData, newItem: CityData): Boolean {
            return oldItem == newItem
        }
    }
}