package ru.netology.nmedia

import MainActivityViewModel
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.activity.viewModels
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.post.observe(this, { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                text.text = post.content
                ivLikes.setImageResource(if (post.likedByMe) R.drawable.baseline_favorite_red_24 else R.drawable.baseline_favorite_border_24)
                likes.text = formatCount(post.likes)
                share.text = formatCount(post.shares)

                ivLikes.setOnClickListener { viewModel.toggleLike() }
                ivShares.setOnClickListener { viewModel.toggleShare() }
            }
        })

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun formatCount(count: Int): String {
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
