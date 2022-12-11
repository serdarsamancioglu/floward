package com.serdar.floward

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.serdar.floward.data.UserData
import com.serdar.floward.data.UserPostData
import com.serdar.floward.entity.ApiCallState
import com.serdar.floward.fragments.list.UserListViewModel
import com.serdar.floward.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {

    private lateinit var viewModel: UserListViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var userRepository: UserRepository

    @Suppress("DEPRECATION")
    @ExperimentalCoroutinesApi
    val dispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        viewModel = UserListViewModel(userRepository)
    }

    @Test
    fun testUserListSuccess() {
        val userList = listOf(
            UserData(1, "John"),
            UserData(2, "Tom")
        )

        val postList = listOf(
            UserPostData(1, 2, "title 1", "body 1"),
            UserPostData(2, 3, "title 2", "body 2")
        )

        coEvery { userRepository.getUsers() } returns ApiCallState.Success(userList)
        coEvery { userRepository.getPosts() } returns ApiCallState.Success(postList)

        val expected = listOf(
            UserData(1, "John", posts = listOf(postList[0])),
            UserData(2, "Tom", posts = listOf(postList[1]))
        )

        viewModel.getUserList(dispatcher)
        assertEquals(expected, viewModel.userListData.value)
        assert(viewModel.noResultVisible.value == false)
        assert(viewModel.showProgress.value == false)
        assert(viewModel.errorVisible.value == false)
    }

    @Test
    fun testUserListFailure() {
        coEvery { userRepository.getUsers() } returns ApiCallState.Error("an error occured")

        viewModel.getUserList(dispatcher)
        assertNull(viewModel.userListData.value)
        assert(viewModel.showProgress.value == false)
        assert(viewModel.errorVisible.value == true)
        assertEquals("an error occured", viewModel.errorMessage.value)
    }
}