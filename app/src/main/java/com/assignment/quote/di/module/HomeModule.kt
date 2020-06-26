package com.assignment.quote.di.module

import androidx.lifecycle.ViewModel
import com.assignment.quote.di.ViewModelBuilder
import com.assignment.quote.di.ViewModelKey
import com.assignment.quote.ui.home.HomeFragment
import com.assignment.quote.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Dagger module for the Home screen.
 *
 * Created by Faraz on 26/06/20.
 */
@Module
abstract class HomeModule {

    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    internal abstract fun homeFragment(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindViewModel(viewmodel: HomeViewModel): ViewModel
}
