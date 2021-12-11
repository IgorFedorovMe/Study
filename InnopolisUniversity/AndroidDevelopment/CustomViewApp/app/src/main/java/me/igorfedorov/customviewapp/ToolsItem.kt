package me.igorfedorov.customviewapp

import androidx.annotation.ColorRes
import me.igorfedorov.customviewapp.base.Item

sealed class ToolsItem : Item {

    data class ColorModel(@ColorRes val color: Int) : ToolsItem()

}
