package com.zmalinowski.contactslist.ui.list.view

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.ui.list.ListItem
import com.zmalinowski.contactslist.utils.inflateDataBinding

class ContactsAdapter : ListAdapter<ListItem, ListItemViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(parent.inflateDataBinding(R.layout.view_list_item))
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.binding.item = getItem(position)
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean = oldItem.contactId == newItem.contactId

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean = oldItem == newItem

        }
    }


}

