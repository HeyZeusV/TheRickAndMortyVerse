package com.heyzeusv.rickmortyverse.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.databinding.FragmentTypeDetailBinding

/**
 *  Used by *DetailFragments.
 *
 *  Sets up some of the general DataBinding and NavController.
 */
abstract class BaseDetailFragment : Fragment() {

    // DataBinding
    protected lateinit var binding : FragmentTypeDetailBinding

    // NavController
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState: Bundle?) : View? {

        // sets up layout and owner
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_type_detail, container, false)
        binding.lifecycleOwner = activity

        // layout manager required by epoxy
        binding.typeDetailEpoxy.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
    }
}