package com.premsuraj.expensemanager.addedit

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.premsuraj.expensemanager.R
import org.jetbrains.anko.dimen

class CategoryAdapter(private val context: Context, private val entries: List<CategoryListItem>)
    : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onBindViewHolder(holder: CategoryViewHolder?, position: Int) {
        (holder?.view as TextView).text = entries[position].name
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.category_item, parent, false)
        val params = view.layoutParams as ViewGroup.MarginLayoutParams
        val padding = context.dimen(R.dimen.normal_margin) * viewType
        params.leftMargin = padding
        view.layoutParams = params
        if (viewType == 1) (view as TextView).setTypeface(view.typeface, Typeface.BOLD)
        return CategoryViewHolder(context, view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (entries[position].parentId.isEmpty()) 1 else 2
    }
}