package me.igorfedorov.customviewapp.feature.canvas

import me.igorfedorov.customviewapp.base.canvas_state.Color
import me.igorfedorov.customviewapp.base.canvas_state.Line
import me.igorfedorov.customviewapp.base.canvas_state.Size

data class CanvasViewState(
    val color: Color,
    val size: Size,
    val line: Line
)
