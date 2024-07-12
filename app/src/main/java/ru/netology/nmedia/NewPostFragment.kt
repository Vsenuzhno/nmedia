package ru.netology.nmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding

class NewPostFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false)

        arguments?.textArg?.let ( binding.edit::setText)
        val viewModel: PostViewModel by activityViewModels()

        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            if (binding.edit.text.isNotBlank()) {
                viewModel.changeContent(binding.edit.text.toString())
                viewModel.edited.value?.let { post ->
                    viewModel.save(post)
                }
                findNavController().navigateUp()
            }
        }
        return binding.root
    }
    companion object {
        var Bundle.textArg: String? by StringArg
    }
}