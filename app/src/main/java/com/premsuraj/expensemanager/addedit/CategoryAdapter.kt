package com.premsuraj.expensemanager.addedit

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class CategoryAdapter(private val context: Context, private val entries: List<CategoryListItem>)
    : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onBindViewHolder(holder: CategoryViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoryViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }
}