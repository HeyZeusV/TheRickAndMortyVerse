package com.heyzeusv.rickmortyverse.controllers

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.airbnb.epoxy.TypedEpoxyController
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.location
import com.heyzeusv.rickmortyverse.models.LocationNameType

/**
 *  Epoxy Controller used to build models using layouts with "item_view" prefix.
 *
 *  @property List<LocationNameType> primary DataType to be displayed
 */
class LocationPageController : TypedEpoxyController<List<LocationNameType>>() {

    // navigate user to Location selected
    private fun setLocationOnClick(id : Int) : View.OnClickListener {

        val bundle : Bundle = bundleOf("locationId" to id)
        return Navigation.createNavigateOnClickListener(
            R.id.action_locationPage_to_locationDetail, bundle)
    }

    /**
     *  Describes what models should be shown for the current state.
     *
     *  @param data primary data to be shown
     */
    override fun buildModels(data : List<LocationNameType>?) {

        data?.forEach {

            // uses item_view_location.xml as model and sets up data to variables in layout
            location {

                id(it.id)
                location(it)
                onLocClicked(setLocationOnClick(it.id))
            }
        }
    }
}