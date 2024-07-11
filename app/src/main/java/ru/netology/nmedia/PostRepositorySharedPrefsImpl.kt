import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.Post
import ru.netology.nmedia.PostRepository

class PostRepositorySharedPrefsImpl(context:Context) : PostRepository {
    val gson = Gson()
    private val prefs=context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val typeToken = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)
    init {
        prefs.getString(key, null)?.let {
            posts = gson.fromJson(it, typeToken)
            nextId = posts.maxOfOrNull { it.id } ?.inc()?:1
            data.value = posts
        }
    }



        override fun getAll(): LiveData<List<Post>> = data

        override fun save(post: Post) {
            if (post.id == 0L) {
                posts = listOf(
                    post.copy(
                        id = nextId++,
                        author = "Me",
                        likedByMe = false,
                        published = "now",
                        video = null
                    )
                ) + posts
            } else {
                posts = posts.map {
                    if (it.id == post.id) {
                        it.copy(content = post.content)
                    } else {
                        it
                    }
                }
            }
            data.value = posts
            sync()
        }


        override fun likeById(id: Long) {
            posts = posts.map {
                if (it.id != id) it else {
                    if (!it.likedByMe) it.copy(likedByMe = !it.likedByMe, likes = it.likes + 1)
                    else it.copy(likedByMe = !it.likedByMe, likes = it.likes - 1)
                }
            }
            data.value = posts
            sync()
        }

        override fun shareById(id: Long) {
            posts = posts.map {
                if (it.id != id) it else it.copy(
                    sharedByMe = !it.sharedByMe,
                    shares = it.shares + 1
                )
            }
            data.value = posts
            sync()
        }

        override fun removeById(id: Long) {
            posts = posts.filter { it.id != id }
            data.value = posts
            sync()
        }

        private fun sync() {
            prefs.edit().apply {
                putString(key, gson.toJson(posts))
                apply()
        }
    }

}

