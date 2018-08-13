package com.zmalinowski.contactslist.ui.details.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.ui.details.DetailsModel
import com.zmalinowski.contactslist.ui.details.DetailsModel.*
import com.zmalinowski.contactslist.ui.details.DetailsViewModel
import com.zmalinowski.contactslist.ui.list.view.PermissionsViewHolder
import com.zmalinowski.contactslist.utils.PermissionHelper
import com.zmalinowski.contactslist.utils.observe
import com.zmalinowski.contactslist.widget.singleitemview.SingleItemAdapter
import com.zmalinowski.contactslist.widget.singleitemview.SingleItemView
import com.zmalinowski.contactslist.widget.singleitemview.ViewHolder.Static
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {

    private val contactId get() = DetailsFragmentArgs.fromBundle(arguments).contactId
    private val detailsViewModel: DetailsViewModel by viewModel { parametersOf(contactId) }
    private val permissionHelper: PermissionHelper by inject { parametersOf(this, detailsViewModel::requestData) }

    private val singleItemAdapter = SingleItemAdapter<DetailsModel> { parent, item ->
        when (item) {
            is Loading -> Static(parent, R.layout.view_loading)
            is Details -> ContactDetailsViewHolder(parent)
            is GeneralError -> Static(parent, R.layout.view_error)
            is PermissionDenied -> PermissionsViewHolder(parent, permissionHelper)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_host, container, false)

        with(view as SingleItemView) {
            adapter = singleItemAdapter
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(detailsViewModel.details) {
            singleItemAdapter.item = it
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        permissionHelper.onActivityResult(requestCode, resultCode, data)
    }

}
