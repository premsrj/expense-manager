package com.premsuraj.expensemanager.addedit

import android.support.v7.widget.RecyclerView
import android.view.View
import com.premsuraj.expensemanager.data.Category

class CategoryViewHolder(val onClicked: (category: Category) -> Unit,
                         val onLongClicked: (category: Category) -> Unit,
                         val view: View) : RecyclerView.ViewHolder(view) {
    var category: Category = Category("", "", "")

    init {
        view.setOnClickListener {
            onClicked.invoke(category)
        }
        view.setOnLongClickListener {
            onLongClicked.invoke(category)
            true
        }
    }
}