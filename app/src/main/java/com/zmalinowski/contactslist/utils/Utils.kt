package com.zmalinowski.contactslist.utils

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.lifecycle.LiveDataReactiveStreams.fromPublisher
import io.reactivex.BackpressureStrategy.BUFFER
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}

inline fun <reified T : ViewModel> FragmentActivity.withViewModel(viewModelFactory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val vm = getViewModel<T>(viewModelFactory)
    vm.body()
    return vm
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

fun <T> Observable<T>.toLiveData() = fromPublisher(toFlowable(BUFFER))
fun <T> Single<T>.toLiveData() = fromPublisher(toFlowable())

fun <T : Any, R : Any> Observable<T>.mapNotNull(transformer: (T) -> R?): Observable<R> =
        flatMap { t -> transformer(t)?.let { r -> Observable.just(r) } ?: Observable.empty() }

fun <T> ObservableSource<T>.toObservable() = Observable.wrap(this)

@BindingAdapter("uri")
fun ImageView.setUri(uri: Uri?) {
    if (uri != null) {
        setImageURI(uri)
    }else{
        setImageResource(com.zmalinowski.contactslist.R.drawable.ic_launcher_background)
    }
}

fun ViewGroup.inflate(layoutId: Int): View =
        LayoutInflater.from(context).inflate(layoutId, this, false)


fun <T : ViewDataBinding> ViewGroup.inflateDataBinding(layoutId: Int): T =
        DataBindingUtil.inflate(
                LayoutInflater.from(context),
                layoutId, this, false)