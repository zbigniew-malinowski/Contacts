package com.zmalinowski.contactslist.contacts.ui

import android.view.ViewGroup
import android.widget.Button
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.utils.PermissionHelper
import com.zmalinowski.contactslist.widget.screenview.ScreenHolder

class PermissionsScreenHolder(parent: ViewGroup, permissionHelper: PermissionHelper) :
        ScreenHolder<Any> by ScreenHolder.Static(parent, R.layout.view_permissions) {

    init {
        itemView.findViewById<Button>(R.id.button).setOnClickListener {
            permissionHelper.requestPermission()
        }
    }
}