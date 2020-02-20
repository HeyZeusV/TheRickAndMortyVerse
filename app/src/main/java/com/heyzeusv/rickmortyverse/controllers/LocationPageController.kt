package com.heyzeusv.rickmortyverse.controllers

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.airbnb.epoxy.TypedEpoxyController
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.location
import com.heyzeusv.rickmortyverse.models.LocationNameType

class LocationPageController : TypedEpoxyController<List<LocationNameType>>() {

    private fun setOnClick(id : Int) : View.OnClickListener {

        val bundle : Bundle = bundleOf("locationId" to id)
        return Navigation.createNavigateOnClickListener(
            R.id.action_locationPage_to_locationDetail, bundle)
    }

    override fun buildModels(data : List<LocationNameType>?) {

        data?.forEach {

            location {

                id(it.id)
                location(it)
                onLocClicked(setOnClick(it.id))
            }
        }
    }
}