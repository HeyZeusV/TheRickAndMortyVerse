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
import com.heyzeusv.rickmortyverse.models.Location
import com.heyzeusv.rickmortyverse.controllers.LocationDetailController
import com.heyzeusv.rickmortyverse.databinding.FragmentTypeDetailBinding
import com.heyzeusv.rickmortyverse.models.FullType
import com.heyzeusv.rickmortyverse.models.ShortType
import com.heyzeusv.rickmortyverse.viewmodels.LocationDetailViewModel

class LocationDetailFragment : Fragment() {

    // DataBinding
    private lateinit var binding : FragmentTypeDetailBinding

    // EpoxyController
    private val locDetailController = LocationDetailController()

    // ViewModel
    private val locDetailVM : LocationDetailViewModel by viewModels()

    // NavController
    private lateinit var navController : NavController

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_type_detail, container, false)
        binding.lifecycleOwner = activity
        binding.detailVM       = locDetailVM

        binding.typeDetailEpoxy.layoutManager = LinearLayoutManager(context)
        binding.typeDetailEpoxy.adapter       = locDetailController.adapter

        val locId : Int = arguments?.getInt("locationId") ?: 1
        locDetailVM.loadLocation(locId)

        locDetailVM.dataType.observe(viewLifecycleOwner, Observer { location : FullType ->

            locDetailVM.carouselType.observe(viewLifecycleOwner, Observer {
                    characters : List<ShortType> ->

                locDetailController.setData(
                    location as Location, characters as List<CharacterNameImage>)
            })
        })

        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
    }
}