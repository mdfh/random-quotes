package com.assignment.quote;

import com.assignment.quote.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * An [Application] that uses Dagger for Dependency Injection.
 *
 * Created by Faraz on 26/06/20.
 */
open class QuoteApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }
}
