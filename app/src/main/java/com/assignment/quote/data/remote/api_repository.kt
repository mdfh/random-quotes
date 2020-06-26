package com.assignment.quote.data.remote

import com.assignment.quote.data.DataResult
import com.assignment.quote.data.remote.services.QuoteService
import com.assignment.quote.models.Quote
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This is the API repository which holds the instance of retrofit service. All the API calls will
 * be made in this class and then it will be validated in safeApiCall()
 *
 * Created by Faraz on 26/06/20.
 */


interface ApiRepository {
    suspend fun getRandomQuote(page: Int = 1): DataResult<Quote>
}

@Singleton
class AppApiRepository @Inject
constructor(
    private val quoteService: QuoteService
) : ApiRepository {
    override suspend fun getRandomQuote(page: Int): DataResult<Quote> {
        return safeApiCall(call = { quoteService.getRandomQuotes() });
    }

    /**
     * Single method to check whether API calls was successful or not.
     */
    private suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): DataResult<T> {
        return try {
            val myResp = call.invoke()
            if (myResp.isSuccessful) {
                DataResult.Success(myResp.body()!!)
            } else {

                /*
                   Here we can handle errors for different error codes.
                   For example, 401 for unauthorized access
                 */

                DataResult.Error(Exception(myResp.errorBody()?.string() ?: "Something goes wrong"))
            }

        } catch (e: Exception) {
            DataResult.Error(Exception(e.message ?: "Internet error runs"))
        }
    }
}