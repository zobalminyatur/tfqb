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
import kotlinx.android.synthetic.main.fragment_profile_self_intro.*

class ProfileSelfIntroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_self_intro, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText_profile_self_intro_body.afterTextChanged {
            button_profile_self_intro.isEnabled = it.length > 16
        }

        button_profile_self_intro.setOnClickListener {
            val selfIntro = editText_profile_self_intro_body.text.toString()
//            mViewModel.registerDTO.selfIntro = selfIntro
            CurrentUserManager.currentUser.selfIntroduction = selfIntro
            val action = ProfileSelfIntroFragmentDirections.actionProfileSelfIntroFragmentToProfileMainLocationFragment()
            findNavController().navigate(action)
        }

    }
}