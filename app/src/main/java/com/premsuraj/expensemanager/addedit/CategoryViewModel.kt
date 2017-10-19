package com.premsuraj.expensemanager.addedit

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.crash.FirebaseCrash
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.premsuraj.expensemanager.Constants
import com.premsuraj.expensemanager.MyApplication
import com.premsuraj.expensemanager.data.Category
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class CategoryViewModel constructor(application: Application) : AndroidViewModel(application) {
    val categories = ArrayList<CategoryListItem>()

    fun getAllCategories(onFetched: (List<CategoryListItem>) -> Unit, onFailed: () -> Unit) {
        if (categories.size != 0) {
            onFetched.invoke(categories)
            return
        }

        refreshCategories(onFetched, onFailed)
    }

    private fun refreshCategories(onFetched: (List<CategoryListItem>) -> Unit, onFailed: () -> Unit) {
        getApplication<MyApplication>().firebaseDb.collection(Constants.DbReferences.CATEGORIES)
                .get()
                .addOnCompleteListener({ task: Task<QuerySnapshot> ->
                    if (task.isSuccessful) {
                        processData(task.result, onFetched, onFailed)
                    } else {
                        onFailed()
                    }
                })
    }

    private fun processData(result: QuerySnapshot?, onFetched: (List<CategoryListItem>) -> Unit, onFailed: () -> Unit) {
        doAsync {
            try {
                val parentCategories = ArrayList<CategoryListItem>()
                val childCategories = ArrayList<CategoryListItem>()

                for (snapshot: DocumentSnapshot in result!!) {
                    val category: Category = snapshot.toObject(Category::class.java)
                    if (category.parentId.isBlank())
                        parentCategories.add(CategoryListItem(snapshot.id, category.name, ""))
                    else
                        childCategories.add(CategoryListItem(snapshot.id, category.name, category.parentId))
                }

                parentCategories.sortBy { categoryListItem -> categoryListItem.name }
                childCategories.sortBy { categoryListItem -> categoryListItem.name }

                for (parentCategory in parentCategories) {
                    categories.add(parentCategory)
                    categories.addAll(childCategories.filter { categoryListItem -> categoryListItem.parentId == parentCategory.id })
                }

                uiThread {
                    onFetched.invoke(categories)
                }
            } catch (ex: Exception) {
                uiThread {
                    FirebaseCrash.report(ex)
                    onFailed.invoke()
                }
            }
        }
    }
}