package com.tresfellas.queenbee.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tresfellas.queenbee.R
import kotlinx.android.synthetic.main.fragment_profile_start.*
import kotlinx.android.synthetic.main.fragment_terms.*

class ProfileStartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_start, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_profile_start.setOnClickListener {
            val action = ProfileStartFragmentDirections.actionProfileStartFragmentToProfileGenderFragment()
            findNavController().navigate(action)

        }

    }
}