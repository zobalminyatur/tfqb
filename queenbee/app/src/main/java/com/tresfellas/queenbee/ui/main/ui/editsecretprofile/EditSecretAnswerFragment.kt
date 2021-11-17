package com.tresfellas.queenbee.ui.main.ui.editsecretprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.data.model.QuestionnaireItem
import com.tresfellas.queenbee.databinding.FragmentEditSecretAnswerBinding
import com.tresfellas.queenbee.ui.main.MainViewModel
import com.tresfellas.queenbee.ui.register.afterTextChanged

class EditSecretAnswerFragment() : Fragment(), EditSecretMultipleAdapter.OnAdapterListener {

    private var _binding: FragmentEditSecretAnswerBinding? = null
    private lateinit var editSecretProfileAdapter: EditSecretMultipleAdapter
    private lateinit var questionaireItem: QuestionnaireItem

    private lateinit var editSecretProfileViewModel: EditSecretProfileViewModel
    private val mainViewModel by activityViewModels<MainViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var questionnaireItemPosition = -1

    private var isLastQuestion = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editSecretProfileViewModel =
            ViewModelProvider(requireActivity()).get(EditSecretProfileViewModel::class.java)

        _binding = FragmentEditSecretAnswerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        questionnaireItemPosition = requireArguments().getInt("questionItemPosition")

        isLastQuestion =
            questionnaireItemPosition == editSecretProfileViewModel.questionnaireItems.size - 1

        questionaireItem = editSecretProfileViewModel.questionnaireItems[questionnaireItemPosition]

        binding.textViewEditSecretProfileMultipleAnswer.text = questionaireItem.question

        if (isLastQuestion) {
            binding.buttonEditSecretProfileMultipleAnswer.setText(R.string.done)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (questionaireItem.type == QuestionnaireItem.QuestionType.MULTIPLECHOICE) {
            initRecyclerView()
            binding.buttonEditSecretProfileMultipleAnswer.setOnClickListener {
                if (editSecretProfileAdapter.currentSelectedItemPosition == -1) {
                    Toast.makeText(requireContext(), "선택해주세요", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    onNextButtonClicked()
                }
            }
        } else {
            binding.editTextEditSecretProfileShortAnswer.visibility = View.VISIBLE
            binding.editTextEditSecretProfileShortAnswer.afterTextChanged {
                binding.buttonEditSecretProfileMultipleAnswer.isEnabled = it != ""
            }
            binding.buttonEditSecretProfileMultipleAnswer.setOnClickListener {
                editSecretProfileViewModel.toUploadBoldProfileDTO[questionnaireItemPosition].choiceNumber =
                    1
                editSecretProfileViewModel.toUploadBoldProfileDTO[questionnaireItemPosition].choice =
                    binding.editTextEditSecretProfileShortAnswer.text.toString()
                if (isLastQuestion) {
//                    CurrentUserManager.currentUser.boldProfile =
//                        editSecretProfileViewModel.sortBoldProfileByQuestionNumber()
                    val userDTOTOUpload = CurrentUserManager.currentUser
                    userDTOTOUpload.boldProfile = editSecretProfileViewModel.toUploadBoldProfileDTO
                    mainViewModel.updateCurrentUser(userDTOTOUpload)
                } else {
                    onNextButtonClicked()
                }
            }
        }
        mainViewModel.isProcessBar.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBarEditSecretProfileAnswer.layoutProgressBar.visibility =
                    View.VISIBLE
            } else {
                binding.progressBarEditSecretProfileAnswer.layoutProgressBar.visibility = View.GONE
            }
        })

        mainViewModel.toPopBackStack.observe(viewLifecycleOwner, Observer {
            if (it) {
                mainViewModel.toPopBackStack.value = false
                requireActivity().supportFragmentManager.popBackStack(
                    "secret",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
        })
    }

    fun onNextButtonClicked() {
        val fragment = EditSecretAnswerFragment.newInstance(questionnaireItemPosition + 1)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
//        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack("secret")
            .replace(R.id.frameLayout_main, fragment)
            .commit()
    }

    private fun initRecyclerView() {
        editSecretProfileAdapter = EditSecretMultipleAdapter(questionaireItem.choices, this)
        val currentChoicePosition =
            editSecretProfileViewModel.toUploadBoldProfileDTO[questionnaireItemPosition].choiceNumber
        if (currentChoicePosition != null) {
            editSecretProfileAdapter.currentSelectedItemPosition = currentChoicePosition - 1

        }
        binding.recyclerViewEditSecretProfileMultiple.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = editSecretProfileAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRowClicked(position: Int) {
        editSecretProfileViewModel.toUploadBoldProfileDTO[questionnaireItemPosition].choice =
            questionaireItem.choices[position].choice
        editSecretProfileViewModel.toUploadBoldProfileDTO[questionnaireItemPosition].choiceNumber =
            questionaireItem.choices[position].choiceNumber
        editSecretProfileAdapter.lastSelectedPosition =
            editSecretProfileAdapter.currentSelectedItemPosition
        editSecretProfileAdapter.currentSelectedItemPosition = position
        editSecretProfileAdapter.onItemSelectedChanged()
        binding.buttonEditSecretProfileMultipleAnswer.isEnabled = true
    }

    companion object {
        @JvmStatic
        fun newInstance(position: Int) =
            EditSecretAnswerFragment().apply {
                arguments = Bundle().apply {
                    putInt("questionItemPosition", position)
                }
            }
    }
}