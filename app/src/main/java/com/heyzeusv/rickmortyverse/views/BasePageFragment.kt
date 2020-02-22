package com.heyzeusv.rickmortyverse.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.databinding.FragmentTypePageBinding

/**
 *  Used by *PageFragments.
 *
 *  Sets up some of the general DataBinding and NavController.
 */
abstract class BasePageFragment : Fragment() {

    // DataBinding
    protected lateinit var binding : FragmentTypePageBinding

    // NavController
    private lateinit var navController : NavController

    // GridLayoutManager used by Epoxy
    protected val layoutManager = GridLayoutManager(context, 2)

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {

        // sets up layout and owner
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_type_page, container, false)
        binding.lifecycleOwner = activity

        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
    }

}