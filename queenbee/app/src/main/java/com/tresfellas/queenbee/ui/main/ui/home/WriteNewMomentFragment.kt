package com.tresfellas.queenbee.ui.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.databinding.FragmentWriteNewMomentBinding
import com.tresfellas.queenbee.ui.main.MainViewModel
import com.tresfellas.queenbee.ui.main.ui.editsecretprofile.EditSecretAnswerFragment
import com.tresfellas.queenbee.ui.register.afterTextChanged

class WriteNewMomentFragment : Fragment() {

    private var _binding: FragmentWriteNewMomentBinding? = null
    private val mainViewModel by activityViewModels<MainViewModel>()

    private val binding get() = _binding!!
    private var isFirstMoment : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWriteNewMomentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        checkIsFirstMoment()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextWriteNewMoment.afterTextChanged {
            binding.buttonWriteNewMoment.isEnabled = it != ""
        }
        binding.editTextWriteNewMoment.setOnClickListener {
            val userDTOTOUpload = CurrentUserManager.currentUser
            val moment = UserDTO.MomentDTO(description = binding.editTextWriteNewMoment.text.toString())
            userDTOTOUpload.moment= moment
            mainViewModel.updateCurrentUser(userDTOTOUpload)


        }
        mainViewModel.isProcessBar.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBarWriteNewMoment.layoutProgressBar.visibility = View.VISIBLE
            } else {
                binding.progressBarWriteNewMoment.layoutProgressBar.visibility = View.GONE
            }
        })

        mainViewModel.toPopBackStack.observe(viewLifecycleOwner, Observer {
            if (it) {
                mainViewModel.toPopBackStack.value = false
                if(isFirstMoment){
                    mainViewModel.activatePromotionState("firstMoment","activated")
                }

            }
        })

        mainViewModel.toNextPage.observe(viewLifecycleOwner, Observer {
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        })

    }

    private fun checkIsFirstMoment() {
        isFirstMoment = CurrentUserManager.currentUser.moment == null
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    companion object {
//        @JvmStatic
//        fun newInstance(userId: Int) =
//            EditSecretAnswerFragment().apply {
//                arguments = Bundle().apply {
//                    putInt("userId", userId)
//                }
//            }
//    }
}