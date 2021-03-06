package com.premsuraj.expensemanager.addedit

import android.app.ProgressDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.premsuraj.expensemanager.Constants
import com.premsuraj.expensemanager.R
import com.premsuraj.expensemanager.data.Category
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

        val progress = indeterminateProgressDialog("Fetching Categories")
        progress.show()

        categoryList.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        categoryList.layoutManager = layoutManager

        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        refreshList(progress)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        newCategory.setOnClickListener {
            val newCategory = Category()
            newCategory.name = ""
            newCategory.id = ""
            addEditCategory(newCategory)
        }
    }

    private fun refreshList(progress: ProgressDialog, force: Boolean = false) {
        viewModel.getAllCategories(force, { list ->
            categoryList.adapter = CategoryAdapter(this@CategoryPicker, list, { category ->
                onCategoryClicked(category)
            }, { category ->
                onCategoryLongClicked(category)
            })
            progress.dismiss()
        }, {
            Snackbar.make(viewContainer, "Failed to fetch categories", Snackbar.LENGTH_LONG).show()
            progress.dismiss()
        })
    }

    fun onCategoryClicked(category: Category) {
        val progress = indeterminateProgressDialog("Wait...")
        progress.show()
        viewModel.getFullyQualifiedName(category, { name ->
            progress.dismiss()
            val intent = Intent()
            intent.putExtra(Constants.KEYS.CATEGORY_NAME, name)
            intent.putExtra(Constants.KEYS.CATEGORY_ID, category.id)
            intent.putExtra(Constants.KEYS.PARENT_ID, category.parentId)
            setResult(101, intent)
            this@CategoryPicker.finish()
        })
    }

    private fun onCategoryLongClicked(category: Category) {
        addEditCategory(category)
    }

    private fun addEditCategory(category: Category) {
        CategoryDialog(this, category, { name, parentId ->

            val progress = indeterminateProgressDialog("Fetching Categories")
            progress.show()
            viewModel.saveCategory(category, name, parentId, {
                refreshList(progress, true)
            })
        }).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
