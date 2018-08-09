package com.zmalinowski.contactslist.core

import android.net.Uri

data class ContactData(
        val id : String,
        val displayName : String,
        val photo : Uri,
        val thumbnail : Uri
)