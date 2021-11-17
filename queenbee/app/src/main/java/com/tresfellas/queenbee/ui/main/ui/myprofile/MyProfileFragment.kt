package com.tresfellas.queenbee.ui.main.ui.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.databinding.FragmentMyProfileBinding
import com.tresfellas.queenbee.ui.main.ui.shop.ShopFragment

class MyProfileFragment : Fragment() {

    private lateinit var profileViewModel: MyProfileViewModel
    private var _binding: FragmentMyProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(MyProfileViewModel::class.java)

        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.userDTO = CurrentUserManager.currentUser

        binding.myProfileAppbarShop.setOnClickListener {
            val fragment = ShopFragment()

            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom, R.anim.enter_from_bottom, R.anim.exit_to_bottom)
                .addToBackStack(null)
                .add(R.id.frameLayout_main, fragment)
                .commit()
        }

//        val textView: TextView = binding.textNotifications
//        profileViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}