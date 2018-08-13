package com.zmalinowski.contactslist

import android.Manifest
import android.app.Application
import androidx.fragment.app.Fragment
import com.github.ajalt.timberkt.Timber
import com.zmalinowski.contactslist.core.ContactData
import com.zmalinowski.contactslist.core.ContactsActionExecutor
import com.zmalinowski.contactslist.core.ContactsDataSource
import com.zmalinowski.contactslist.core.State
import com.zmalinowski.contactslist.data.ContactTransformer
import com.zmalinowski.contactslist.data.ContactsDataSourceImpl
import com.zmalinowski.contactslist.data.DataProvider
import com.zmalinowski.contactslist.data.validation.DataAccessValidator
import com.zmalinowski.contactslist.data.validation.ValidatingContactsDataSource
import com.zmalinowski.contactslist.framework.Store
import com.zmalinowski.contactslist.framework.Transformer
import com.zmalinowski.contactslist.ui.details.DetailsModelTransformer
import com.zmalinowski.contactslist.ui.details.DetailsViewModel
import com.zmalinowski.contactslist.ui.list.ListModelTransformer
import com.zmalinowski.contactslist.ui.list.ListViewModel
import com.zmalinowski.contactslist.utils.PermissionHelper
import ir.mirrajabi.rxcontacts.Contact
import ir.mirrajabi.rxcontacts.RxContacts
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module

class ContactsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin(this, listOf(contactsModule))
    }

    private val contactsModule = module {
        viewModel { ListViewModel(get(), get()) }
        viewModel { (contactId: String) ->
            DetailsViewModel(
                    stateStore = get(),
                    transformer = get(parameters = { parametersOf(contactId) })
            )
        }
        single { ListModelTransformer }
        factory { (contactId: String) -> DetailsModelTransformer(contactId) }
        single { Store.create(State.Initial, get<ContactsActionExecutor>()) }
        single { ContactsActionExecutor(get()) }
        single {
            ValidatingContactsDataSource(
                    wrapped = ContactsDataSourceImpl<Contact>(get(), get()),
                    validator = get()
            ) as ContactsDataSource
        }
        single { ContactTransformer as Transformer<Contact, ContactData> }
        single {
            DataAccessValidator.PermissionValidator(
                    context = androidContext(),
                    permission = Manifest.permission.READ_CONTACTS
            ) as DataAccessValidator
        }
        single {
            { RxContacts.fetch(androidContext()) } as DataProvider<Contact>
        }

        factory { (fragment: Fragment, onPermissionGranted: () -> Unit) ->
            PermissionHelper(
                    fragment = fragment,
                    permissionList = *arrayOf(Manifest.permission.READ_CONTACTS),
                    onPermissionsGranted = onPermissionGranted
            )
        }
    }
}