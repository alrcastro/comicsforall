package com.example.comicsforall

import com.example.comicsforall.model.AccessibilityCanvasItem

sealed interface AccessibilityCanvasEvent

class AddAccessibilityCanvasItem(val item: AccessibilityCanvasItem) : AccessibilityCanvasEvent

class DeleteAccessibilityCanvasItem(val item: AccessibilityCanvasItem) : AccessibilityCanvasEvent
