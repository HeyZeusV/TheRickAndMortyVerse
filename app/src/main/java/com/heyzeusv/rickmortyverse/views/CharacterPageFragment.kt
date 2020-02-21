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
import com.heyzeusv.rickmortyverse.viewmodels.CharacterPageViewModel
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.controllers.CharacterPageController
import com.heyzeusv.rickmortyverse.databinding.FragmentTypePageBinding
import com.heyzeusv.rickmortyverse.models.ShortType

class CharacterPageFragment : Fragment() {

    // DataBinding
    private lateinit var binding : FragmentTypePageBinding

    // EpoxyController
    private val charPageController = CharacterPageController()

    // ViewModel
    private val charPageVM : CharacterPageViewModel by viewModels()

    // NavController
    private lateinit var navController : NavController

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_type_page, container, false)
        binding.lifecycleOwner = activity
        binding.pageVM         = charPageVM

        val layoutManager = GridLayoutManager(context, 2)
        charPageController.spanCount = 2
        layoutManager.spanSizeLookup = charPageController.spanSizeLookup
        binding.typePageEpoxy.layoutManager = layoutManager
        binding.typePageEpoxy.adapter       = charPageController.adapter

        charPageVM.dataType.observe(viewLifecycleOwner, Observer {
                charList :  List<ShortType> ->

            charPageController.setData(charList as List<CharacterNameImage>)
        })

        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
    }
}