package com.heyzeusv.rickmortyverse.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.Episode
import com.heyzeusv.rickmortyverse.controllers.EpisodeDetailController
import com.heyzeusv.rickmortyverse.models.FullType
import com.heyzeusv.rickmortyverse.models.ShortType
import com.heyzeusv.rickmortyverse.viewmodels.EpisodeDetailViewModel

/**
 *  Sets up more specific details, such as LiveData regarding data and Epoxy adapter.
 */
class EpisodeDetailFragment : BaseDetailFragment() {

    // EpoxyController
    private val episDetailController = EpisodeDetailController()

    // ViewModel
    private val episDetailVM : EpisodeDetailViewModel by viewModels()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // attaches ViewModel
        binding.detailVM = episDetailVM

        // attaches Epoxy Controller to RecyclerView
        binding.typeDetailEpoxy.adapter = episDetailController.adapter

        // retrieves id of Episode to be loaded and starts request
        val episId : Int = arguments?.getInt("episodeId") ?: 1
        episDetailVM.loadEpisode(episId)

        // updates data shown by Epoxy when there is change, which occurs when data is emitted
        episDetailVM.dataType.observe(viewLifecycleOwner, Observer { episode : FullType ->

            episDetailVM.carouselType.observe(viewLifecycleOwner, Observer {
                    characters : List<ShortType> ->

                episDetailController.setData(
                    episode as Episode, characters as List<CharacterNameImage>)
            })
        })

        return binding.root
    }
}