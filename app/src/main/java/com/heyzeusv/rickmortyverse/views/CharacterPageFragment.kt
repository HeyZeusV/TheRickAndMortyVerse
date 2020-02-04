package com.heyzeusv.rickmortyverse.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.heyzeusv.rickmortyverse.viewmodels.CharacterPageViewModel
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.databinding.FragmentCharacterPageBinding
import com.heyzeusv.rickmortyverse.viewmodels.CharacterPageController

class CharacterPageFragment : Fragment() {

    // DataBinding
    private lateinit var binding : FragmentCharacterPageBinding

    private val charPageController = CharacterPageController()

    // ViewModel
    private val charPageVM : CharacterPageViewModel by viewModels()

    // NavController
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_character_page, container, false)
        binding.lifecycleOwner = activity
        binding.charPageVM     = charPageVM
        binding.characterPageEpoxy.layoutManager = LinearLayoutManager(context)
        binding.characterPageEpoxy.adapter = charPageController.adapter

        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
    }
}