package com.tresfellas.queenbee.ui.main.ui.editsecretprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding3.widget.checkedChanges
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.databinding.FragmentEditSecretProfileBinding
import com.tresfellas.queenbee.ui.main.MainViewModel

class EditSecretProfileFragment : Fragment() {

    private var _binding: FragmentEditSecretProfileBinding? = null
    private lateinit var editSecretProfileViewModel: EditSecretProfileViewModel
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditSecretProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        editSecretProfileViewModel =
            ViewModelProvider(requireActivity()).get(EditSecretProfileViewModel::class.java)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mainViewModel.isProcessBar.observe(viewLifecycleOwner,Observer{
            if(it){
                binding.progressBarEditSecretProfile.layoutProgressBar.visibility = View.VISIBLE
            }else{
                binding.progressBarEditSecretProfile.layoutProgressBar.visibility = View.GONE
            }
        })

        editSecretProfileViewModel.questionnaireItemsLiveData.observe(viewLifecycleOwner, Observer {
            editSecretProfileViewModel.questionnaireItems = it

            val fragment = EditSecretAnswerFragment.newInstance(0)
//            parentFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit)
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit)
                .addToBackStack("secret")
                .replace(R.id.frameLayout_main, fragment)
                .commit()

        })

        binding.checkBoxEditSecretProfileStart.setOnCheckedChangeListener { _, boolean ->
            binding.buttonEditSecretProfileStart.isEnabled = boolean
        }

        binding.buttonEditSecretProfileStart.setOnClickListener {
            getBoldProfiles()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getBoldProfiles(){
        if(CurrentUserManager.currentUser.sex == "male"){
            editSecretProfileViewModel.getMaleBoldProfiles()
        }else{
            editSecretProfileViewModel.getFemaleBoldProfiles()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}