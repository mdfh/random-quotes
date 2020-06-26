package com.assignment.quote.connectivity.provider

import android.content.Context
import com.assignment.quote.connectivity.base.ConnectivityProvider
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides the dependency of connection provider.
 *
 * Created by Faraz on 26/06/20.
 */

interface ConnectivityHelper {
    fun isConnectedToInternet(): Boolean
}

@Singleton
class AppConnectivityHelper @Inject
constructor(
    context: Context
) : ConnectivityHelper {
    private val provider: ConnectivityProvider

    init {
        provider = ConnectivityProvider.createProvider(context)
    }

    override fun isConnectedToInternet(): Boolean {
        return provider.getNetworkState().hasInternet();
    }

    private fun ConnectivityProvider.NetworkState.hasInternet(): Boolean {
        return (this as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
    }

}