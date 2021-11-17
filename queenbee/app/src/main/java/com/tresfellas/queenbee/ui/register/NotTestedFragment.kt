package com.tresfellas.queenbee.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tresfellas.queenbee.R
import kotlinx.android.synthetic.main.fragment_not_tested.*

class NotTestedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_not_tested, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        layout_not_tested_body1.setOnClickListener {

        }

        super.onViewCreated(view, savedInstanceState)

    }
}