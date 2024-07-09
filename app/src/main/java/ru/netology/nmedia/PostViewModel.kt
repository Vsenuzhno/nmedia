package ru.netology.nmedia

import PostRepositoryInMemoryImpl
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    sharedByMe = false,
    likes = 0,
    shares = 0,
    published = ""
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    private val _data = MutableLiveData<List<Post>>()
    val data: LiveData<List<Post>>
        get() = _data
    val edited = MutableLiveData(empty)
    private val _isEditing = MutableLiveData(false)
    val isEditing: LiveData<Boolean>
        get() = _isEditing

    init {
        _data.value = repository.getAll().value
    }

    fun save() {
        edited.value?.let {
            repository.save(it)
            _data.value = repository.getAll().value // Обновляем данные
        }
        edited.value = empty
        _isEditing.value = false
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
        _data.value = repository.getAll().value // Обновляем данные
    }

    fun shareById(id: Long) {
        repository.shareById(id)
        _data.value = repository.getAll().value // Обновляем данные
    }

    fun removeById(id: Long) {
        repository.removeById(id)
        _data.value = repository.getAll().value // Обновляем данные
    }

    fun cancelEditing() {
        _isEditing.value = false
        edited.value = empty
    }
}