package com.tresfellas.queenbee.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_profile_any_location.*

class ProfileAnyLocationFragment : Fragment() {
    private val mViewModel by activityViewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_any_location, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_profile_any_location.setOnClickListener {
            val anyLocation = editText_any_location.text.toString()
//            mViewModel.registerDTO.anyLocation = anyLocation
//            mViewModel.register()
//            val intent = Intent(requireActivity(), MainActivity::class.java)
//            requireActivity().startActivity(intent)
//            requireActivity().finish()
        }


    }
}