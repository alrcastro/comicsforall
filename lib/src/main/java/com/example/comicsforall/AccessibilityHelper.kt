package com.example.comicsforall

import android.os.Bundle
import android.view.View
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.customview.widget.ExploreByTouchHelper
import com.example.comicsforall.model.AccessibilityItem

class AccessibilityHelper(
    view: View,
    list: List<AccessibilityItem>
) : ExploreByTouchHelper(view) {

    private val filteredList: List<AccessibilityItem> =
        list.filter { !it.text.isNullOrEmpty() }.sortedBy { it.position }

    override fun getVirtualViewAt(x: Float, y: Float): Int {
        filteredList.forEachIndexed { index, item ->
            if (item.rect.contains(x.toInt(), y.toInt())) {
                return index
            }
        }
        return -1
    }

    override fun getVisibleVirtualViews(virtualViewIds: MutableList<Int>?) {
        virtualViewIds?.addAll(0 until filteredList.count())
    }

    @Suppress("DEPRECATION")
    override fun onPopulateNodeForVirtualView(
        virtualViewId: Int,
        node: AccessibilityNodeInfoCompat
    ) {
        val item = filteredList[virtualViewId]
        node.className = AccessibilityCanvas::class.simpleName
        node.contentDescription = item.text
        node.setBoundsInParent(item.rect)
    }

    override fun onPerformActionForVirtualView(
        virtualViewId: Int,
        action: Int,
        arguments: Bundle?
    ): Boolean = false
}