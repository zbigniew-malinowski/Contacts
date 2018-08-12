package com.zmalinowski.contactslist.ui.list.view

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.ui.list.ListModel
import com.zmalinowski.contactslist.utils.SingleItemAdapter

class ContactListViewHolder(parent: ViewGroup) : SingleItemAdapter.ViewHolder<ListModel>(parent, R.layout.view_list) {

    private val contactsAdapter = ContactsAdapter()

    init {
        with(itemView as RecyclerView) {
            layoutManager = LinearLayoutManager(itemView.context)
            adapter = contactsAdapter
        }
    }

    override fun bind(model: ListModel) {
        with(model as ListModel.Contacts) {
            contactsAdapter.submitList(model.list)
        }
    }
}

