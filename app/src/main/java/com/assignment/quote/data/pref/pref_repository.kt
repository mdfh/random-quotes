package com.assignment.quote.data.pref;

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.assignment.quote.models.Quote
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This repository manages the shared preferences.
 *
 * Created by Faraz on 26/06/20.
 */

interface PrefRepository {
    fun setQuote(quote: String)
    fun getQuote(): String?

    fun setAuthor(author: String)
    fun getAuthor(): String?
}

@Singleton
class AppPrefRepository @Inject
constructor(
    private val context: Context
) : PrefRepository {
    private final val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)

    override fun setQuote(quote: String) {
        sharedPreference.edit().putString(_DATA_QUOTE, quote).apply()

    }

    override fun getQuote(): String? {
        return sharedPreference.getString(_DATA_QUOTE, null) ?: "";
    }

    override fun setAuthor(author: String) {
        sharedPreference.edit().putString(_DATA_AUTHOR, author).apply()
    }

    override fun getAuthor(): String? {
        return sharedPreference.getString(_DATA_AUTHOR, null);
    }

    private val _DATA_QUOTE = "data_quote"
    private val _DATA_AUTHOR = "data_author"
}