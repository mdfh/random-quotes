package com.assignment.quote.connectivity

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.assignment.quote.connectivity.base.ConnectivityProvider.NetworkState
import com.assignment.quote.connectivity.base.ConnectivityProvider.NetworkState.ConnectedState.Connected
import com.assignment.quote.connectivity.base.ConnectivityProvider.NetworkState.NotConnectedState
import com.assignment.quote.connectivity.base.AppConnectivityProviderBase

/**
 * As getActiveNetworkInfo() was deprecated in Android 10. We have to use NetworkCallbacks instead
 * for apps that target API 29 and higher.
 *
 * Created by Faraz on 26/06/20.
 */
@RequiresApi(Build.VERSION_CODES.N)
class AppConnectivityProvider(private val cm: ConnectivityManager) :
    AppConnectivityProviderBase() {

    private val networkCallback = ConnectivityCallback()

    override fun subscribe() {
        cm.registerDefaultNetworkCallback(networkCallback)
    }

    override fun unsubscribe() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    override fun getNetworkState(): NetworkState {
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return if (capabilities != null) {
            Connected(capabilities)
        } else {
            NotConnectedState
        }
    }

    private inner class ConnectivityCallback : NetworkCallback() {

        override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
            dispatchChange(Connected(capabilities))
        }

        override fun onLost(network: Network) {
            dispatchChange(NotConnectedState)
        }
    }
}