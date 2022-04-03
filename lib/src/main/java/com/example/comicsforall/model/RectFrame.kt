package com.example.comicsforall.model

import android.graphics.Rect
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RectFrame(
    @SerialName("left")
    val left: Int,
    @SerialName("top")
    val top: Int,
    @SerialName("right")
    val right: Int,
    @SerialName("bottom")
    val bottom: Int
) {
    fun toRect(): Rect =
        Rect(left, top, right, bottom)
}