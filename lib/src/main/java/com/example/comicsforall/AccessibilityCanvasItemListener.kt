package com.example.comicsforall

import com.example.comicsforall.model.AccessibilityCanvasItem

interface AccessibilityCanvasItemListener {
    fun onAccessibilityItemDraw(item: AccessibilityCanvasItem)
}