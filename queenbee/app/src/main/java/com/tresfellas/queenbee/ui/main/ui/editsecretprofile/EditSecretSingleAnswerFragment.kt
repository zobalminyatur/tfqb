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
import com.tresfellas.queenbee.ui.main.ui.myprofile.MySecretProfileViewModel

class EditSecretSingleAnswerFragment : Fragment(), EditSecretMultipleAdapter.OnAdapterListener {

    private var _binding: FragmentEditSecretAnswerBinding? = null
    private lateinit var editSecretProfileAdapter: EditSecretMultipleAdapter
    private lateinit var questionaireItem: QuestionnaireItem

    private lateinit var mySecretProfileViewModel: MySecretProfileViewModel
    private val mainViewModel by activityViewModels<MainViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var questionnaireItemPosition = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mySecretProfileViewModel =
            ViewModelProvider(requireActivity()).get(MySecretProfileViewModel::class.java)

        _binding = FragmentEditSecretAnswerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.editSecretProfileBackAnswer.visibility = View.GONE

        mySecretProfileViewModel.getBoldProfiles()

        questionnaireItemPosition = requireArguments().getInt("questionItemPosition")

        questionaireItem = mySecretProfileViewModel.questionnaireItems[questionnaireItemPosition]

        binding.textViewEditSecretProfileMultipleAnswer.text = questionaireItem.question

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (questionaireItem.type == QuestionnaireItem.QuestionType.MULTIPLECHOICE) {
            initRecyclerView()
            binding.buttonEditSecretProfileMultipleAnswer.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            }
        } else {
            binding.editTextEditSecretProfileShortAnswer.visibility = View.VISIBLE
            binding.buttonEditSecretProfileMultipleAnswer.setOnClickListener {
                mySecretProfileViewModel.toUploadBoldProfileDTO[questionnaireItemPosition].choiceNumber =
                    1
                mySecretProfileViewModel.toUploadBoldProfileDTO[questionnaireItemPosition].choice =
                    binding.editTextEditSecretProfileShortAnswer.text.toString()
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            }
        }

    }

    private fun initRecyclerView() {
        editSecretProfileAdapter = EditSecretMultipleAdapter(questionaireItem.choices, this)
        val currentChoicePosition =
            mySecretProfileViewModel.toUploadBoldProfileDTO[questionnaireItemPosition].choiceNumber
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
        mySecretProfileViewModel.toUploadBoldProfileDTO[questionnaireItemPosition].choice =
            questionaireItem.choices[position].choice
        mySecretProfileViewModel.toUploadBoldProfileDTO[questionnaireItemPosition].choiceNumber =
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
            EditSecretSingleAnswerFragment().apply {
                arguments = Bundle().apply {
                    putInt("questionItemPosition", position)
                }
            }
    }
}