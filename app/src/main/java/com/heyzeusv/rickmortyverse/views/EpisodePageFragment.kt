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
import androidx.recyclerview.widget.GridLayoutManager
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.databinding.FragmentEpisodePageBinding
import com.heyzeusv.rickmortyverse.models.EpisodePage
import com.heyzeusv.rickmortyverse.viewmodels.EpisodePageController
import com.heyzeusv.rickmortyverse.viewmodels.EpisodePageViewModel

class EpisodePageFragment : Fragment() {

    //DataBinding
    private lateinit var binding : FragmentEpisodePageBinding

    // EpoxyController
    private val episPageController = EpisodePageController()

    // ViewModel
    private val episPageVM : EpisodePageViewModel by viewModels()

    // NavController
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_episode_page, container, false)
        binding.lifecycleOwner = activity
        binding.episPageVM     = episPageVM

        val layoutManager = GridLayoutManager(context, 2)
        episPageController.spanCount = 2
        layoutManager.spanSizeLookup = episPageController.spanSizeLookup
        binding.episodePageEpoxy.layoutManager = layoutManager
        binding.episodePageEpoxy.adapter       = episPageController.adapter

        episPageVM.episPage.observe(viewLifecycleOwner, Observer { episPage : EpisodePage ->

            episPageController.setData(episPage)
        })

        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
    }
}