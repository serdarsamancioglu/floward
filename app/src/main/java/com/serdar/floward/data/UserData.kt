package com.serdar.floward.data

@kotlinx.serialization.Serializable
data class UserData(
    val userId: Int?,
    val name: String?,
    val url: String? = null,
    val thumbnailUrl: String? = null,
    val albumId: Int? = null,
    var posts: List<UserPostData>? = null
)