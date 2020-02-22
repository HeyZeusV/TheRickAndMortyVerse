package com.heyzeusv.rickmortyverse.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.Location
import com.heyzeusv.rickmortyverse.controllers.LocationDetailController
import com.heyzeusv.rickmortyverse.models.FullType
import com.heyzeusv.rickmortyverse.models.ShortType
import com.heyzeusv.rickmortyverse.viewmodels.LocationDetailViewModel

/**
 *  Sets up more specific details, such as LiveData regarding data and Epoxy adapter.
 */
class LocationDetailFragment : BaseDetailFragment() {

    // EpoxyController
    private val locDetailController = LocationDetailController()

    // ViewModel
    private val locDetailVM : LocationDetailViewModel by viewModels()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // attaches ViewModel
        binding.detailVM = locDetailVM

        // attaches Epoxy Controller to RecyclerView
        binding.typeDetailEpoxy.adapter = locDetailController.adapter

        // retrieves id of Location to be loaded and starts request
        val locId : Int = arguments?.getInt("locationId") ?: 1
        locDetailVM.loadLocation(locId)

        // updates data shown by Epoxy when there is change, which occurs when data is emitted
        locDetailVM.dataType.observe(viewLifecycleOwner, Observer { location : FullType ->

            locDetailVM.carouselType.observe(viewLifecycleOwner, Observer {
                    characters : List<ShortType> ->

                locDetailController.setData(
                    location as Location, characters as List<CharacterNameImage>)
            })
        })

        return binding.root
    }
}