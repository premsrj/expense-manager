package com.premsuraj.expensemanager.home

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.premsuraj.expensemanager.Constants
import com.premsuraj.expensemanager.R
import com.premsuraj.expensemanager.addedit.AddEditActivity

/**
 * A placeholder fragment containing a simple view.
 */
class HomeActivityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val inflatedView = inflater.inflate(R.layout.fragment_home, container, false)

        var fab = inflatedView.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { _ ->
            val intent = Intent(activity, AddEditActivity::class.java)
            intent.putExtra(Constants.Ids.TRANSACTION, "DPRvxloogNzE3E22ujhP")
            startActivity(intent)
        }
        return inflatedView
    }
}
