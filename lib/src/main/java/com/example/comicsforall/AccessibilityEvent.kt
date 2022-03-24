package com.example.comicsforall

import com.example.comicsforall.model.AccessibilityItem

sealed interface AccessibilityEvent

class AddAccessibilityItem(val item: AccessibilityItem) : AccessibilityEvent

class DeleteAccessibilityItem(val item: AccessibilityItem) : AccessibilityEvent
