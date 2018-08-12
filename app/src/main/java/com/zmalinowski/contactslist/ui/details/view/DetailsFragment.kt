package com.zmalinowski.contactslist.ui.details.view

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
import com.zmalinowski.contactslist.ui.details.DetailsModel
import com.zmalinowski.contactslist.ui.details.DetailsModel.*
import com.zmalinowski.contactslist.ui.details.DetailsViewModel
import com.zmalinowski.contactslist.ui.list.view.PermissionsViewHolder
import com.zmalinowski.contactslist.utils.PermissionHelper
import com.zmalinowski.contactslist.utils.SingleItemAdapter
import com.zmalinowski.contactslist.utils.SingleItemAdapter.*
import com.zmalinowski.contactslist.utils.observe

class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var permissionHelper: PermissionHelper

    private val singleItemAdapter = SingleItemAdapter<DetailsModel> { parent, item ->
        when (item) {
            is Loading -> ViewHolder(parent, R.layout.view_loading)
            is Details -> ContactDetailsViewHolder(parent)
            is GeneralError -> ViewHolder(parent, R.layout.view_error)
            is PermissionDenied -> PermissionsViewHolder(parent, permissionHelper)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.view_details, container, false)

        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = singleItemAdapter
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        permissionHelper = PermissionHelper(activity!!, Manifest.permission.READ_CONTACTS) {
            viewModel.requestData()
        }
        observe(viewModel.details) {
            singleItemAdapter.item = it
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
