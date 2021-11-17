package com.tresfellas.queenbee.ui.main.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.databinding.FragmentMyProfileBinding
import com.tresfellas.queenbee.databinding.FragmentShopBinding
import com.tresfellas.queenbee.ui.main.ui.myprofile.MyProfileViewModel

class ShopFragment : Fragment() {

    private lateinit var shopViewModel: ShopViewModel
    private var _binding: FragmentShopBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shopViewModel =
            ViewModelProvider(this).get(ShopViewModel::class.java)

        _binding = FragmentShopBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.textViewRoyalJellyAmount.text = CurrentUserManager.currentUser.royalJelly.toString()+"ml"
        initViewPagerWithTab()

        return root
    }

    private fun initViewPagerWithTab() {
        val shopTabAdapter = ShopTabAdapter(this)
        val viewpager = binding.viewPagerShop
        viewpager.adapter = shopTabAdapter

        TabLayoutMediator(binding.tabLayoutShop, viewpager) { tab, position ->
            tab.text =
            when(position){
                0 -> "충전하기"
                1 -> "무료 로얄젤리"
                else -> "충전내역"
            }
            viewpager.setCurrentItem(tab.position, true)
        }.attach()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopViewModel.royalJellyPurchaseDTO.observe(viewLifecycleOwner, Observer {
            CurrentUserManager.currentUser.royalJelly = it.balance
            println("@@@@@@@JELYYAMOUNTUPDATED@@@@@@@@@@@@@")
            binding.textViewRoyalJellyAmount.text = it.balance.toString()+"ml"
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}