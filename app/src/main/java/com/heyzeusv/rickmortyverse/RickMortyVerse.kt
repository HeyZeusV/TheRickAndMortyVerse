package com.heyzeusv.rickmortyverse

import android.app.Application
import timber.log.Timber

/**
 *  Used to instantiate anything 
 */
class RickMortyVerse : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {

            Timber.plant(object : Timber.DebugTree() {

                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, "RMV$tag", message, t)
                }
            })
        }
    }
}