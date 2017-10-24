package com.premsuraj.expensemanager.addedit

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.premsuraj.expensemanager.R
import com.premsuraj.expensemanager.data.Category
import io.realm.Realm
import kotlinx.android.synthetic.main.category_dialog.*

class CategoryDialog(context: Context, val category: Category,
                     val onSave: (name: String, parentId: String) -> Unit) : Dialog(context) {
    lateinit var adapter: ParentAdapter
    var chosenParent = Category("", "...", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_dialog)

        save.setOnClickListener {
            this@CategoryDialog.dismiss()
            onSave.invoke(name.text.toString(), chosenParent.id)
        }

        name.setText(category.name)

        loadAvailableParents()
    }

    private fun loadAvailableParents() {
        val parents = ArrayList<Category>(Realm.getDefaultInstance().where(Category::class.java)
                .equalTo("parentId", "").findAll())

        parents.add(0, Category("", "...", ""))
        adapter = ParentAdapter(context, parents)
        parent.adapter = adapter
        var position = 0
        for (thisCategory in parents) {
            if (thisCategory.id == category.parentId)
                break
            position++
        }
        parent.setSelection(if (category.parentId.isBlank()) 0 else position)
        parent.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                if (adapterView?.count!! > 0)
                    chosenParent = adapterView.getItemAtPosition(position) as Category
            }
        }
    }

    class ParentAdapter(context: Context, val categories: List<Category>) :
            ArrayAdapter<Category>(context, android.R.layout.simple_spinner_item) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var rowView: View? = convertView
            if (convertView == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                rowView = inflater.inflate(android.R.layout.simple_spinner_item, null, false)
            }

            rowView?.findViewById<TextView>(android.R.id.text1)?.text = categories[position].name
            return rowView!!
        }

        override fun getCount(): Int {
            return categories.size
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var rowView: View? = convertView
            if (convertView == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                rowView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null, false)
            }

            rowView?.findViewById<TextView>(android.R.id.text1)?.text = categories[position].name
            return rowView!!
        }

        override fun getItem(position: Int): Category {
            return categories[position]
        }
    }
}