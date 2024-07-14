package ru.netology.nmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDB

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
        PostRepositorySQLiteImpl(AppDB.getInstance(application).postDao)
    private val _data = MutableLiveData<List<Post>>()
    val data =repository.getAll()
    val edited = MutableLiveData(empty)
    private val _isEditing = MutableLiveData(false)


    fun save(post: Post) {
        repository.save(post)
        _data.value = repository.getAll().value
    }

    fun edit(post: Post) {
        edited.value = post
        _isEditing.value = true
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
        _data.value = repository.getAll().value
    }

    fun shareById(id: Long) {
        repository.shareById(id)
        _data.value = repository.getAll().value
    }

    fun removeById(id: Long) {
        repository.removeById(id)
        _data.value = repository.getAll().value
    }

    fun cancelEditing() {
        _isEditing.value = false
        edited.value = empty
    }


}