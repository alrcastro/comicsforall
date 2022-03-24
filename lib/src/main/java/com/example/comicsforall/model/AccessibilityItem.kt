package com.example.comicsforall.model

import android.graphics.Rect

data class AccessibilityItem(
    val text: String? = null,
    val rect: Rect,
    val position: Int? = null
)