package com.heyzeusv.rickmortyverse.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.heyzeusv.rickmortyverse.models.LocationNameType
import com.heyzeusv.rickmortyverse.controllers.LocationPageController
import com.heyzeusv.rickmortyverse.models.ShortType
import com.heyzeusv.rickmortyverse.viewmodels.LocationPageViewModel

/**
 *  Sets up more specific details, such as LiveData regarding data and Epoxy adapter.
 */
class LocationPageFragment : BasePageFragment() {

    // EpoxyController
    private val locPageController = LocationPageController()

    // ViewModel
    private val locPageVM : LocationPageViewModel by viewModels()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // attaches ViewModel
        binding.pageVM         = locPageVM

        // GridLayout requires span to be set
        locPageController.spanCount = 2
        layoutManager.spanSizeLookup = locPageController.spanSizeLookup

        // attaches Epoxy Controller to RecyclerView
        binding.typePageEpoxy.layoutManager = layoutManager
        binding.typePageEpoxy.adapter       = locPageController.adapter

        // updates data shown by Epoxy when there is change, which occurs when data is emitted
        locPageVM.dataType.observe(viewLifecycleOwner, Observer {
                locList : List<ShortType> ->

            locPageController.setData(locList as List<LocationNameType>)
        })

        return binding.root
    }
}