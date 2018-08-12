package com.zmalinowski.contactslist.ui.list.view

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.zmalinowski.contactslist.ui.list.ListItem

class ContactsAdapter : ListAdapter<ListItem, ListItemViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean = oldItem.contactId == newItem.contactId

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean = oldItem == newItem

        }
    }
}