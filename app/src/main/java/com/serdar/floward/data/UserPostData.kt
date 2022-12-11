package com.serdar.floward.data

@kotlinx.serialization.Serializable
data class UserPostData(
    val userId: Int?,
    val id: Int?,
    val title: String?,
    val body: String?
)