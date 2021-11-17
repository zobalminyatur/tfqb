package com.tresfellas.queenbee.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.data.model.RegisterDTO
import com.tresfellas.queenbee.ui.main.ui.home.RightNowFragment
import com.tresfellas.queenbee.ui.main.ui.home.UserListFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile_gender.*

class ProfileGenderFragment : Fragment() {

    private val mViewModel by activityViewModels<LoginViewModel>()
    var gender: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_gender, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_profile_gender_male.setOnClickListener {
            button_profile_gender.isEnabled = true
            tabMale()
        }
        layout_profile_gender_female.setOnClickListener {
            button_profile_gender.isEnabled = true
            tabFemale()
        }
        button_profile_gender.setOnClickListener {
//            mViewModel.registerDTO.gender = gender
            CurrentUserManager.currentUser.sex = gender
            val action =
                ProfileGenderFragmentDirections.actionProfileGenderFragmentToSelfCheckFragment()
            findNavController().navigate(action)
        }
    }

    private fun tabMale() {
        layout_profile_gender_male.background = ContextCompat.getDrawable(requireContext(),R.drawable.round_corner_button)
        textView_profile_gender_male.setTextColor(resources.getColor(R.color.gold))
        layout_profile_gender_female.background = ContextCompat.getDrawable(requireContext(),R.drawable.round_corner_10)
        textView_profile_gender_female.setTextColor(resources.getColor(R.color.black))
        gender = "male"
    }

    private fun tabFemale() {
        layout_profile_gender_female.background = ContextCompat.getDrawable(requireContext(),R.drawable.round_corner_button)
        textView_profile_gender_female.setTextColor(resources.getColor(R.color.gold))
        layout_profile_gender_male.background = ContextCompat.getDrawable(requireContext(),R.drawable.round_corner_10)
        textView_profile_gender_male.setTextColor(resources.getColor(R.color.black))
        gender = "female"
    }
}