package com.serdar.floward.api

import com.serdar.floward.data.UserData
import com.serdar.floward.data.UserPostData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class UserApiImpl(private val httpClient: HttpClient): UserApi {

    override suspend fun getUserList(): List<UserData>? {
        return httpClient.get{
            url {
                encodedPath = "test_resources/users"
            }
        }.body()
    }

    override suspend fun getUserPosts(): List<UserPostData>? {
        return httpClient.get{
            url {
                encodedPath = "test_resources/posts"
            }
        }.body()
    }
}