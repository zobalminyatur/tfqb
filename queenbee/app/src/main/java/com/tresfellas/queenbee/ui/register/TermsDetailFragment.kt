package com.tresfellas.queenbee.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.tresfellas.queenbee.databinding.FragmentTermsDetailBinding

class TermsDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            FragmentTermsDetailBinding.inflate(inflater, container, false)
        val args : TermsDetailFragmentArgs by navArgs()
        return binding.root
    }
}
