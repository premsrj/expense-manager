package com.premsuraj.expensemanager.addedit

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.google.firebase.crash.FirebaseCrash
import com.premsuraj.expensemanager.data.Category
import io.realm.Realm

class CategoryViewModel constructor(application: Application) : AndroidViewModel(application) {
    var categories = ArrayList<Category>()

    fun getAllCategories(forced: Boolean = false, onFetched: (List<Category>) -> Unit, onFailed: () -> Unit) {
        if (!forced && categories.size != 0) {
            onFetched.invoke(categories)
            return
        }

        refreshCategories(onFetched, onFailed)
    }

    private fun refreshCategories(onFetched: (List<Category>) -> Unit, onFailed: () -> Unit) {
        val realm = Realm.getDefaultInstance()
        val allCategories = realm.where(Category::class.java).findAllSorted("name")
        processData(allCategories, onFetched, onFailed)
    }

    private fun processData(result: List<Category>, onFetched: (List<Category>) -> Unit, onFailed: () -> Unit) {
        try {
            categories.clear()
            val parentCategories = ArrayList<Category>()
            val childCategories = ArrayList<Category>()

            for (category: Category in result) {
                if (category.parentId.isBlank())
                    parentCategories.add(Category(category.id, category.name, ""))
                else
                    childCategories.add(Category(category.id, category.name, category.parentId))
            }

            for (parentCategory in parentCategories) {
                categories.add(parentCategory)
                categories.addAll(childCategories.filter { categoryListItem -> categoryListItem.parentId == parentCategory.id })
            }

            onFetched.invoke(categories)
        } catch (ex: Exception) {
            FirebaseCrash.report(ex)
            onFailed.invoke()
        }
    }

    fun getFullyQualifiedName(category: Category, onFetched: (name: String) -> Unit) {
        if (category.parentId.isBlank()) {
            onFetched.invoke(category.name)
            return
        }
        val realm = Realm.getDefaultInstance()
        val parentCategory = realm.where(Category::class.java)
                .equalTo("id", category.parentId).findFirst()
        try {
            if (parentCategory == null)
                onFetched.invoke(category.name)
            else
                onFetched.invoke(parentCategory.name + ":" + category.name)
        } catch (ex: Exception) {
            FirebaseCrash.report(ex)
            onFetched.invoke(category.name)
        }
    }

    fun saveCategory(category: Category, name: String, parentId: String, onSaved: () -> Unit) {
        Realm.getDefaultInstance().executeTransaction { realm ->
            category.name = name
            category.parentId = parentId
            if (category.id.isBlank()) category.id = "" + category.hashCode()
            realm.insertOrUpdate(category)
            onSaved.invoke()
        }
    }
}