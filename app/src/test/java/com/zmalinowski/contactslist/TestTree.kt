package com.zmalinowski.contactslist

import timber.log.Timber

class TestTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        println(message)
        t?.printStackTrace()
    }
}