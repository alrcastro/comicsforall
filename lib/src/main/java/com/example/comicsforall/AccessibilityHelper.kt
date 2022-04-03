package com.example.comicsforall

import android.os.Bundle
import android.view.View
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.customview.widget.ExploreByTouchHelper
import com.example.comicsforall.model.AccessibilityItem

class AccessibilityHelper(
    view: View,
    private val list: List<AccessibilityItem>
) : ExploreByTouchHelper(view) {

    override fun getVirtualViewAt(x: Float, y: Float): Int {
        list.forEachIndexed { index, item ->
            if (item.frame.toRect().contains(x.toInt(), y.toInt())) {
                return index
            }
        }
        return -1
    }

    override fun getVisibleVirtualViews(virtualViewIds: MutableList<Int>?) {
        virtualViewIds?.addAll(0 until list.count())
    }

    @Suppress("DEPRECATION")
    override fun onPopulateNodeForVirtualView(
        virtualViewId: Int,
        node: AccessibilityNodeInfoCompat
    ) {
        val item = list[virtualViewId]
        node.className = AccessibilityCanvas::class.simpleName
        node.contentDescription = item.text
        node.setBoundsInParent(item.frame.toRect())
    }

    override fun onPerformActionForVirtualView(
        virtualViewId: Int,
        action: Int,
        arguments: Bundle?
    ): Boolean = false
}