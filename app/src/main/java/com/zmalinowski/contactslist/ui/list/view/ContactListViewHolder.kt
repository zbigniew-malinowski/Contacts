package com.zmalinowski.contactslist.ui.list.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.ui.list.ListModel
import com.zmalinowski.contactslist.widget.singleitemview.ViewHolder

class ContactListViewHolder(parent: ViewGroup, onContactSelected : (String) -> Unit) : ViewHolder<ListModel> by ViewHolder.Static(parent, R.layout.view_list) {

    private val contactsAdapter = ContactsAdapter(onContactSelected)

    init {
        with(itemView as RecyclerView) {
            adapter = contactsAdapter
        }
    }

    override fun bind(model: ListModel) {
        with(model as ListModel.Contacts) {
            contactsAdapter.submitList(model.list)
        }
    }
}

