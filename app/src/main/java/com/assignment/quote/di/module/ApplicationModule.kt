package com.assignment.quote.di.module

import com.assignment.quote.data.pref.AppPrefRepository
import com.assignment.quote.data.pref.PrefRepository
import com.assignment.quote.data.remote.ApiRepository
import com.assignment.quote.data.remote.AppApiRepository
import com.assignment.quote.connectivity.provider.AppConnectivityHelper
import com.assignment.quote.connectivity.provider.ConnectivityHelper
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Dagger module for the app.
 *
 * Created by Faraz on 26/06/20.
 */

@Module
object ApplicationModule {

    @JvmStatic
    @Singleton
    @Provides
    fun providePrefRepository(repo: AppPrefRepository): PrefRepository {
        return repo;
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideApiRepository(repo: AppApiRepository): ApiRepository {
        return repo;
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideConnectivityHelper(repo: AppConnectivityHelper): ConnectivityHelper {
        return repo;
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideGson(): Gson {
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        return gson;
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}
