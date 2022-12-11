package com.serdar.floward.fragments.list

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.serdar.floward.R
import com.serdar.floward.adapters.UserAdapter
import com.serdar.floward.data.UserData
import com.serdar.floward.databinding.FragmentUserListBinding
import com.serdar.floward.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class UserListFragment : BaseFragment<FragmentUserListBinding>(R.layout.fragment_user_list) {

    private val viewModel: UserListViewModel by sharedViewModel()

    override fun init() {
        binding.viewModel = viewModel
        viewModel.getUserList()
        binding.buttonRetry.setOnClickListener {
            viewModel.getUserList()
        }
        observe()
    }

    private fun observe() {
        viewModel.userListData.observe(this) {
            setAdapter(it)
        }
    }

    private fun setAdapter(items: List<UserData>) {
        val adapter = UserAdapter(items) {
            viewModel.setSelectedUser(it)
            findNavController().navigate(
                UserListFragmentDirections.actionUserDetails()
            )
        }
        binding.rvSearchResults.adapter = adapter
        binding.rvSearchResults.layoutManager = LinearLayoutManager(context)
    }
}