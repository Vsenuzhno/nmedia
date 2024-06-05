package ru.netology.nmedia

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия\n" +
                    "- помочь встать на путь роста и начать цепочку\n" +
                    "перемен - http://netolo.gy/fyb",
            published = "21 мая 18:36",
            likedByMe = false,
            shared = false
        )


        with(binding) {
            author.text = post.author
            published.text = post.published
            text.text = post.content
            if (post.likedByMe) {
                ivLikes?.setImageResource(R.drawable.baseline_favorite_red_24)
            }
            likes?.text = post.likes.toString()

            ivLikes?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                ivLikes.setImageResource(
                    if (post.likedByMe) R.drawable.baseline_favorite_red_24 else R.drawable.baseline_favorite_border_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likes?.text = formatCount(post.likes)
            }

            ivShares?.setOnClickListener {
                post.shared = !post.shared
                if (post.shared) {
                    post.shares++
                    share?.text = post.shares.toString()
                } else if (post.shares > 0) {
                    post.shares++
                    share?.text = formatCount(post.shares)
                }
            }





            enableEdgeToEdge()


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
    fun formatCount(count: Int): String {
        if (count < 0) {
            return "0"
        }
        if (count < 1000) {
            return count.toString()
        }
        val thousands = count / 1000
        val remainder = count % 1000
        if (thousands < 100) {
            return if (remainder > 0) {
                String.format("%d.%dK", thousands, remainder / 100)
            } else {
                String.format("%dK", thousands)
            }
        }
        val millions = thousands / 100
        val remainder2 = thousands % 100
        return if (remainder2 > 0) {
            String.format("%d.%dM", millions, remainder2)
        } else {
            String.format("%dM", millions)
        }
    }

}
