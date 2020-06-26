package com.assignment.quote.data.remote.services

import com.assignment.quote.data.remote.ApiEndPoint
import com.assignment.quote.models.Quote
import retrofit2.Response
import retrofit2.http.GET

/**
 * This file holds all the services used by the retrofit with HTTP methods and API endpoints.
 * Basically we define here the API call like request parameters, response, api endpoint and the HTTP method
 *
 * Created by Faraz on 26/06/20.
 */
interface QuoteService {

    @GET(ApiEndPoint.ENDPOINT_RANDOM_QUOTES)
    suspend fun getRandomQuotes(): Response<Quote>
}