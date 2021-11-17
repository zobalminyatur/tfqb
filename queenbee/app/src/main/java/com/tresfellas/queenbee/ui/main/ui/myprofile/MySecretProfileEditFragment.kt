package com.tresfellas.queenbee.ui.main.ui.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.databinding.FragmentMySecretProfileEditBinding
import com.tresfellas.queenbee.ui.main.MainViewModel
import com.tresfellas.queenbee.ui.main.ui.editsecretprofile.EditSecretSingleAnswerFragment

class MySecretProfileEditFragment : Fragment() , MySecretProfileEditAdapter.OnAdapterListener {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var mySecretProfileViewModel : MySecretProfileViewModel
    private var _binding : FragmentMySecretProfileEditBinding? = null
    private lateinit var mySercretProfileEditAdapter : MySecretProfileEditAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var boldProfileToUpdate : ArrayList<UserDTO.BoldProfileDTO>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        boldProfileToUpdate = CurrentUserManager.currentUser.boldProfile
        mySecretProfileViewModel =
            ViewModelProvider(requireActivity()).get(MySecretProfileViewModel::class.java)

        _binding = FragmentMySecretProfileEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mySecretProfileViewModel.toUploadBoldProfileDTO = CurrentUserManager.currentUser.boldProfile

        initRecyclerView()



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewMyProfileEditDone.setOnClickListener {
//            mySecretProfileViewModel.toUploadBoldProfileDTO = mySecretProfileViewModel.sortBoldProfileByQuestionNumber()
            val toUpdateUserDTO = CurrentUserManager.currentUser
            toUpdateUserDTO.boldProfile = mySecretProfileViewModel.toUploadBoldProfileDTO
            mainViewModel.updateCurrentUser(CurrentUserManager.currentUser)
        }

        mainViewModel.isProcessBar.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBarEditMySecretProfile.layoutProgressBar.visibility =
                    View.VISIBLE
            } else {
                binding.progressBarEditMySecretProfile.layoutProgressBar.visibility = View.GONE
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

    private fun initRecyclerView(){
        mySercretProfileEditAdapter = MySecretProfileEditAdapter(
            boldProfileToUpdate,this)
        binding.recyclerViewEditMySecretProfile.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mySercretProfileEditAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRowClicked(item: UserDTO.BoldProfileDTO ,position : Int) {
        val fragment = EditSecretSingleAnswerFragment.newInstance(position)
//        parentFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom, R.anim.enter_from_bottom, R.anim.exit_to_bottom)
        requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom, R.anim.enter_from_bottom, R.anim.exit_to_bottom)
            .addToBackStack("secret")
            .replace(R.id.frameLayout_main, fragment)
            .commit()
    }

}