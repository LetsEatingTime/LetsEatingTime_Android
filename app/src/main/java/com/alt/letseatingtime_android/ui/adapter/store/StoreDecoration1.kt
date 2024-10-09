package com.alt.letseatingtime_android.ui.adapter.store

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class StoreDecoration1(
    private val startPadding: Int = 0,
    private val endPadding: Int = 0,
    val bottomPadding: Int = 0,
    val topPadding: Int = 0,
    private val lastIndex: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        when (position) {
            0 -> {
                outRect.right = endPadding
                outRect.left = 27
                outRect.top = topPadding
                outRect.bottom = bottomPadding
            }
            lastIndex-1 -> {
                outRect.left = startPadding
                outRect.right = 27
                outRect.top = topPadding
                outRect.bottom = bottomPadding
            }
            else -> {
                outRect.left = startPadding
                outRect.right = endPadding
                outRect.top = topPadding
                outRect.bottom = bottomPadding
            }
        }
    }
}