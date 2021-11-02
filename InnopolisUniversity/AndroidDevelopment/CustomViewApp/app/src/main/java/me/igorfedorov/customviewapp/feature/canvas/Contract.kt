package me.igorfedorov.customviewapp.feature.canvas

import me.igorfedorov.customviewapp.ToolsItem
import me.igorfedorov.customviewapp.base.base_view_model.Event
import me.igorfedorov.customviewapp.base.canvas_state.Line
import me.igorfedorov.customviewapp.base.canvas_state.Size

data class ViewState(
    val colors: List<ToolsItem.ColorModel>,
    val sizes: List<ToolsItem.SizeModel>,
    val lines: List<ToolsItem.LineModel>,
    val isToolsVisible: Boolean,
    val isPaletteToolsVisible: Boolean,
    val isSizeToolsVisible: Boolean,
    val isLineToolsVisible: Boolean,
    val canvasViewState: CanvasViewState,
)

sealed class UIEvent : Event {
    object OnToolsClicked : UIEvent()
    object OnPaletteToolsClicked : UIEvent()
    object OnSizeToolsClicked : UIEvent()
    object OnLineToolsClicked : UIEvent()
    object OnShowTools : UIEvent()
    data class OnColorClicked(val index: Int) : UIEvent()
    data class OnSizeClicked(val size: Size) : UIEvent()
    data class OnLineClicked(val line: Line) : UIEvent()
}