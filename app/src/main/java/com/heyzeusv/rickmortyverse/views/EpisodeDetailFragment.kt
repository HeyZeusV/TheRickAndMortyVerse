package com.heyzeusv.rickmortyverse.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.Episode
import com.heyzeusv.rickmortyverse.controllers.EpisodeDetailController
import com.heyzeusv.rickmortyverse.databinding.FragmentTypeDetailBinding
import com.heyzeusv.rickmortyverse.models.FullType
import com.heyzeusv.rickmortyverse.models.ShortType
import com.heyzeusv.rickmortyverse.viewmodels.EpisodeDetailViewModel

class EpisodeDetailFragment : Fragment() {

    // DataBinding
    private lateinit var binding : FragmentTypeDetailBinding

    // EpoxyController
    private val episDetailController = EpisodeDetailController()

    // ViewModel
    private val episDetailVM : EpisodeDetailViewModel by viewModels()

    // NavController
    private lateinit var navController : NavController

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_type_detail, container, false)
        binding.lifecycleOwner = activity
        binding.detailVM       = episDetailVM

        binding.typeDetailEpoxy.layoutManager = LinearLayoutManager(context)
        binding.typeDetailEpoxy.adapter       = episDetailController.adapter

        val episId : Int = arguments?.getInt("episodeId") ?: 1
        episDetailVM.loadEpisode(episId)

        episDetailVM.dataType.observe(viewLifecycleOwner, Observer { episode : FullType ->

            episDetailVM.carouselType.observe(viewLifecycleOwner, Observer {
                    characters : List<ShortType> ->

                episDetailController.setData(
                    episode as Episode, characters as List<CharacterNameImage>)
            })
        })

        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
    }
}