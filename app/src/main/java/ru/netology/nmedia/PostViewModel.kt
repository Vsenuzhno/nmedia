package ru.netology.nmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDB
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    sharedByMe = false,
    likes = 0,
    shares = 0,
    published = "",
    video = null

)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository =
        PostRepositoryImpl(AppDB.getInstance(context = application).postDao())

    val data = repository.getAll()
    val edited = MutableLiveData(empty)


    fun save(post: Post) {
        repository.save(post)
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
            edited.value = it.copy(content = text)
        }
    }

    fun likeById(id: Long) {
        repository.likeById(id)
    }

     fun shareById(id: Long) {
         repository.shareById(id)
     }

    fun removeById(id: Long) {
        repository.removeById(id)
    }

    fun cancelEditing() {
        edited.value = empty
    }


}