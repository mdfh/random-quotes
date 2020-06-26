package com.assignment.quote.models

import com.google.gson.annotations.SerializedName

/**
 * Quote Model
 *
 * Created by Faraz on 26/06/20.
 */
data class Quote
(
    @SerializedName("en")
    val quote : String,

    @SerializedName("author")
    val author : String
)