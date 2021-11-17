package com.tresfellas.queenbee.ui.main.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.databinding.FragmentSecretProfileBinding
import com.tresfellas.queenbee.ui.main.MainViewModel
import com.tresfellas.queenbee.ui.main.ui.editsecretprofile.EditSecretProfileFragment
import com.tresfellas.queenbee.ui.main.ui.home.RightNowAdapter
import com.tresfellas.queenbee.ui.main.ui.shop.ShopFragment

class SecretProfileFragment  : Fragment() {

    private val mViewModel by activityViewModels<MainViewModel>()
    private var _binding: FragmentSecretProfileBinding? = null
    private lateinit var sercretProfileAdapter: SecretProfileAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var userDTO : UserDTO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userDTO = requireArguments().getParcelable<UserDTO>("userDTO")!!


        _binding = FragmentSecretProfileBinding.inflate(inflater, container, false)

        val root: View = binding.root

        checkCurrentUserBoldIsEmpty(CurrentUserManager.currentUser)

        println("@@@@@@@@@@@BOLDISEMPTY?${userDTO.boldProfile}")
        if(userDTO.boldProfile.isEmpty()){
            binding.layoutNotFilledSecretProfile.visibility = View.VISIBLE
        }else{
            binding.layoutNotFilledSecretProfile.visibility = View.GONE
        }

        initRecyclerView()

        binding.buttonEditSecretProfile.setOnClickListener {
            val fragment = EditSecretProfileFragment()
//            parentFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit)
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit)
                .addToBackStack("secret")
                .replace(R.id.frameLayout_main, fragment)
                .commit()
        }

        binding.secretProfileAppbarShop.setOnClickListener {
            val fragment = ShopFragment()
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom, R.anim.enter_from_bottom, R.anim.exit_to_bottom)
                .addToBackStack(null)
                .add(R.id.frameLayout_main, fragment)
                .commit()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mViewModel.currentUserDTO.observe(viewLifecycleOwner, Observer{
            checkCurrentUserBoldIsEmpty(it)
        })
        super.onViewCreated(view, savedInstanceState)
    }

    fun checkCurrentUserBoldIsEmpty(userDTO : UserDTO){
        println("@@@@@@@checkCurrentUserBoldIsEmpty${userDTO.boldProfile}")
        if(userDTO.boldProfile.isEmpty()){
            binding.layoutBlurSecretProfile.visibility = View.VISIBLE
        }else{
            binding.layoutBlurSecretProfile.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView(){
        sercretProfileAdapter = SecretProfileAdapter(
            userDTO)
        binding.recyclerViewSecretProfile.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sercretProfileAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(userDTO: UserDTO) =
            SecretProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("userDTO", userDTO)
                }
            }
    }
}