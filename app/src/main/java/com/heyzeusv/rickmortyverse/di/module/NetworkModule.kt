package com.heyzeusv.rickmortyverse.di.module

import com.heyzeusv.rickmortyverse.network.ApiService
import com.heyzeusv.rickmortyverse.utils.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *  Module which provides all required dependencies about network.
 */
@Module
class NetworkModule {

    /**
     *  @return Interceptor that allows logging of requests made
     */
    @Singleton
    @Provides
    fun provideLogging() : HttpLoggingInterceptor {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /**
     *  @param  interceptor adds logging
     *  @return the OkHttpClient object
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor : HttpLoggingInterceptor) : OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            // this fixes SocketTimeoutException occurring even after
            // internet connection is established again
            .protocols(listOf(Protocol.HTTP_1_1))
            .build()
    }

    /**
     *  @param  client OkHttpClient object used to instantiate Retrofit
     *  @return the Retrofit object
     */
    @Singleton
    @Provides
    fun provideRetrofit(client : OkHttpClient) : Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     *  @param  retrofit Retrofit object used to instantiate the service
     *  @return the Api service implementation
     */
    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit) : ApiService {

        return retrofit.create(ApiService::class.java)
    }
}