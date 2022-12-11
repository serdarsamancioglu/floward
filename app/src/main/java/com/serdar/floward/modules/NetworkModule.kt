package com.serdar.floward.modules

import com.serdar.floward.utility.REQUEST_TIMEOUT
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(CIO).config {
            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT
            }
            install(ContentNegotiation) {
                json()
            }
            defaultRequest {
                url("https://my-json-server.typicode.com/SharminSirajudeen/")
            }
        }
    }
}