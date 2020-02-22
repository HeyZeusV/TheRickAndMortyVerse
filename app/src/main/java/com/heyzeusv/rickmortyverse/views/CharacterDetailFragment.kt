package com.heyzeusv.rickmortyverse.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.heyzeusv.rickmortyverse.models.Character
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode
import com.heyzeusv.rickmortyverse.controllers.CharacterDetailController
import com.heyzeusv.rickmortyverse.models.FullType
import com.heyzeusv.rickmortyverse.models.ShortType
import com.heyzeusv.rickmortyverse.viewmodels.CharacterDetailViewModel

/**
 *  Sets up more specific details, such as LiveData regarding data and Epoxy adapter.
 */
class CharacterDetailFragment : BaseDetailFragment() {

    // EpoxyController
    private val charDetailController = CharacterDetailController()

    // ViewModel
    private val charDetailVM : CharacterDetailViewModel by viewModels()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState: Bundle?) : View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // attaches ViewModel
        binding.detailVM = charDetailVM

        // attaches Epoxy Controller to RecyclerView
        binding.typeDetailEpoxy.adapter = charDetailController.adapter

        // retrieves id of Character to be loaded and starts request
        val charId : Int = arguments?.getInt("characterId") ?: 1
        charDetailVM.loadCharacter(charId)

        // updates data shown by Epoxy when there is change, which occurs when data is emitted
        charDetailVM.dataType.observe(viewLifecycleOwner, Observer { character : FullType ->

            charDetailVM.carouselType.observe(viewLifecycleOwner, Observer {
                    episodes : List<ShortType> ->

                charDetailController.setData(
                    character as Character, episodes as List<EpisodeNameCode>)
            })
        })

        return binding.root
    }
}