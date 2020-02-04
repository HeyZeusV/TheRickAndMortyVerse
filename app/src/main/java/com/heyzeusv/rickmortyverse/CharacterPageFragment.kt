package com.heyzeusv.rickmortyverse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.heyzeusv.rickmortyverse.databinding.FragmentCharacterPageBinding

class CharacterPageFragment : Fragment() {

    // DataBinding
    private lateinit var binding : FragmentCharacterPageBinding

    // ViewModel
    private val charPageVM : CharacterPageViewModel by viewModels()

    // NavController
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_page, container, false)
        binding.lifecycleOwner = activity
        binding.charPageVM     = charPageVM

        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
    }
}