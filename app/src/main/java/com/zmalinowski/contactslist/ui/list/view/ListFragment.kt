package com.zmalinowski.contactslist.ui.list.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.ui.details.view.DetailsFragmentArgs
import com.zmalinowski.contactslist.ui.list.ListModel
import com.zmalinowski.contactslist.ui.list.ListModel.*
import com.zmalinowski.contactslist.ui.list.ListViewModel
import com.zmalinowski.contactslist.utils.PermissionHelper
import com.zmalinowski.contactslist.utils.observe
import com.zmalinowski.contactslist.widget.singleitemview.SingleItemAdapter
import com.zmalinowski.contactslist.widget.singleitemview.SingleItemView
import com.zmalinowski.contactslist.widget.singleitemview.ViewHolder.Static
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ListFragment : Fragment() {

    private val listViewModel: ListViewModel by viewModel()
    private val permissionHelper: PermissionHelper by inject { parametersOf(this, listViewModel::requestData) }

    private val singleItemAdapter = SingleItemAdapter<ListModel> { parent, item ->
        when (item) {
            is Loading -> Static(parent, R.layout.view_loading)
            is Contacts -> ContactListViewHolder(parent, this::onContactSelected)
            is Empty -> Static(parent, R.layout.view_list_empty)
            is GeneralError -> Static(parent, R.layout.view_error)
            is PermissionDenied -> PermissionsViewHolder(parent, permissionHelper)
        }
    }

    private fun onContactSelected(contactId: String) {
        val args = DetailsFragmentArgs.Builder(contactId).build().toBundle()
        findNavController(this).navigate(R.id.action_list_to_details, args)
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
        observe(listViewModel.list) {
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
