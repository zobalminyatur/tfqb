package com.tresfellas.queenbee.ui.main.ui.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tresfellas.queenbee.databinding.FragmentMyProfileBinding
import com.tresfellas.queenbee.databinding.FragmentMyProfilePagerBinding

class MyProfilePagerFragment : Fragment() {

    private lateinit var profileViewModel: MyProfileViewModel
    private var _binding: FragmentMyProfilePagerBinding? = null

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

        _binding = FragmentMyProfilePagerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initViewPager()
//        val textView: TextView = binding.textNotifications
//        profileViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        return root
    }

    private fun initViewPager(){
        val profileTabAdapter = MyProfileAdapter(this)
        val viewpager = binding.myProfileViewpager

        viewpager.apply {
            adapter = profileTabAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}