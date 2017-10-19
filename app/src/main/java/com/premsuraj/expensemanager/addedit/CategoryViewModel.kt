package com.premsuraj.expensemanager.addedit

import android.arch.lifecycle.AndroidViewModel
import com.premsuraj.expensemanager.MyApplication

class CategoryViewModel constructor(application: MyApplication) : AndroidViewModel(application) {
    fun getAllCategories(onFetched: (List<CategoryListItem>) -> Unit, onFailed: () -> Unit) {

    }
}