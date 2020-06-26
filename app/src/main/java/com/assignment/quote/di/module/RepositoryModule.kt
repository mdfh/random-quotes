package com.assignment.quote.di.module

import com.assignment.quote.data.pref.AppPrefRepository
import com.assignment.quote.data.remote.AppApiRepository
import com.assignment.quote.data.repository.QuoteRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Module which provides all required dependencies about the repositories
 *
 * Created by Faraz on 26/06/20.
 */

@Module
class RepositoryModule {
    @Singleton
    @Provides
    internal fun provideMovieRepository(
        pref: AppPrefRepository,
        apiRepository: AppApiRepository
    ): QuoteRepository {
        return QuoteRepository(pref, apiRepository);
    }
}