package com.tresfellas.queenbee.ui.register

import android.content.Intent
import android.os.Bundle
import android.os.UserManager
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.api.managers.TokenManager
import com.tresfellas.queenbee.data.model.SMSOTPDTO
import com.tresfellas.queenbee.ui.main.MainActivity
import com.tresfellas.queenbee.utils.SharedPreference
import kotlinx.android.synthetic.main.fragment_personnel.*
import kotlinx.android.synthetic.main.fragment_verification_code.*

class VerificationCodeFragment : Fragment(R.layout.fragment_verification_code) {

    private val mViewModel by activityViewModels<LoginViewModel>()
    private val args: VerificationCodeFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val phoneNumber = args.phoneNumber
        val number = StringBuilder(args.phoneNumber).insert(7, "-")
        val formedPhoneNumber = StringBuilder(number).insert(3, "-")

        val phoneNumberAddedText =
            resources.getString(R.string.verification_title, formedPhoneNumber)
        textView_verificationCode_title.text = phoneNumberAddedText


        button_verificationCode_resend.setOnClickListener {
            val smsotpdto = SMSOTPDTO(phoneNumber = phoneNumber)
            mViewModel.requestMobileVerificationCode(smsotpdto)
        }

        editText_verificationCode.afterTextChanged {
            button_verificationCode_submit.isEnabled = it.length == 6
        }

        button_verificationCode_submit.setOnClickListener {
            mViewModel.isProcessBar.value = true
            val code = editText_verificationCode.text.toString()
            val smsOtoDTO = mViewModel.smsOtpDto.value!!
            smsOtoDTO.otp = code
            mViewModel.verifyMobileVerificationCode(smsOtoDTO)

        }
        mViewModel.registerDTO.observe(viewLifecycleOwner, Observer {
            println("@@@@@CURRENTUSER==$it.user")
            CurrentUserManager.currentUser = it.user
            if(CurrentUserManager.currentUser.age != 0){
                saveToken()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                requireActivity().startActivity(intent)
                requireActivity().finish()
            }else{
                val action =
                    VerificationCodeFragmentDirections.actionVerificationCodeFragmentToTermsAndConditionsFragment()
                findNavController().navigate(action)
            }

        })
        mViewModel.isProcessBar.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar_verificationCode.visibility = View.VISIBLE
            } else {
                progressBar_verificationCode.visibility = View.GONE
            }
        })
        mViewModel.errorStatus.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), this.resources.getString(it), Toast.LENGTH_SHORT)
                .show()
        })
    }
    fun saveToken(){
        val sharedPreference = SharedPreference(requireContext())
        val accessToken =  TokenManager.accessToken
        val refreshToken = TokenManager.refreshToken

        sharedPreference.saveValueString("access_token", accessToken)
        sharedPreference.saveValueString("refresh_token", refreshToken)

    }

}
