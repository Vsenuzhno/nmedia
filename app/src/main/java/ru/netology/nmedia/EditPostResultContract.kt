package ru.netology.nmedia

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class EditPostResultContract : ActivityResultContract<Post, Post?>() {
    override fun createIntent(context: Context, input: Post): Intent {
        return Intent(context, EditPost::class.java)
            .putExtra(Intent.EXTRA_TEXT, input.content)
            .putExtra("postId", input.id)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Post? {
        if (resultCode != Activity.RESULT_OK) return null
        val content = intent?.getStringExtra(Intent.EXTRA_TEXT) ?: return null
        val postId = intent.getLongExtra("postId", 0L)
        return Post(postId, content, "/Users/andreioskilko/Netology/nmedia/app/src/main/java/ru/netology/nmedia/EditPost.kt"
            ,"31",4,3, likedByMe = false, sharedByMe = false)
    }
}