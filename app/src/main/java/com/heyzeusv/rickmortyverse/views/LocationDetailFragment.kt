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
import com.heyzeusv.rickmortyverse.databinding.FragmentLocationDetailBinding
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.Location
import com.heyzeusv.rickmortyverse.viewmodels.LocationDetailController
import com.heyzeusv.rickmortyverse.viewmodels.LocationDetailViewModel

class LocationDetailFragment : Fragment() {

    // DataBinding
    private lateinit var binding : FragmentLocationDetailBinding

    // EpoxyController
    private val locDetailController = LocationDetailController()

    // ViewModel
    private val locDetailVM : LocationDetailViewModel by viewModels()

    // NavController
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_location_detail, container, false)
        binding.lifecycleOwner = activity
        binding.locDetailVM    = locDetailVM

        binding.locationDetailEpoxy.layoutManager = LinearLayoutManager(context)
        binding.locationDetailEpoxy.adapter       = locDetailController.adapter

        val locId : Int = arguments?.getInt("locationId") ?: 1
        locDetailVM.loadLocation(locId)

        locDetailVM.location.observe(viewLifecycleOwner, Observer { location : Location ->

            locDetailVM.locCharacters.observe(viewLifecycleOwner, Observer {
                    characters : List<CharacterNameImage> ->

                locDetailController.setData(location, characters)
            })
        })

        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
    }
}