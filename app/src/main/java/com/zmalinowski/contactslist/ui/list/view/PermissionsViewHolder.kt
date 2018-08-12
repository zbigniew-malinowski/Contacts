package com.zmalinowski.contactslist.ui.list.view

import android.view.ViewGroup
import android.widget.Button
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.utils.PermissionHelper
import com.zmalinowski.contactslist.utils.SingleItemAdapter.ViewHolder

class PermissionsViewHolder(parent: ViewGroup, permissionHelper: PermissionHelper) :
        ViewHolder<Any>(parent, R.layout.view_permissions) {

    init {
        itemView.findViewById<Button>(R.id.button).setOnClickListener {
            permissionHelper.requestPermission()
        }
    }
}