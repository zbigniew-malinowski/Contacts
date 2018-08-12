package com.zmalinowski.contactslist.ui.list.view

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.ui.list.ListModel
import com.zmalinowski.contactslist.ui.list.ListModel.*
import com.zmalinowski.contactslist.ui.list.ListViewModel
import com.zmalinowski.contactslist.utils.PermissionHelper
import com.zmalinowski.contactslist.utils.SingleItemAdapter
import com.zmalinowski.contactslist.utils.SingleItemAdapter.ViewHolder
import com.zmalinowski.contactslist.utils.observe

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var permissionHelper: PermissionHelper

    private val singleItemAdapter = SingleItemAdapter<ListModel> { parent, item ->
        when (item) {
            is Loading -> ViewHolder(parent, R.layout.view_loading)
            is Contacts -> ContactListViewHolder(parent)
            is Empty -> ViewHolder(parent, R.layout.view_list_empty)
            is GeneralError -> ViewHolder(parent, R.layout.view_error)
            is PermissionDenied -> PermissionsViewHolder(parent, permissionHelper)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.view_list, container, false)

        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = singleItemAdapter
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        permissionHelper = PermissionHelper(activity!!, Manifest.permission.READ_CONTACTS) {
            viewModel.requestData()
        }
        observe(viewModel.list) {
            singleItemAdapter.item = it
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
