package me.igorfedorov.customviewapp.feature.canvas

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.drawToBitmap
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import me.igorfedorov.customviewapp.R
import me.igorfedorov.customviewapp.ToolsLayout
import me.igorfedorov.customviewapp.base.canvas_state.EnumLine
import me.igorfedorov.customviewapp.base.canvas_state.EnumSize
import me.igorfedorov.customviewapp.base.utils.setThrottledClickListener
import me.igorfedorov.customviewapp.databinding.FragmentCanvasBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import permissions.dispatcher.ktx.constructLocationPermissionRequest

class CanvasFragment : Fragment(R.layout.fragment_canvas) {

    companion object {

        private const val PALETTE = 0
        private const val SIZE = 1
        private const val LINE = 2

        fun newInstance() = CanvasFragment()
    }

    private val viewModel: CanvasFragmentViewModel by viewModel()

    private val binding: FragmentCanvasBinding by viewBinding()

    private val toolsLayouts: List<ToolsLayout> by lazy {
        listOf(
            requireActivity().findViewById(R.id.palette),
            requireActivity().findViewById(R.id.size),
            requireActivity().findViewById(R.id.line)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        setOnToolsClickListeners()

        toolsLayouts[PALETTE].setOnClickListener {
            viewModel.processUiEvent(UIEvent.OnColorClicked(it))
        }
        toolsLayouts[SIZE].setOnClickListener {
            viewModel.processUiEvent(UIEvent.OnSizeClicked(EnumSize.values()[it]))
        }
        toolsLayouts[LINE].setOnClickListener {
            viewModel.processUiEvent(UIEvent.OnLineClicked(EnumLine.values()[it]))
        }
        viewModel.viewState.observe(viewLifecycleOwner, ::render)
    }

    private fun setOnToolsClickListeners() {
        binding.apply {
            palettePickerImageView.setThrottledClickListener {
                viewModel.processUiEvent(UIEvent.OnPaletteToolsClicked)
            }
            sizePickerImageView.setThrottledClickListener {
                viewModel.processUiEvent(UIEvent.OnSizeToolsClicked)
            }
            linePickerImageView.setThrottledClickListener {
                viewModel.processUiEvent(UIEvent.OnLineToolsClicked)
            }
        }
    }

    private fun initToolbar() {
        requireActivity().findViewById<Toolbar>(R.id.toolbar).setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.show_palette -> {
                    viewModel.processUiEvent(UIEvent.OnShowTools)
                    true
                }
                R.id.delete_drawing -> {
                    requireActivity().findViewById<DrawView>(R.id.drawView).clear()
                    true
                }
                R.id.save_drawing -> {
                    viewLifecycleOwner.lifecycleScope.launch {
                        constructLocationPermissionRequest(
                            permissions = arrayOf(
                            ),
                            requiresPermission = {
                                viewModel.saveBitmap(
                                    requireActivity().findViewById<DrawView>(R.id.drawView)
                                        .drawToBitmap()
                                )
                            }
                        ).launch()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun render(viewState: ViewState) {

        renderPalettePickerImageView(viewState)
        renderLinePickerImageView(viewState)
        renderSizePickerImageView(viewState)

        toolsLayouts[PALETTE].isGone = !viewState.isPaletteToolsVisible
        toolsLayouts[PALETTE].render(viewState.colors)
        toolsLayouts[SIZE].isGone = !viewState.isSizeToolsVisible
        toolsLayouts[SIZE].render(viewState.sizes)
        toolsLayouts[LINE].isGone = !viewState.isLineToolsVisible
        toolsLayouts[LINE].render(viewState.lines)
        requireActivity().findViewById<DrawView>(R.id.drawView).render(viewState.canvasViewState)
    }

    private fun renderSizePickerImageView(viewState: ViewState) {
        binding.sizePickerImageView.isGone = !viewState.isToolsVisible
        binding.sizePickerImageView.setImageResource(
            when (viewState.canvasViewState.enumSize) {
                EnumSize.SMALL -> {
                    R.drawable.ic_size_small
                }
                EnumSize.MEDIUM -> {
                    R.drawable.ic_size_medium
                }
                EnumSize.LARGE -> {
                    R.drawable.ic_size_large
                }
            }
        )
    }

    private fun renderLinePickerImageView(viewState: ViewState) {
        binding.linePickerImageView.isGone = !viewState.isToolsVisible
    }

    private fun renderPalettePickerImageView(viewState: ViewState) {
        binding.palettePickerImageView.apply {
            isGone = !viewState.isToolsVisible
            setBackgroundColor(
                resources.getColor(viewState.canvasViewState.enumColor.value, null)
            )
        }
    }
}