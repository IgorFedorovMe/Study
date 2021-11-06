package me.igorfedorov.customviewapp.feature.canvas

import me.igorfedorov.customviewapp.R
import me.igorfedorov.customviewapp.ToolsItem
import me.igorfedorov.customviewapp.base.base_view_model.BaseViewModel
import me.igorfedorov.customviewapp.base.base_view_model.Event
import me.igorfedorov.customviewapp.base.canvas_state.EnumColor
import me.igorfedorov.customviewapp.base.canvas_state.EnumLine
import me.igorfedorov.customviewapp.base.canvas_state.EnumSize
import me.igorfedorov.customviewapp.base.utils.SingleLiveEvent
import me.igorfedorov.customviewapp.feature.canvas.domain.CanvasInteractor

class CanvasFragmentViewModel(
    private val canvasInteractor: CanvasInteractor
) : BaseViewModel<ViewState>() {

    val singleEvent = SingleLiveEvent<DataEvent.SingleEvent>()

    override fun initialViewState() = ViewState(
        colors = enumValues<EnumColor>().map { ToolsItem.ColorModel(it.value) },
        sizes = enumValues<EnumSize>().map { ToolsItem.SizeModel(it) },
        lines = enumValues<EnumLine>().map { ToolsItem.LineModel(it) },
        isToolsVisible = false,
        isPaletteToolsVisible = false,
        isSizeToolsVisible = false,
        isLineToolsVisible = false,
        canvasViewState = CanvasViewState(
            enumColor = EnumColor.BLACK,
            enumSize = EnumSize.SMALL,
            enumLine = EnumLine.CONTINUOUS
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
                        enumColor = EnumColor.from(
                            previousState.colors[event.index].color
                        )
                    )
                )
            }
            is UIEvent.OnSizeClicked -> {
                return previousState.copy(
                    isSizeToolsVisible = !previousState.isSizeToolsVisible,
                    canvasViewState = previousState.canvasViewState.copy(
                        enumSize = previousState.sizes[event.enumSize.ordinal].enumSize
                    )
                )
            }
            is UIEvent.OnLineClicked -> {
                processUiEvent(UIEvent.OnLineToolsClicked)
                return ViewState.canvasViewState.enumLine.set(previousState, event.enumLine)
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
            is UIEvent.OnSaveDrawingClicked -> {
                canvasInteractor.saveBitmapToMediaStore(event.bitmap)
            }
            is UIEvent.OnReadWritePermissionDenied -> {
                DataEvent.SingleEvent.ToastEvent(R.string.permission_not_granted)
            }
            is UIEvent.OnImagePicked -> {
                canvasInteractor.getBitmapFromMediaStore(event.imageUri).fold(
                    onError = {

                    },
                    onSuccess = {
                        processDataEvent(DataEvent.SingleEvent.OnBitmapResumed(it))
                    }
                )
            }
            is DataEvent.SingleEvent.OnBitmapResumed -> {
                singleEvent.value = event
            }
            is DataEvent.SingleEvent.ToastEvent -> {
                singleEvent.value = event
            }
        }
        return null
    }
}