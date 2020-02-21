package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 *  Additional LiveData and required onClick used by all *PageFragments.
 */
abstract class PageViewModel : BaseViewModel() {

    // total number of pages returned by REST call
    protected val _maxPages : MutableLiveData<Int> = MutableLiveData(0)
    val maxPages : LiveData<Int>
        get() = _maxPages

    // current page user is on
    protected val _currentPage : MutableLiveData<Int> = MutableLiveData(-1)
    val currentPage : LiveData<Int>
        get() = _currentPage

    // required onClick for back Button used by *PageFragments
    abstract val backOnClick : View.OnClickListener

    // required onClick for forward Button used by *PageFragments
    abstract val forwardOnClick : View.OnClickListener
}