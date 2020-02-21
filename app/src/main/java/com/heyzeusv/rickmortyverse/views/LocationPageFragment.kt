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
import com.heyzeusv.rickmortyverse.models.LocationNameType
import com.heyzeusv.rickmortyverse.controllers.LocationPageController
import com.heyzeusv.rickmortyverse.databinding.FragmentTypePageBinding
import com.heyzeusv.rickmortyverse.viewmodels.LocationPageViewModel

class LocationPageFragment : Fragment() {

    // DataBinding
    private lateinit var binding : FragmentTypePageBinding

    // EpoxyController
    private val locPageController = LocationPageController()

    // ViewModel
    private val locPageVM : LocationPageViewModel by viewModels()

    // NavController
    private lateinit var navController : NavController

    override fun onCreateView(
            inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_type_page, container, false)
        binding.lifecycleOwner = activity
        binding.pageVM         = locPageVM

        val layoutManager = GridLayoutManager(context, 2)
        locPageController.spanCount = 2
        layoutManager.spanSizeLookup = locPageController.spanSizeLookup
        binding.typePageEpoxy.layoutManager = layoutManager
        binding.typePageEpoxy.adapter       = locPageController.adapter

        locPageVM.locList.observe(viewLifecycleOwner, Observer {
                locList : List<LocationNameType> ->

            locPageController.setData(locList)
        })

        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
    }
}