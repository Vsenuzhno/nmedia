package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityEditPostBinding

class EditPost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val initialContent = intent?.getStringExtra(Intent.EXTRA_TEXT) ?: ""
        binding.editContent.setText(initialContent)

        binding.editContent.requestFocus()
        binding.ok.setOnClickListener {
            val intent = Intent()
            intent.putExtra(Intent.EXTRA_TEXT, binding.editContent.text.toString())
            intent.putExtra("postId", intent.getLongExtra("postId", 0L))
            setResult(RESULT_OK, intent)
            finish()
        }
        binding.cancel.setOnClickListener {
            finish()
        }
    }
}