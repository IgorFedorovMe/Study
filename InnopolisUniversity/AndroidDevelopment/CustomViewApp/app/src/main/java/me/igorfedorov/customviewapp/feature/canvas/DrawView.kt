package me.igorfedorov.customviewapp.feature.canvas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import me.igorfedorov.customviewapp.R
import me.igorfedorov.customviewapp.base.canvas_state.EnumLine
import kotlin.math.abs

class DrawView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        private const val STROKE_WIDTH = 12f
    }

    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private var drawColor = ResourcesCompat.getColor(resources, R.color.black, null)

    private var path = Path()
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    private var currentX = 0f
    private var currentY = 0f
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    // Path representing
    private val drawing = Path() // the drawing
    private val curPath = Path() // what's currently being drawn

    private var onClick: () -> Unit = {}

    // Painting Settings
    private val paint = Paint().apply {
        color = drawColor
        isAntiAlias = true // Smooths out edges of what is drawn without affecting shape.
        isDither =
            true // Dithering affects how colors with higher-precision than the device are down-sampled.
        style = Paint.Style.STROKE // default: FILL
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
        canvas.drawPath(drawing, paint)
        canvas.drawPath(curPath, paint)
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        // Changed extraBitmap.recycle() to return for not to redraw onSizeChanged
        if (::extraBitmap.isInitialized) return
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    fun render(state: CanvasViewState) {
        drawColor = ResourcesCompat.getColor(resources, state.enumColor.value, null)
        paint.color = drawColor
        paint.strokeWidth = state.enumSize.value.toFloat()
        // When expression to simplify adding new Line states
        when (state.enumLine) {
            EnumLine.BROKEN -> {
                paint.pathEffect = DashPathEffect(
                    floatArrayOf(
                        state.enumSize.value.toFloat() * 2,
                        state.enumSize.value.toFloat() * 2,
                        state.enumSize.value.toFloat() * 2,
                        state.enumSize.value.toFloat() * 2
                    ), 0f
                )
            }
            EnumLine.CONTINUOUS -> {
                paint.pathEffect = null
            }
        }
    }

    fun setBitmap(bitmap: Bitmap) {
        extraBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        extraCanvas = Canvas(extraBitmap)
        invalidate()
    }

    fun clear() {
        extraCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        invalidate()
    }

    fun setOnClickField(onClickField: () -> Unit) {
        onClick = onClickField
    }

    private fun restartCurrentXY() {
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchStart() {
        onClick()
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        restartCurrentXY()
    }

    private fun touchMove() {
        val dx = abs(motionTouchEventX - currentX)
        val dy = abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            path.quadTo(
                currentX,
                currentY,
                (motionTouchEventX + currentX) / 2,
                (motionTouchEventY + currentY) / 2
            )
            restartCurrentXY()
            extraCanvas.drawPath(path, paint)
            extraCanvas.save()
        }
        invalidate()
    }

    private fun touchUp() {
        drawing.addPath(curPath)
        curPath.reset()
    }
}