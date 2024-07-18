package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

interface OnInteractionListener {
    fun onLikeListener(post: Post)
    fun onShareListener(post: Post)
    fun onEdit(post: Post)
    fun onRemove(post: Post)
    fun onVideoClick(videoUrl: String?)
    fun onOpenPost(post: Post)
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Post, PostsAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    class PostViewHolder(
        private val binding: CardPostBinding,
        private val onInteractionListener: OnInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private fun formatCount(count: Int): String {
            return when {
                count < 0 -> "0"
                count < 1000 -> count.toString()
                count < 1_000_000 -> {
                    val thousands = count / 1000
                    val remainder = count % 1000
                    if (remainder > 0) {
                        String.format("%d.%dK", thousands, remainder / 100)
                    } else {
                        String.format("%dK", thousands)
                    }
                }

                else -> {
                    val millions = count / 1_000_000
                    val remainder = count % 1_000_000
                    if (remainder > 0) {
                        String.format("%d.%dM", millions, remainder / 100_000)
                    } else {
                        String.format("%dM", millions)
                    }
                }
            }
        }

        fun bind(post: Post) {
            binding.apply {
                author.text = post.author
                published.text = post.published
                content.text = post.content

                if (!post.video.isNullOrBlank()) {
                    videoContainer.visibility = View.VISIBLE
                    videoContainer.setOnClickListener {
                        onInteractionListener.onVideoClick(post.video)
                    }
                } else {
                    videoContainer.visibility = View.GONE
                }

                ivShares.text = formatCount(post.shares)
                ivLikes.isChecked = post.likedByMe
                ivLikes.text = formatCount(post.likes)
                ivLikes.setOnClickListener {
                    onInteractionListener.onLikeListener(post)
                }

                ivShares.setOnClickListener {
                    onInteractionListener.onShareListener(post)
                }
                root.setOnClickListener {
                    onInteractionListener.onOpenPost(post)
                }
                content.setOnClickListener {
                    onInteractionListener.onOpenPost(post)
                }


                menu.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.menu_post)
                        setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.remove -> {
                                    onInteractionListener.onRemove(post)
                                    true
                                }

                                R.id.edit -> {
                                    onInteractionListener.onEdit(post)
                                    true
                                }

                                else -> false
                            }
                        }
                    }.show()
                }
            }
        }
    }
}