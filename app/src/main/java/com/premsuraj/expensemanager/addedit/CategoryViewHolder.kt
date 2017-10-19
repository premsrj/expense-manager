package com.premsuraj.expensemanager.addedit

import android.support.v7.widget.RecyclerView
import android.view.View
import com.premsuraj.expensemanager.data.Category

class CategoryViewHolder(var onClicked: (category: Category) -> Unit, val view: View) : RecyclerView.ViewHolder(view) {
    var category: Category = Category("", "", "")

    init {
        view.setOnClickListener {
            onClicked.invoke(category)
        }
    }
}