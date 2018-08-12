package com.zmalinowski.contactslist.ui.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.ui.list.ListViewModel

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var contactsAdapter: ContactsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = contactsAdapter
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
    }

}