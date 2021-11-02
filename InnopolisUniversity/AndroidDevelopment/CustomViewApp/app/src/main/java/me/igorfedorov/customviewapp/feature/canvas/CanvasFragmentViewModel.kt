package me.igorfedorov.customviewapp.feature.canvas

import me.igorfedorov.customviewapp.ToolsItem
import me.igorfedorov.customviewapp.base.base_view_model.BaseViewModel
import me.igorfedorov.customviewapp.base.base_view_model.Event
import me.igorfedorov.customviewapp.base.canvas_state.Color
import me.igorfedorov.customviewapp.base.canvas_state.Line
import me.igorfedorov.customviewapp.base.canvas_state.Size

class CanvasFragmentViewModel : BaseViewModel<ViewState>() {

    override fun initialViewState() = ViewState(
        colors = enumValues<Color>().map { ToolsItem.ColorModel(it.value) },
        sizes = enumValues<Size>().map { ToolsItem.SizeModel(it) },
        lines = enumValues<Line>().map { ToolsItem.LineModel(it) },
        isToolsVisible = false,
        isPaletteToolsVisible = false,
        isSizeToolsVisible = false,
        isLineToolsVisible = false,
        canvasViewState = CanvasViewState(
            color = Color.BLACK,
            size = Size.SMALL,
            line = Line.CONTINUOUS
        )
    )

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            /*is UIEvent.OnToolsClicked -> {
                return previousState.copy(isToolsVisible = !previousState.isToolsVisible)
            }*/
            is UIEvent.OnShowTools -> {
                return previousState.copy(isToolsVisible = !previousState.isToolsVisible)
            }
            is UIEvent.OnColorClicked -> {
                return previousState.copy(
                    isPaletteToolsVisible = !previousState.isPaletteToolsVisible,
                    canvasViewState = previousState.canvasViewState.copy(
                        color = Color.from(
                            previousState.colors[event.index].color
                        )
                    )
                )
            }
            is UIEvent.OnSizeClicked -> {
                return previousState.copy(
                    isSizeToolsVisible = !previousState.isSizeToolsVisible,
                    canvasViewState = previousState.canvasViewState.copy(
                        size = previousState.sizes[event.size.ordinal].size
                    )
                )
            }
            is UIEvent.OnLineClicked -> {
                return previousState.copy(
                    isLineToolsVisible = !previousState.isLineToolsVisible,
                    canvasViewState = previousState.canvasViewState.copy(
                        line = previousState.lines[event.line.ordinal].line
                    )
                )
            }
            is UIEvent.OnPaletteToolsClicked -> {
                return previousState.copy(isPaletteToolsVisible = !previousState.isPaletteToolsVisible)
            }
            is UIEvent.OnSizeToolsClicked -> {
                return previousState.copy(isSizeToolsVisible = !previousState.isSizeToolsVisible)
            }
            is UIEvent.OnLineToolsClicked -> {
                return previousState.copy(isLineToolsVisible = !previousState.isLineToolsVisible)
            }
        }
        return null
    }
}