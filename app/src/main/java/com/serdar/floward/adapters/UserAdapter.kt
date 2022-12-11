package com.serdar.floward.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.serdar.floward.data.UserData
import com.serdar.floward.databinding.ItemUserBinding

class UserAdapter(
    private val userList: List<UserData>,
    val onItemClick: (user: UserData) -> Unit): RecyclerView.Adapter<UserAdapter.UserVH>() {

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.bind(userList[position], onItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        return UserVH.from(parent)
    }

    class UserVH(val binding: ItemUserBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserData, onItemClick: (user: UserData) -> Unit) {
            binding.userName = item.name
            binding.postCount = item.posts?.size?.toString().orEmpty()
            Glide.with(binding.root).load(item.thumbnailUrl).circleCrop().into(binding.imgProfile)

            binding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): UserVH {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemUserBinding.inflate(inflater, parent, false)
                return UserVH(binding)
            }
        }
    }
}