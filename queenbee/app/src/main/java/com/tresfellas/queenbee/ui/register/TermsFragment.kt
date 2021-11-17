package com.tresfellas.queenbee.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.data.model.RegisterDTO
import kotlinx.android.synthetic.main.fragment_personnel.*
import kotlinx.android.synthetic.main.fragment_terms.*

class TermsFragment : Fragment() {
    private val mViewModel by activityViewModels<LoginViewModel>()

    //    private val args: TermsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_terms, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        checkBox_terms.setOnCheckedChangeListener { _, isCheck ->
//            if(isCheck) {
//                setAllCheck()
//            }
//        }
        checkBox_termsItem.setOnCheckedChangeListener { _, _ ->
            button_terms.isEnabled = isMandatoryCheckedAll()
        }
        checkBox_termsItem2.setOnCheckedChangeListener { _, _ ->
            button_terms.isEnabled = isMandatoryCheckedAll()
        }

        imageView_terms_item1.setOnClickListener {

            val action = TermsFragmentDirections.actionTermsFragmentToTermsDetailFragment("약관")
            findNavController().navigate(action)
        }
        imageView_terms_item2.setOnClickListener {
            val action = TermsFragmentDirections.actionTermsFragmentToTermsDetailFragment("약관")
            findNavController().navigate(action)
        }

        button_terms.setOnClickListener {
            val pair = isMandatoryCheckedAll()

            if (pair) {
//                data.cellphone = data.cellphone.substring(1).replace("-".toRegex(), "")
//                mViewModel.register(args.phoneNumber)

                CurrentUserManager.currentUser.agreedToTermsAndConditions = true
                CurrentUserManager.currentUser.accountStatus = "active"

                mViewModel.updateCurrentUser(CurrentUserManager.currentUser)

            } else {
                Toast.makeText(requireContext(), R.string.terms_unchecked, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        mViewModel.isProcessBar.observe(viewLifecycleOwner,Observer{
            if(it){
                progressBar_terms.visibility = View.VISIBLE
            }else{
                progressBar_terms.visibility = View.GONE
            }
        })

        mViewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            CurrentUserManager.currentUser = it
        })

        mViewModel.toNextPage.observe(viewLifecycleOwner, Observer {
            if(it){
                mViewModel.toNextPage.value = false
                val action = TermsFragmentDirections.actionTermsFragmentToProfileStartFragment()
                findNavController().navigate(action)
            }
        })

    }

//    private fun setAllCheck() {
//
//        checkBox_termsItem.isChecked = true
//        checkBox_termsItem2.isChecked = true
//        for(i in 0 until mAdapter.itemCount) {
//            val viewHolder =
//                requireView().recyclerView_terms.findViewHolderForAdapterPosition(i)
//
//            viewHolder?.let {
//                it.itemView.checkBox_termsItem.isChecked = true
//            }
//        }
//    }

    private fun isMandatoryCheckedAll(): Boolean {
        return checkBox_termsItem.isChecked &&
                checkBox_termsItem2.isChecked
    }

}
