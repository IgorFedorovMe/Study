package me.igorfedorov.customviewapp.feature.canvas

import me.igorfedorov.customviewapp.base.canvas_state.EnumColor
import me.igorfedorov.customviewapp.base.canvas_state.EnumLine
import me.igorfedorov.customviewapp.base.canvas_state.EnumSize

data class CanvasViewState(
    val enumColor: EnumColor,
    val enumSize: EnumSize,
    val enumLine: EnumLine
)
