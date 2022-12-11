package com.serdar.floward.api

import com.serdar.floward.data.UserData
import com.serdar.floward.data.UserPostData

interface UserApi {
    suspend fun getUserList(): List<UserData>?
    suspend fun getUserPosts(): List<UserPostData>?
}