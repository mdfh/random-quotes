package com.assignment.quote.data.repository

import android.util.Log
import com.assignment.quote.data.DataResult
import com.assignment.quote.data.pref.PrefRepository
import com.assignment.quote.data.remote.ApiRepository
import com.assignment.quote.models.Quote
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class acts as an interface between viewmodels and repositories ( API, SharedPreferences or Database)
 *
 * The job of this repository is to manage all the tasks related to quotes.
 *
 * Created by Faraz on 26/06/20.
 */
@Singleton
class QuoteRepository @Inject
constructor(
    private val mPreference: PrefRepository,
    private val mApiHelper: ApiRepository
) {
    private val TAG = QuoteRepository::class.simpleName

    /**
     * Gets the quote from the server and then save it in preferences for offline use
     */
    suspend fun getQuoteFromServer(): DataResult<Quote> {
        // Remote first
        val quoteResult = mApiHelper.getRandomQuote()
        when (quoteResult) {
            is Error -> Log.d(TAG, "Remote data source fetch failed")
            is DataResult.Success -> {
                mPreference.setQuote(quoteResult.data.quote)
                mPreference.setAuthor(quoteResult.data.author)
            }
            else -> throw IllegalStateException()
        }
        return quoteResult
    }

    /**
     * Gets the quote from shared preferences
     */
    fun getQuoteWhenOffline(): Quote? {
        val quote = mPreference.getQuote()
        val author = mPreference.getAuthor()

        if (quote != null && author != null) {
            return Quote(quote, author)
        }
        return null;
    }
}