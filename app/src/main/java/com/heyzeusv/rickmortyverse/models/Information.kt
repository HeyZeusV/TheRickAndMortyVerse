package com.heyzeusv.rickmortyverse.models

/*
 *  All classes here correspond to responses from API calls. All variable names are the same as
 *  they appear in API. Should be pretty clear on what each mean...
 *  [Info Schema](https://rickandmortyapi.com/documentation/#info-and-pagination)
 */
data class Information(

    val count : Int,
    val pages : Int,
    val next  : String,
    val prev  : String
)