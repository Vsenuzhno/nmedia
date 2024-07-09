package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            val editPostLauncher = registerForActivityResult(EditPostResultContract()) { editedPost ->
                editedPost ?: return@registerForActivityResult
                viewModel.edit(editedPost)
            }
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

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        //  viewModel.edited.observe(this) { post ->
        //      if (post.id == 0L) {
        //          binding.cancelButton.visibility = View.GONE
        //          binding.previewText.visibility = View.GONE
        //          binding.edit.visibility = View.GONE
        //          binding.editorText.visibility = View.GONE
        //          binding.content.setText("")
        //          binding.content.clearFocus()
        //          return@observe
        //      }
        //      binding.cancelButton.visibility = View.VISIBLE
        //      binding.previewText.visibility = View.VISIBLE
        //      binding.edit.visibility = View.VISIBLE
        //      binding.editorText.visibility = View.VISIBLE
        //      with(binding.content) {
        //          requestFocus()
        //          setText(post.content)
        //      }
        //  }

        //  binding.save.setOnClickListener {
        //      with(binding.content) {
        //          if (text.isNullOrBlank()) {
        //              Toast.makeText(
        //                  this@MainActivity,
        //                  "Поле не может быть пустым",
        //                  Toast.LENGTH_SHORT
        //              ).show()
        //              return@setOnClickListener
        //          }

        //         viewModel.changeContent(text.toString())
        //         viewModel.save()
//
        //         setText("")
        //         clearFocus()
        //         AndroidUtil.hideKeyboard(this)
        //     }
        // }

        //binding.cancelButton.setOnClickListener {
        //    viewModel.cancelEditing()
        //    with(binding.content) {
        //        setText("")
        //        clearFocus()
        //        AndroidUtil.hideKeyboard(this)
        //    }
        //}

        //binding.content.addTextChangedListener(object : TextWatcher {
        //    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //        // Не используется
        //    }
//
        //    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //        // Не используется
        //    }
//
        //    override fun afterTextChanged(s: Editable?) {
        //        val firstLine = s?.toString()?.lines()?.firstOrNull() ?: ""
        //        binding.previewText.text = firstLine
        //    }
        //})




        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch()
        }
    }
}