package me.igorfedorov.customviewapp

import androidx.annotation.ColorRes
import me.igorfedorov.customviewapp.base.Item
import me.igorfedorov.customviewapp.base.canvas_state.EnumLine
import me.igorfedorov.customviewapp.base.canvas_state.EnumSize

sealed class ToolsItem : Item {

    data class ColorModel(@ColorRes val color: Int) : ToolsItem()
    data class SizeModel(val enumSize: EnumSize) : ToolsItem()
    data class LineModel(val enumLine: EnumLine) : ToolsItem()

}
