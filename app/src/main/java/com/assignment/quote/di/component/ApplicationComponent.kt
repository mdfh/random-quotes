package com.assignment.quote.di.component

import android.content.Context
import com.assignment.quote.QuoteApp
import com.assignment.quote.di.module.ApplicationModule
import com.assignment.quote.di.module.HomeModule
import com.assignment.quote.di.module.NetworkModule
import com.assignment.quote.di.module.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Main component for the application.
 * Created by Faraz on 26/06/20.
 */
@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        HomeModule::class,
        NetworkModule::class,
        RepositoryModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<QuoteApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}
