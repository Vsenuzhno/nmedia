package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentEditPostBinding

class EditPostFragment : Fragment() {
    private lateinit var binding: FragmentEditPostBinding
    private var postId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val initialContent = arguments?.getString(Intent.EXTRA_TEXT) ?: ""
        postId = arguments?.getLong("postId") ?: 0L

        binding.editContent.setText(initialContent)
        binding.editContent.requestFocus()

        binding.ok.setOnClickListener {
            val result = Bundle().apply {
                putString(Intent.EXTRA_TEXT, binding.editContent.text.toString())
                putLong("postId", postId)
            }
            parentFragmentManager.setFragmentResult("editPostRequestKey", result)
            findNavController().navigateUp()
        }

        binding.cancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}