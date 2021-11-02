package me.igorfedorov.customviewapp

import androidx.annotation.ColorRes
import me.igorfedorov.customviewapp.base.Item
import me.igorfedorov.customviewapp.base.canvas_state.Line
import me.igorfedorov.customviewapp.base.canvas_state.Size

sealed class ToolsItem : Item {

    data class ColorModel(@ColorRes val color: Int) : ToolsItem()
    data class SizeModel(val size: Size) : ToolsItem()
    data class LineModel(val line: Line) : ToolsItem()

}
