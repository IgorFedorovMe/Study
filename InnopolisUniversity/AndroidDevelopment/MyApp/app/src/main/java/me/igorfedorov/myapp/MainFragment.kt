package me.igorfedorov.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import me.igorfedorov.myapp.common.setThrottledClickListener
import me.igorfedorov.myapp.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Cannot access binding")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.weatherButton.setThrottledClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToWeatherScreenFragment(
                    null
                )
            )
        }

        binding.settingsButton.setThrottledClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToSettingsScreenFragment()
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}