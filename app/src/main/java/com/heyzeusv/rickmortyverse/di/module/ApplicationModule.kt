package com.heyzeusv.rickmortyverse.di.module

import com.heyzeusv.rickmortyverse.di.BaseUrl
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl() : String {

        return "https://rickandmortyapi.com/api/"
    }
}