package ru.netology.nmedia.dao

import ru.netology.nmedia.Post

interface PostDao {
    fun getAll(): List<Post>
    fun removeById(id: Long)
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun save(post: Post): Post
}