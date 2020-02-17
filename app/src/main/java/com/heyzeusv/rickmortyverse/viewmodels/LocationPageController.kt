package com.heyzeusv.rickmortyverse.viewmodels

import com.airbnb.epoxy.TypedEpoxyController
import com.heyzeusv.rickmortyverse.location
import com.heyzeusv.rickmortyverse.models.LocationPage

class LocationPageController : TypedEpoxyController<LocationPage>() {

    override fun buildModels(data: LocationPage?) {

        data?.results?.forEach {

            location {

                id(it.id)
                location(it)
            }
        }
    }
}