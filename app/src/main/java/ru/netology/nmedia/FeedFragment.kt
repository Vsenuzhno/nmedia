package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )




        val viewModel: PostViewModel by activityViewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            val editPostLauncher =
                registerForActivityResult(EditPostResultContract()) { editedPost ->
                    editedPost ?: return@registerForActivityResult
                    viewModel.save(editedPost)
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

            override fun onVideoClick(videoUrl: String?) {
                videoUrl ?: return
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                startActivity(intent)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }


        binding.fab.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_animation)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    // Nothing to do
                }

                override fun onAnimationEnd(animation: Animation?) {
                    findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
                }

                override fun onAnimationRepeat(animation: Animation?) {
                    // Nothing to do
                }
            })
            binding.fab.startAnimation(animation)
        }
        return binding.root
    }
}
