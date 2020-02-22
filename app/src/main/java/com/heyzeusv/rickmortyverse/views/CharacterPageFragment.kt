package com.heyzeusv.rickmortyverse.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.heyzeusv.rickmortyverse.viewmodels.CharacterPageViewModel
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.controllers.CharacterPageController
import com.heyzeusv.rickmortyverse.models.ShortType

/**
 *  Sets up more specific details, such as LiveData regarding data and Epoxy adapter.
 */
class CharacterPageFragment : BasePageFragment() {

    // EpoxyController
    private val charPageController = CharacterPageController()

    // ViewModel
    private val charPageVM : CharacterPageViewModel by viewModels()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // attaches ViewModel
        binding.pageVM = charPageVM

        // GridLayout requires span to be set
        charPageController.spanCount = 2
        layoutManager.spanSizeLookup = charPageController.spanSizeLookup

        // attaches Epoxy Controller to RecyclerView
        binding.typePageEpoxy.layoutManager = layoutManager
        binding.typePageEpoxy.adapter       = charPageController.adapter

        // updates data shown by Epoxy when there is change, which occurs when data is emitted
        charPageVM.dataType.observe(viewLifecycleOwner, Observer {
                charList :  List<ShortType> ->

            charPageController.setData(charList as List<CharacterNameImage>)
        })

        return binding.root
    }
}