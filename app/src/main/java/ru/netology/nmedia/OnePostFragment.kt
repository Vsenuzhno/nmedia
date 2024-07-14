package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentOnePostBinding

class OnePostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: PostViewModel by activityViewModels()
        val binding = FragmentOnePostBinding.inflate(
            inflater,
            container,
            false
        )

        binding.lifecycleOwner = viewLifecycleOwner

        val viewHolder =
            PostsAdapter.PostViewHolder(binding.onePost, object : OnInteractionListener {
                override fun onEdit(post: Post) {
                    val bundle = Bundle().apply {
                        putString(Intent.EXTRA_TEXT, post.content)
                        putLong("postId", post.id)
                    }
                    findNavController().navigate(
                        R.id.action_onePostFragment_to_editPostFragment2,
                        bundle
                    )
                }

                override fun onLikeListener(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                    findNavController().navigateUp()
                    viewModel.cancelEditing()
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

                override fun onVideoClick(videoUrl: String?) {
                    videoUrl ?: return
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                    startActivity(intent)
                }

                override fun onOpenPost(post: Post) {
                    val currentFragment = findNavController().currentDestination?.id
                    if (currentFragment == R.id.onePostFragment) {
                        return
                    }
                    val bundle = Bundle().apply {
                        putLong("postId", post.id)
                    }
                    findNavController().navigate(
                        R.id.action_feedFragment_to_onePostFragment,
                        bundle
                    )
                }
            })

        val postId = arguments?.getLong("postId") ?: run {
            Log.e("OnePostFragment", "Post ID is null")
            return binding.root
        }

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId } ?: return@observe
            viewHolder.bind(post)
        }

        parentFragmentManager.setFragmentResultListener(
            "editPostRequestKey",
            viewLifecycleOwner
        ) { _, result ->
            val editedPost = Post(
                id = result.getLong("postId"),
                content = result.getString(Intent.EXTRA_TEXT) ?: "",
                author = result.getString("author") ?: "",
                likedByMe = result.getBoolean("likedByMe"),
                sharedByMe = result.getBoolean("sharedByMe"),
                likes = result.getInt("likes"),
                shares = result.getInt("shares"),
                published = result.getString("published") ?: ""
            )
            viewModel.save(editedPost)
        }

        return binding.root
    }
}