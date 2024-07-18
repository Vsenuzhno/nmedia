package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity

data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val views: Int = 0,
    val likes: Int = 0,
    val shares: Int = 0,
    val likedByMe: Boolean = false,
    val sharedByMe: Boolean = false,
) {
    fun toDto() =
        Post(id, author, content, published, views, likes, shares, likedByMe, sharedByMe)


    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                id = dto.id,
                author = dto.author,
                content = dto.content,
                published = dto.published,
                views = dto.views,
                likes = dto.likes,
                shares = dto.shares,
                likedByMe = dto.likedByMe,
                sharedByMe = dto.sharedByMe
            )
    }
}