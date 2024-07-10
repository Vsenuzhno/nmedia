import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.PostRepository

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "",
            published = "21 мая 18:36",
            likedByMe = false,
            sharedByMe = false,
            video = null
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
            published = "21 мая 18:36",
            likedByMe = false,
            sharedByMe = false,
            video = null
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия\n" +
                    "- помочь встать на путь роста и начать цепочку\n" +
                    "перемен - http://netolo.gy/fyb",
            published = "22 мая 18:36",
            likedByMe = false,
            sharedByMe = false,
            video = null
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия\n" +
                    "- помочь встать на путь роста и начать цепочку\n" +
                    "перемен - http://netolo.gy/fyb",
            published = "23 мая 18:36",
            likedByMe = false,
            sharedByMe = false,
            video = null
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия\n" +
                    "- помочь встать на путь роста и начать цепочку\n" +
                    "перемен - http://netolo.gy/fyb",
            published = "22 мая 18:36",
            likedByMe = false,
            sharedByMe = false,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        ),
    ).reversed()

    private val data = MutableLiveData(posts)
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
    }


    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else {
                if (!it.likedByMe) it.copy(likedByMe = !it.likedByMe, likes = it.likes + 1)
                else it.copy(likedByMe = !it.likedByMe, likes = it.likes - 1)
            }
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(sharedByMe = !it.sharedByMe, shares = it.shares + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }
}
