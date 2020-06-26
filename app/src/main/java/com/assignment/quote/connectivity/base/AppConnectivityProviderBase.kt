package com.assignment.quote.connectivity.base

import android.os.Handler
import android.os.Looper
import com.assignment.quote.connectivity.base.ConnectivityProvider.ConnectivityStateListener
import com.assignment.quote.connectivity.base.ConnectivityProvider.NetworkState

/**
 * Base class for connectivity providers.
 *
 * Created by Faraz on 26/06/20.
 */
abstract class AppConnectivityProviderBase :
    ConnectivityProvider {
    private val handler = Handler(Looper.getMainLooper())
    private val listeners = mutableSetOf<ConnectivityStateListener>()
    private var subscribed = false

    private fun verifySubscription() {
        if (!subscribed && listeners.isNotEmpty()) {
            subscribe()
            subscribed = true
        } else if (subscribed && listeners.isEmpty()) {
            unsubscribe()
            subscribed = false
        }
    }

    protected fun dispatchChange(state: NetworkState) {
        handler.post {
            for (listener in listeners) {
                listener.onStateChange(state)
            }
        }
    }

    protected abstract fun subscribe()
    protected abstract fun unsubscribe()
}