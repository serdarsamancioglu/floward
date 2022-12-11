package com.serdar.floward.fragments.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serdar.floward.data.UserData
import com.serdar.floward.data.UserPostData
import com.serdar.floward.entity.ApiCallState
import com.serdar.floward.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserListViewModel(private val repository: UserRepository): ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val errorVisible = MutableLiveData(false)
    val noResultVisible = MutableLiveData(false)
    val userListData = MutableLiveData<List<UserData>>()
    var selectedUser: UserData? = null
        private set

    val showProgress = MutableLiveData(false)

    //dispatcher param added for unit test TestCoroutineDispatcher
    fun getUserList(dispatcher: CoroutineDispatcher? = null) {
        showProgress.postValue(true)
        viewModelScope.launch(dispatcher ?: Dispatchers.IO) {
            val deferredUsers = async { repository.getUsers() }
            val deferredPosts = async { repository.getPosts() }
            when (val userResponse = deferredUsers.await()) {
                is ApiCallState.Success -> {
                    val postResponse = deferredPosts.await()
                    onUserListSuccess(userResponse.item, postResponse)
                }
                is ApiCallState.Error -> {
                    onUserListError(userResponse.message)
                }
            }
        }
    }

    private fun onUserListSuccess(userResponse: List<UserData>?, postResponse: ApiCallState<List<UserPostData>?>) {
        showProgress.postValue(false)
        noResultVisible.postValue(userResponse.isNullOrEmpty())
        userListData.postValue(
            if (postResponse is ApiCallState.Success) {
                userResponse?.onEach { user ->
                    user.posts = postResponse.item?.filter { it.userId == user.userId }
                }
            } else {
                userResponse
            }
        )

    }

    private fun onUserListError(message: String) {
        showProgress.postValue(false)
        errorMessage.postValue(message)
        errorVisible.postValue(true)
    }

    fun setSelectedUser(user: UserData) {
        selectedUser = user
    }
}