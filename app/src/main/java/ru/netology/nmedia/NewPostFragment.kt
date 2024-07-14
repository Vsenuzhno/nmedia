package ru.netology.nmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.databinding.FragmentNewPostBinding

class NewPostFragment : Fragment() {

    private lateinit var binding: FragmentNewPostBinding
    private val viewModel: PostViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.draftContent = binding.edit.text.toString()
                findNavController().navigateUp()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPostBinding.inflate(inflater, container, false)

        binding.edit.setText(viewModel.draftContent ?: arguments?.textArg)

        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            if (binding.edit.text.isNotBlank()) {
                viewModel.changeContent(binding.edit.text.toString())
                viewModel.edited.value?.let { post ->
                    viewModel.save(post)
                }
                viewModel.draftContent = null
                findNavController().navigateUp()
            } else {
                Snackbar.make(
                    binding.root, R.string.error_empty_content, BaseTransientBottomBar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) {}.show()
            }
        }
        return binding.root
    }

    companion object {
        var Bundle.textArg: String? by StringArg
    }
}