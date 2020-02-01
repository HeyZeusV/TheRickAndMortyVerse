package com.heyzeusv.rickmortyverse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // checks if fragment currently exists
        val currentFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.fragment_character_container)

        // would not be null if activity is destroyed and recreated
        if (currentFragment == null) {

            val charListFragment = CharacterListFragment()

            // create new transaction, add fragment, commit
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_character_container, charListFragment)
                .commit()
        }
    }
}