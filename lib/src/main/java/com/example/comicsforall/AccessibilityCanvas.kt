package com.example.comicsforall

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.view.ViewCompat
import com.example.comicsforall.model.AccessibilityCanvasItem
import com.example.comicsforall.model.AccessibilityItem
import com.example.comicsforall.model.RectFrame

// TODO ViewModel with state,
open class AccessibilityCanvas(context: Context, attr: AttributeSet?) :
    View(context, attr), ExtendedAccessibility {

    private var firstPoint: Point? = null
    private var frame: RectFrame? = null
    private var _isDrawing = false
    private var listener: AccessibilityCanvasItemListener? = null
    private var currentItem: AccessibilityCanvasItem? = null
    private var accessibilityListItems = mutableListOf<AccessibilityCanvasItem>()

    val isDrawing get() = _isDrawing

    override fun setAccessibilityContentList(list: List<AccessibilityItem>) {
        accessibilityListItems =
            list.mapIndexed { index: Int, item: AccessibilityItem ->
                AccessibilityCanvasItem(text = item.text, rect = item.frame, position = index + 1)
            }.toMutableList()
        ViewCompat.setAccessibilityDelegate(this, AccessibilityHelper(this, list))
    }

    fun openDrawCanvas(listener: AccessibilityCanvasItemListener) {
        this.listener = listener
        _isDrawing = true
        setOnTouchListener { view, event ->
            view.performClick()
            draw(event)
        }
    }

    fun sendEvent(event: AccessibilityCanvasEvent) {
        when (event) {
            is AddAccessibilityCanvasItem -> {
                accessibilityListItems.add(event.item)
                currentItem?.let { accessibilityListItems.remove(it) }
            }
            is DeleteAccessibilityCanvasItem -> {
                accessibilityListItems.remove(event.item)
            }
        }
        invalidate()
    }

    fun finishDrawing(): List<AccessibilityItem> {
        setOnTouchListener(null)
        _isDrawing = false
        return accessibilityListItems
            .filter { !it.text.isNullOrEmpty() }
            .sortedBy { it.position }
            .map { AccessibilityItem(it.text ?: "", it.rect) }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (!_isDrawing) return

        accessibilityListItems.forEach {
            canvas?.run {
                drawRect(it.rect.toRect(), if (it.text.isNullOrEmpty()) colorEmpty else colorSelected)
            }
        }

        frame?.let {
            canvas?.run {
                drawRect(it.toRect(), colorEmpty)
            }
        }
    }

    private fun draw(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val item = accessibilityListItems.firstOrNull {
                    it.rect.toRect().contains(
                        event.x.toInt(),
                        event.y.toInt()
                    )
                }

                currentItem = item

                item?.let {
                    listener?.onAccessibilityItemDraw(it)
                    return true
                }
                firstPoint = Point(event.x.toInt(), event.y.toInt())
            }
            MotionEvent.ACTION_MOVE -> {
                frame = RectFrame(firstPoint!!.x, firstPoint!!.y, event.x.toInt(), event.y.toInt())
            }
            MotionEvent.ACTION_UP -> {
                frame?.toRect()?.let {
                    if (it.width() > MIN_SIZE && it.height() > MIN_SIZE)
                        accessibilityListItems.add(
                            AccessibilityCanvasItem(
                                rect = frame!!,
                                position = accessibilityListItems.size + 1
                            )
                        )
                }
                frame = null
            }
        }
        invalidate()
        return true
    }

    companion object {
        private var colorEmpty = Paint().also {
            it.color = Color.RED
            it.strokeWidth = 2f
            it.style = Paint.Style.FILL_AND_STROKE
            it.alpha = 50
        }
        private var colorSelected = Paint().also {
            it.color = Color.BLUE
            it.strokeWidth = 2f
            it.style = Paint.Style.FILL_AND_STROKE
            it.alpha = 50
        }
        private const val MIN_SIZE = 10

    }
}