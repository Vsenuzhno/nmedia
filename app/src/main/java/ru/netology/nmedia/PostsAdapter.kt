package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit

class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
) : ListAdapter<Post, PostsAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener)
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
        private val onLikeListener: OnLikeListener,
        private val onShareListener: OnShareListener

    ) : RecyclerView.ViewHolder(binding.root) {

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

        fun bind(post: Post) {
            binding.apply {
                author.text = post.author
                published.text = post.published
                text.text = post.content

                likes.text = formatCount(post.likes)
                share.text = formatCount(post.shares)

                ivLikes.setImageResource(if (post.likedByMe) R.drawable.baseline_favorite_red_24 else R.drawable.baseline_favorite_border_24)
                likes.text = formatCount(post.likes)
                ivLikes.setOnClickListener {
                    onLikeListener(post)
                    likes.text = formatCount(post.likes)
                }

                ivShares.setOnClickListener {
                    onShareListener(post)
                    share.text = formatCount(post.shares)

                }
            }

        }
    }
}