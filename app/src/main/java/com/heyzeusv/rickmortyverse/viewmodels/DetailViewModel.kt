package com.heyzeusv.rickmortyverse.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heyzeusv.rickmortyverse.models.FullType
import com.heyzeusv.rickmortyverse.models.ShortType

/**
 *  Addition LiveData and variable used by all *DetailFragments.
 */
abstract class DetailViewModel : BaseViewModel() {

    // DataType to be shown in full detail
    protected val _dataType : MutableLiveData<FullType> = MutableLiveData()
    val dataType : LiveData<FullType>
        get() = _dataType

    // DataType to be shown in Epoxy Carousel
    protected val _carouselType : MutableLiveData<List<ShortType>> = MutableLiveData()
    val carouselType : LiveData<List<ShortType>>
        get() = _carouselType

    // id of loaded DataType, to be used in case of error
    protected var typeId = 1
}