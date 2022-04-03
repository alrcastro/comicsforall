package com.example.comicsforall.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessibilityItem(
    @SerialName("text")
    val text: String,
    @SerialName("frame")
    val frame: RectFrame
)