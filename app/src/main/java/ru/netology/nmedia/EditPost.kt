package ru.netology.nmedia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityEditPostBinding

class EditPost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val initialContent = intent?.getStringExtra(Intent.EXTRA_TEXT) ?: ""
        val postId = intent?.getLongExtra("postId", 0L) ?: 0L
        binding.editContent.setText(initialContent)
        binding.editContent.requestFocus()
        binding.ok.setOnClickListener {
            val intent = Intent()
            intent.putExtra(Intent.EXTRA_TEXT, binding.editContent.text.toString())
            intent.putExtra("postId", postId)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.cancel.setOnClickListener {
            finish()
        }
    }

}