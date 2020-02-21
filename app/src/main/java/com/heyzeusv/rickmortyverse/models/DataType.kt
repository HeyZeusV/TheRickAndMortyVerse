package com.heyzeusv.rickmortyverse.models

/**
 *  Used in Page/Detail ViewModels in order to have 1 LiveData variable in parent ViewModel, rather
 *  than each Specific ViewModel having its own LiveData variable representing Data.
 */
abstract class DataType

/**
 *  Represents data class containing all the details (Character, Episode, Location).
 */
abstract class FullType : DataType()

/**
 *  Represents data class containing minimal details for ItemViews/Carousel.
 */
abstract class ShortType : DataType()