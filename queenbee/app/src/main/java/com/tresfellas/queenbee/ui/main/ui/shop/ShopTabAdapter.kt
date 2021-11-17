package com.tresfellas.queenbee.ui.main.ui.shop

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ShopTabAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ShopPurchaseFragment()
            1 -> ShopFreeFragment()
            else -> ShopHistoryFragment()
        }
    }
}