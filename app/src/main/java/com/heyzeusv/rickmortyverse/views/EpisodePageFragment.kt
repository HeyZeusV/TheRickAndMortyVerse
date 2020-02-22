package com.heyzeusv.rickmortyverse.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode
import com.heyzeusv.rickmortyverse.controllers.EpisodePageController
import com.heyzeusv.rickmortyverse.models.ShortType
import com.heyzeusv.rickmortyverse.viewmodels.EpisodePageViewModel

/**
 *  Sets up more specific details, such as LiveData regarding data and Epoxy adapter.
 */
class EpisodePageFragment : BasePageFragment() {

    // EpoxyController
    private val episPageController = EpisodePageController()

    // ViewModel
    private val episPageVM : EpisodePageViewModel by viewModels()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // attaches ViewModel
        binding.pageVM         = episPageVM

        // GridLayout requires span to be set
        episPageController.spanCount = 2
        layoutManager.spanSizeLookup = episPageController.spanSizeLookup

        // attaches Epoxy Controller to RecyclerView
        binding.typePageEpoxy.layoutManager = layoutManager
        binding.typePageEpoxy.adapter       = episPageController.adapter

        // updates data shown by Epoxy when there is change, which occurs when data is emitted
        episPageVM.dataType.observe(viewLifecycleOwner, Observer {
                episList : List<ShortType> ->

            episPageController.setData(episList as List<EpisodeNameCode>)
        })

        return binding.root
    }
}