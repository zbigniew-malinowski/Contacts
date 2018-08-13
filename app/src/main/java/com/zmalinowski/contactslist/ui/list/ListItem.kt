package com.zmalinowski.contactslist.ui.list

import android.net.Uri

data class ListItem(
        val contactId : String,
        val displayName: String,
        val thumbnail: Uri?
)