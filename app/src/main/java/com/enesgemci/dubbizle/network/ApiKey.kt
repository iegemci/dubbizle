package com.enesgemci.dubbizle.network

import com.enesgemci.dubbizle.R
import com.enesgemci.dubbizle.core.App
import javax.inject.Inject

class ApiKey @Inject constructor(val app: App) {

    val apiKey: String
        get() = app.getString(R.string.api_key)
}