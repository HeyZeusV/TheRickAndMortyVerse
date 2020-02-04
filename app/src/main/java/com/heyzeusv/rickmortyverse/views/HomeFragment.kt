package com.heyzeusv.rickmortyverse.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.heyzeusv.rickmortyverse.viewmodels.HomeViewModel
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    // DataBinding
    private lateinit var binding : FragmentHomeBinding

    // ViewModel
    private val homeVM : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = activity
        binding.homeVM         = homeVM

        return binding.root
    }

}