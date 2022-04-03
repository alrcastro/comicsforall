package com.example.comicsforall.util

import android.widget.ImageView
import androidx.core.view.ViewCompat
import com.example.comicsforall.AccessibilityHelper
import com.example.comicsforall.model.AccessibilityItem

fun ImageView.setAccessibilityList(list: List<AccessibilityItem>) {
    ViewCompat.setAccessibilityDelegate(this, AccessibilityHelper(this, list))
}
