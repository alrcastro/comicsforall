package com.example.comicsforall

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.ViewCompat
import com.example.comicsforall.model.AccessibilityItem

interface ExtendedAccessibility {
    fun setAccessibilityContentList(list: List<AccessibilityItem>)
}