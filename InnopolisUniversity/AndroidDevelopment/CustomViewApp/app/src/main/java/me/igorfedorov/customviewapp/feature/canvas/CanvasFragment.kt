package me.igorfedorov.customviewapp.feature.canvas

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.drawToBitmap
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import me.igorfedorov.customviewapp.R
import me.igorfedorov.customviewapp.ToolsLayout
import me.igorfedorov.customviewapp.base.canvas_state.EnumLine
import me.igorfedorov.customviewapp.base.canvas_state.EnumSize
import me.igorfedorov.customviewapp.base.utils.minSdk29
import me.igorfedorov.customviewapp.base.utils.setThrottledClickListener
import me.igorfedorov.customviewapp.base.utils.toastLong
import me.igorfedorov.customviewapp.databinding.FragmentCanvasBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CanvasFragment : Fragment(R.layout.fragment_canvas) {

    companion object {

        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { minSdk29().not() }
        )

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

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                saveBitmap()
            } else {
                viewModel.processUiEvent(UIEvent.OnReadWritePermissionDenied)
            }
        }

    private val startActivityForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            val pickedImageUri = activityResult.data?.data
            showImageAsBitmap(pickedImageUri)
        }
    }

    private fun showImageAsBitmap(pickedImageUri: Uri?) {
        pickedImageUri?.let {
            viewModel.processUiEvent(UIEvent.OnImagePicked(it))
        }
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

        viewModel.singleEvent.observe(viewLifecycleOwner, ::processSingleEvent)
    }

    private fun processSingleEvent(singleEvent: DataEvent.SingleEvent) {
        when (singleEvent) {
            is DataEvent.SingleEvent.ToastEvent -> {
                toastLong(singleEvent.stringRes)
            }
            is DataEvent.SingleEvent.OnBitmapResumed -> {
                requireActivity().findViewById<DrawView>(R.id.drawView)
                    .setBitmap(singleEvent.bitmap)
            }
        }
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
                    if (minSdk29())
                        saveBitmap()
                    else {
                        if (hasPermission())
                            saveBitmap()
                        else
                            PERMISSIONS.forEach { requestPermissionLauncher.launch(it) }
                    }
                    true
                }
                R.id.pick_image -> {
                    startActivityForResult.launch(Intent(Intent.ACTION_PICK).apply {
                        type = "image/*"
                    })
                    true
                }
                else -> false
            }
        }
    }

    private fun saveBitmap() {
        viewModel.processUiEvent(
            UIEvent.OnSaveDrawingClicked(
                requireActivity().findViewById<DrawView>(R.id.drawView)
                    .drawToBitmap()
            )
        )
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

    private fun hasPermission() =
        PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
}