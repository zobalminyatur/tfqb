package com.tresfellas.queenbee.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import kotlinx.android.synthetic.main.fragment_profile_nickname.*

class ProfileNicknameFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_nickname, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText_profile_nickname_body.afterTextChanged {
            button_profile_nickname.isEnabled = it.length >= 4
        }

        button_profile_nickname.setOnClickListener {
            val nickname = editText_profile_nickname_body.text.toString()
//            mViewModel.registerDTO.nickname = nickname
            CurrentUserManager.currentUser.nickName = nickname
            val action = ProfileNicknameFragmentDirections.actionProfileNicknameFragmentToProfileSelfIntroFragment()
            findNavController().navigate(action)
        }

    }
}