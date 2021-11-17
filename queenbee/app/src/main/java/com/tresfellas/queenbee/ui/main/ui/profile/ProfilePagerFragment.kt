package com.tresfellas.queenbee.ui.main.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.databinding.FragmentProfilePagerBinding

class ProfilePagerFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfilePagerBinding? = null

    private lateinit var userDTO : UserDTO
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userDTO = requireArguments().getParcelable<UserDTO>("userDTO")!!
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfilePagerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initViewPager()
//        val textView: TextView = binding.textNotifications
//        profileViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        return root
    }

    private fun initViewPager(){
        val profileTabAdapter = ProfileAdapter(this,userDTO)
        val viewpager = binding.profileViewpager

        viewpager.apply {
            adapter = profileTabAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        @JvmStatic
        fun newInstance(userDTO: UserDTO) =
            ProfilePagerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("userDTO", userDTO)
                }
            }
    }
}