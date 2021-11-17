package com.tresfellas.queenbee.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tresfellas.queenbee.R
import kotlinx.android.synthetic.main.fragment_self_check.*

class SelfCheckFragment : Fragment() {
    private val mViewModel by activityViewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_self_check, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profile_self_check_checkBox_yes_1.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked) {
                button_profile_self_check.isEnabled = true
                if (profile_self_check_checkBox_no_1.isChecked) {
                    profile_self_check_checkBox_no_1.isChecked = false
                }
            } else {
                if (!profile_self_check_checkBox_no_1.isChecked) {
                    button_profile_self_check.isEnabled = false
                }
            }
        }
        profile_self_check_checkBox_no_1.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked) {
                button_profile_self_check.isEnabled = true
                if (profile_self_check_checkBox_yes_1.isChecked) {
                    profile_self_check_checkBox_yes_1.isChecked = false
                }
            } else {
                if (!profile_self_check_checkBox_yes_1.isChecked) {
                    button_profile_self_check.isEnabled = false
                }
            }
        }

        button_profile_self_check.setOnClickListener {
            if (profile_self_check_checkBox_yes_1.isChecked) {
//                mViewModel.registerDTO.selfChecked = true
                val actionToCheckReport =
                    SelfCheckFragmentDirections.actionSelfCheckFragmentToSelfCheckReportFragment()
                findNavController().navigate(actionToCheckReport)
            } else {
                val actionToNoTested =
                    SelfCheckFragmentDirections.actionSelfCheckFragmentToNotTestedFragment()
                findNavController().navigate(actionToNoTested)
            }
        }

    }
}