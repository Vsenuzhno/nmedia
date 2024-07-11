package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            val newPost = Post(
                id = 0L,
                author = "Me",
                content = viewModel.edited.value?.content ?: "",
                published = "now",
            )
            viewModel.save(newPost)
        }

        val editPostLauncher = registerForActivityResult(EditPostResultContract()) { editedPost ->
            editedPost ?: return@registerForActivityResult
            viewModel.save(editedPost)
        }

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                editPostLauncher.launch(post)
            }

            override fun onLikeListener(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShareListener(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }

            override fun onVideoClick(videoUrl: String?) {
                videoUrl ?: return
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                startActivity(intent)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch()
        }
    }
}
