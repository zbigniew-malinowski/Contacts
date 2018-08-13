package com.zmalinowski.contactslist.ui.list.view

import android.view.ViewGroup
import android.widget.Button
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.utils.PermissionHelper
import com.zmalinowski.contactslist.widget.singleitemview.ViewHolder

class PermissionsViewHolder(parent: ViewGroup, permissionHelper: PermissionHelper) :
        ViewHolder<Any> by ViewHolder.Static(parent, R.layout.view_permissions) {

    init {
        itemView.findViewById<Button>(R.id.button).setOnClickListener {
            permissionHelper.requestPermission()
        }
    }
}