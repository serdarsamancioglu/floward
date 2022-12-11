package com.serdar.floward.entity

import com.serdar.floward.utility.ERROR_NETWORK_COMMON
import com.serdar.floward.utility.ERROR_NETWORK_NOT_AVAILABLE
import com.serdar.floward.utility.ERROR_NETWORK_TIMEOUT
import com.serdar.floward.utility.ERROR_UNKNOWN
import io.ktor.client.plugins.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*

object ErrorHandler {
    fun handleException(e: Any): String {
        return when (e) {
            is IOException -> ERROR_NETWORK_NOT_AVAILABLE
            is ResponseException -> {
                if (e.response.status == HttpStatusCode.GatewayTimeout) ERROR_NETWORK_TIMEOUT
                else ERROR_NETWORK_COMMON
            }
            else -> ERROR_UNKNOWN
        }
    }
}