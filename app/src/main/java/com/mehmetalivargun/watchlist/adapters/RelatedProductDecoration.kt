package com.mehmetalivargun.watchlist.adapters
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mehmetalivargun.watchlist.R


class RelatedProductDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spacing = view.context.resources.getDimensionPixelSize(R.dimen.item_product_spacing)
        val position = parent.getChildAdapterPosition(view)

        val isAtFirst = position == 0
        outRect.right = spacing
        outRect.bottom = spacing
        if (isAtFirst) outRect.left = spacing

    }
}