package com.tresfellas.queenbee.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.data.model.SMSOTPDTO
import kotlinx.android.synthetic.main.fragment_personnel.*

class PersonnelFragment : Fragment(R.layout.fragment_personnel) {

    private val mViewModel by activityViewModels<LoginViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var phone = ""
        editText_personnel_phone.afterTextChanged {
            button_personnel.isEnabled = it.length == 11
            phone = editText_personnel_phone.text.toString()
        }
        editText_personnel_phone.requestFocus()

        button_personnel.setOnClickListener {

            val phoneNumber = SMSOTPDTO(phoneNumber = phone)
            mViewModel.isProcessBar.value = true
//            mViewModel.smsList.value = null
            mViewModel.requestMobileVerificationCode(phoneNumber)
        }

        mViewModel.isProcessBar.observe(viewLifecycleOwner,Observer{
            if(it){
                progressBar_personnel_phone.visibility = View.VISIBLE
            }else{
                progressBar_personnel_phone.visibility = View.GONE
            }
        })

        mViewModel.toNextPage.observe(viewLifecycleOwner, Observer {
            if(it){
                mViewModel.toNextPage.value = false
                val action = PersonnelFragmentDirections.actionPersonnelFragmentToVerificationCodeFragment(phone)
                findNavController().navigate(action)
            }
        })

//        mViewModel.smsList.observe(viewLifecycleOwner, Observer {
//            if(it!=null){
//            }
//        })

        mViewModel.errorStatus.observe(viewLifecycleOwner,Observer{
            Toast.makeText(requireContext(), this.resources.getString(it), Toast.LENGTH_SHORT).show()
        })

    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}