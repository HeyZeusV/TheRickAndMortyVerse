package com.heyzeusv.rickmortyverse.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.heyzeusv.rickmortyverse.R

class MainActivity : AppCompatActivity() {

    // Navigation
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this,
            R.id.fragment
        )
    }
}