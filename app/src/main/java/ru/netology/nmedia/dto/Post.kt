package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val views: Int = 0,
    val likes: Int = 0,
    val shares: Int = 0,
    val likedByMe: Boolean = false,
    val sharedByMe: Boolean = false,
    val video: String? = null
)