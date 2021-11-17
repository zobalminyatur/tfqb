package com.tresfellas.queenbee.ui.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import kotlinx.android.synthetic.main.fragment_profile_age.*
import androidx.core.content.ContextCompat.getSystemService




class ProfileAgeFragment : Fragment() {
    private val mViewModel by activityViewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_age, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText_profile_age_body.requestFocus()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        editText_profile_age_body.afterTextChanged {
            if(it ==""){
                button_profile_age.isEnabled = false
            }else{
                button_profile_age.isEnabled = it.toInt() >= 18
            }
        }

        button_profile_age.setOnClickListener {
            val age = editText_profile_age_body.text.toString().toInt()
//            mViewModel.registerDTO.age = age
            CurrentUserManager.currentUser.age = age
            val action  = ProfileAgeFragmentDirections.actionProfileAgeFragmentToProfileNicknameFragment()
            findNavController().navigate(action)
        }

    }
}