package com.premsuraj.expensemanager.addedit

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.premsuraj.expensemanager.R
import kotlinx.android.synthetic.main.activity_category_picker.*
import kotlinx.android.synthetic.main.content_category_picker.*
import org.jetbrains.anko.indeterminateProgressDialog


class CategoryPicker : AppCompatActivity() {
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var viewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_picker)
        setSupportActionBar(toolbar)

        val progress = indeterminateProgressDialog("")
        progress.show()

        categoryList.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        categoryList.layoutManager = layoutManager

        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        viewModel.getAllCategories({ list ->
            categoryList.adapter = CategoryAdapter(this@CategoryPicker, list)
            progress.dismiss()
        }, {
            Snackbar.make(viewContainer, "Failed to fetch categories", Snackbar.LENGTH_LONG).show()
            progress.dismiss()
        })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
