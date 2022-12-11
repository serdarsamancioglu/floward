package com.serdar.floward.repository

import com.serdar.floward.api.UserApi
import com.serdar.floward.data.UserData
import com.serdar.floward.data.UserPostData
import com.serdar.floward.entity.ApiCallState
import com.serdar.floward.entity.ErrorHandler

class UserRepository(private val userApi: UserApi) {

    suspend fun getUsers(): ApiCallState<List<UserData>?> {
        return try {
            ApiCallState.Success(userApi.getUserList())
        } catch (e: Exception) {
            ApiCallState.Error(ErrorHandler.handleException(e))
        }
    }

    suspend fun getPosts(): ApiCallState<List<UserPostData>?> {
        return try {
            ApiCallState.Success(userApi.getUserPosts())
        } catch (e: Exception) {
            ApiCallState.Error(ErrorHandler.handleException(e))
        }
    }
}