package com.example.comicsforall

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.view.ViewCompat
import com.example.comicsforall.model.AccessibilityItem

open class AccessibilityCanvas(context: Context, attr: AttributeSet?) :
    View(context, attr), ExtendedAccessibility {

    private var firstPoint: Point? = null
    private var rect: Rect? = null
    private var isDrawing = false
    private var listener: AccessibilityItemListener? = null
    private var currentItem: AccessibilityItem? = null
    private var accessibilityListItems = mutableListOf<AccessibilityItem>()

    override fun setAccessibilityContentList(list: List<AccessibilityItem>) {
        accessibilityListItems = list.toMutableList()
        ViewCompat.setAccessibilityDelegate(this, AccessibilityHelper(this, list))
    }

    fun openDrawCanvas(listener: AccessibilityItemListener) {
        this.listener = listener
        isDrawing = true
        setOnTouchListener { view, event ->
            view.performClick()
            draw(event)
        }
    }

    fun sendEvent(event: AccessibilityEvent) {
        when (event) {
            is AddAccessibilityItem -> {
                accessibilityListItems.add(event.item)
                currentItem?.let { accessibilityListItems.remove(it) }
            }
            is DeleteAccessibilityItem -> {
                accessibilityListItems.remove(event.item)
            }
        }
        invalidate()
    }

    fun finishDrawing(): List<AccessibilityItem> {
        setOnTouchListener(null)
        isDrawing = false
        return accessibilityListItems
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (!isDrawing) return

        accessibilityListItems.forEach {
            canvas?.run {
                drawRect(it.rect, if(it.text.isNullOrEmpty()) colorEmpty else colorSelected)
            }
        }

        rect?.let {
            canvas?.run {
                drawRect(it, colorEmpty)
            }
        }
    }

    private fun draw(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val item = accessibilityListItems.firstOrNull { it.rect.contains(event.x.toInt(), event.y.toInt()) }

                currentItem = item

                item?.let {
                    listener?.onAccessibilityItemDraw(it)
                    return true
                }
                firstPoint = Point(event.x.toInt(), event.y.toInt())
            }
            MotionEvent.ACTION_MOVE -> {
                rect = Rect(firstPoint!!.x, firstPoint!!.y, event.x.toInt(), event.y.toInt())
            }
            MotionEvent.ACTION_UP -> {
                rect?.let {
                    if (it.width() > MIN_SIZE && it.height() > MIN_SIZE)
                        accessibilityListItems.add(
                            AccessibilityItem(
                                rect = it,
                                position = accessibilityListItems.size + 1
                            )
                        )
                }
                rect = null
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