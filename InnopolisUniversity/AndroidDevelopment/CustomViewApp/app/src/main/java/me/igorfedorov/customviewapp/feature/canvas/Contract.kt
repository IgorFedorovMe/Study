package me.igorfedorov.customviewapp.feature.canvas

import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.StringRes
import arrow.optics.optics
import me.igorfedorov.customviewapp.ToolsItem
import me.igorfedorov.customviewapp.base.base_view_model.Event
import me.igorfedorov.customviewapp.base.canvas_state.EnumLine
import me.igorfedorov.customviewapp.base.canvas_state.EnumSize

@optics
data class ViewState(
    val colors: List<ToolsItem.ColorModel>,
    val sizes: List<ToolsItem.SizeModel>,
    val lines: List<ToolsItem.LineModel>,
    val isToolsVisible: Boolean,
    val isPaletteToolsVisible: Boolean,
    val isSizeToolsVisible: Boolean,
    val isLineToolsVisible: Boolean,
    val canvasViewState: CanvasViewState
) {
    companion object
}

sealed class UIEvent : Event {
    object OnPaletteToolsClicked : UIEvent()
    object OnSizeToolsClicked : UIEvent()
    object OnLineToolsClicked : UIEvent()
    object OnShowTools : UIEvent()
    object OnReadWritePermissionDenied : UIEvent()
    data class OnSaveDrawingClicked(val bitmap: Bitmap) : UIEvent()
    data class OnColorClicked(val index: Int) : UIEvent()
    data class OnSizeClicked(val enumSize: EnumSize) : UIEvent()
    data class OnLineClicked(val enumLine: EnumLine) : UIEvent()
    data class OnImagePicked(val imageUri: Uri) : UIEvent()
}

sealed class DataEvent : Event {
    sealed class SingleEvent : DataEvent() {
        data class OnBitmapResumed(val bitmap: Bitmap) : SingleEvent()
        data class ToastEvent(@StringRes val stringRes: Int) : SingleEvent()
    }
}

