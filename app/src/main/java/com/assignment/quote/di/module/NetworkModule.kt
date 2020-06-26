package com.assignment.quote.di.module

import android.content.Context
import com.assignment.quote.BuildConfig
import com.assignment.quote.data.remote.services.QuoteService
import com.google.gson.Gson
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Module which provides all required dependencies about network
 *
 * Created by Faraz on 26/06/20.
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    internal fun provideQuoteInterface(retrofit: Retrofit): QuoteService {
        return retrofit.create(QuoteService::class.java)
    }

    @Provides
    @Singleton
    internal fun getHttpClient(
        context: Context
    ): OkHttpClient {

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder().build()
            chain.proceed(request)
        }
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)

        return httpClient.build()
    }
}
