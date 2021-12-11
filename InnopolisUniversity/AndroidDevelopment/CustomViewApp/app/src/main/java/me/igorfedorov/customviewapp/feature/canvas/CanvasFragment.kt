package me.igorfedorov.customviewapp.feature.canvas

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import me.igorfedorov.customviewapp.R
import me.igorfedorov.customviewapp.ToolsLayout
import me.igorfedorov.customviewapp.databinding.FragmentCanvasBinding

class CanvasFragment : Fragment(R.layout.fragment_canvas) {

    companion object {

        private const val PALETTE = 0

        fun newInstance() = CanvasFragment()
    }

    private val binding: FragmentCanvasBinding by viewBinding(FragmentCanvasBinding::bind)

    private lateinit var toolsLayouts: List<ToolsLayout>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        toolsLayouts = listOf(binding.palette as ToolsLayout)
    }

}