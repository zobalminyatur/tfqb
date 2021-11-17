package com.tresfellas.queenbee.ui.main.ui.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.databinding.FragmentMySecretProfileBinding
import com.tresfellas.queenbee.ui.main.MainViewModel
import com.tresfellas.queenbee.ui.main.ui.editsecretprofile.EditSecretProfileFragment
import com.tresfellas.queenbee.ui.main.ui.profile.SecretProfileAdapter
import com.tresfellas.queenbee.ui.main.ui.shop.ShopFragment

class MySecretProfileFragment : Fragment() {

    private val mViewModel by activityViewModels<MainViewModel>()

    private var _binding: FragmentMySecretProfileBinding? = null

    private lateinit var mySercretProfileAdapter: MySecretProfileAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMySecretProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        checkBoldProfileFilled()

        binding.buttonNotFilledSecretProfile.setOnClickListener {
            val fragment = EditSecretProfileFragment()
//            parentFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit)
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit)
                .addToBackStack("secret")
                .replace(R.id.frameLayout_main, fragment)
                .commit()
        }

        binding.buttonEditMySecretProfile.setOnClickListener {
            val fragment = MySecretProfileEditFragment()
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit)
                .addToBackStack("secret")
                .replace(R.id.frameLayout_main, fragment)
                .commit()
        }

        binding.mySecretProfileAppbarShop.setOnClickListener {
            val fragment = ShopFragment()
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom, R.anim.enter_from_bottom, R.anim.exit_to_bottom)
                .addToBackStack(null)
                .add(R.id.frameLayout_main, fragment)
                .commit()
        }

        initRecyclerView()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel.currentUserDTO.observe(viewLifecycleOwner, Observer{
            if(it.boldProfile.isEmpty()){
                binding.layoutNotFilledMySecretProfile.visibility = View.VISIBLE
                binding.buttonEditMySecretProfile.visibility = View.GONE
            }else{
                binding.layoutNotFilledMySecretProfile.visibility = View.GONE
                binding.buttonEditMySecretProfile.visibility = View.VISIBLE
                mySercretProfileAdapter.setItem(it.boldProfile)
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView(){
        mySercretProfileAdapter = MySecretProfileAdapter(
            CurrentUserManager.currentUser.boldProfile)
        binding.recyclerViewMySecretProfile.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mySercretProfileAdapter
        }
    }

    private fun checkBoldProfileFilled(){
        if(CurrentUserManager.currentUser.boldProfile.isEmpty()){
            binding.layoutNotFilledMySecretProfile.visibility = View.VISIBLE
            binding.buttonEditMySecretProfile.visibility = View.GONE
        }else {
            binding.layoutNotFilledMySecretProfile.visibility = View.GONE
            binding.buttonEditMySecretProfile.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}