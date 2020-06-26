package com.assignment.quote.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.quote.Event
import com.assignment.quote.data.DataResult
import com.assignment.quote.data.repository.QuoteRepository
import com.assignment.quote.models.Quote
import com.assignment.quote.connectivity.provider.ConnectivityHelper
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Faraz on 26/06/20.
 */
class HomeViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository,
    private val provider: ConnectivityHelper
) : ViewModel() {
    private val TAG = HomeViewModel::class.simpleName

    protected val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    protected val _dataError = MutableLiveData<Event<Unit>>()
    val dataError: LiveData<Event<Unit>> = _dataError

    protected val _noInternetError = MutableLiveData<Event<Unit>>()
    val noInternet: LiveData<Event<Unit>> = _noInternetError

    val quote: MutableLiveData<Quote> by lazy {
        MutableLiveData<Quote>()
    }

    init {
        if (provider.isConnectedToInternet())
            fetchQuote()
        else
            fetchQuoteOffline()
    }


    /**
     * This method fetches the quote from the server.
     */

    private fun fetchQuote() {
        viewModelScope.launch {
            try {
                _dataLoading.value = true
                when (val quoteResult = quoteRepository.getQuoteFromServer()) {
                    is DataResult.Success -> {
                        quote.value = quoteResult.data
                        Log.d(TAG, "Success")
                        _dataLoading.value = false
                    }
                    is DataResult.Error -> {
                        Log.d(TAG, "Failure")
                        _dataError.value = Event(Unit)
                        _dataLoading.value = false
                    }
                }
            } catch (e: Exception) {
                _dataError.value = Event(Unit)
                _dataLoading.value = false
            }
        }
    }

    /**
     * If user is offline, then get the quote from the preferences.
     *
     * Incase of fresh install preference is empty. In this case show error dialog.
     */
    private fun fetchQuoteOffline() {
        viewModelScope.launch {
            var data = quoteRepository.getQuoteWhenOffline()
            if (data != null) {
                quote.value = data
            } else
                _noInternetError.value = Event(Unit)
        }
    }

    /**
     * Refresh the quote if user is online; otherwise show internet error dialog
     */
    fun onRefreshQuoteButtonClick() {
        if (provider.isConnectedToInternet())
            fetchQuote()
        else
            _noInternetError.value = Event(Unit)

    }
}