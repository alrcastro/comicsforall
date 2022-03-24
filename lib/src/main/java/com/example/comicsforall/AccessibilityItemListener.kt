package com.example.comicsforall

import com.example.comicsforall.model.AccessibilityItem

interface AccessibilityItemListener {
    fun onAccessibilityItemDraw(item: AccessibilityItem)
}