import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post

class MainActivityViewModel : ViewModel() {
    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post>
        get() = _post

    init {
        _post.value = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия\n" +
                    "- помочь встать на путь роста и начать цепочку\n" +
                    "перемен - http://netolo.gy/fyb",
            published = "21 мая 18:36",
            likedByMe = false,
            shared = false
        )
    }

    fun toggleLike() {
        val currentPost = _post.value ?: return
        currentPost.likedByMe = !currentPost.likedByMe
        if (currentPost.likedByMe) {
            currentPost.likes++
        } else {
            currentPost.likes--
        }
        _post.value = currentPost
    }

    fun toggleShare() {
        val currentPost = _post.value ?: return
        currentPost.shared = !currentPost.shared
        if (currentPost.shared) {
            currentPost.shares++
        }
        _post.value = currentPost
    }
}
