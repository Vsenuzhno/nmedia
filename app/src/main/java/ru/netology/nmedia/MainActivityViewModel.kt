import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.PostRepository

class MainActivityViewModel : PostRepository {
    private var posts = listOf(
        Post(
            id = 5,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия\n" +
                    "- помочь встать на путь роста и начать цепочку\n" +
                    "перемен - http://netolo.gy/fyb",
            published = "21 мая 18:36",
            likedByMe = false,
            sharedByMe = false
        ),
        Post(
            id = 4,
            author = "Нетология. Университет интернет-профессий",
            content = "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия\n" +
                    "- помочь встать на путь роста и начать цепочку\n" +
                    "перемен - http://netolo.gy/fyb",
            published = "22 мая 18:36",
            likedByMe = false,
            sharedByMe = false
        ),
        Post(
            id = 3,
            author = "Нетология. Университет интернет-профессий",
            content = "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия\n" +
                    "- помочь встать на путь роста и начать цепочку\n" +
                    "перемен - http://netolo.gy/fyb",
            published = "23 мая 18:36",
            likedByMe = false,
            sharedByMe = false
        ),
        Post(
            id = 2,
            author = "Нетология. Университет интернет-профессий",
            content = "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия\n" +
                    "- помочь встать на путь роста и начать цепочку\n" +
                    "перемен - http://netolo.gy/fyb",
            published = "22 мая 18:36",
            likedByMe = false,
            sharedByMe = false
        ),
        Post(
            id = 1,
            author = "Нетология.",
            content = "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия\n" +
                    "- помочь встать на путь роста и начать цепочку\n" +
                    "перемен - http://netolo.gy/fyb",
            published = "22 мая 23:36",
            likedByMe = false,
            sharedByMe = false
        )
    )

    private val data = MutableLiveData(posts)
    override fun getAll(): LiveData<List<Post>> = data

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

}
