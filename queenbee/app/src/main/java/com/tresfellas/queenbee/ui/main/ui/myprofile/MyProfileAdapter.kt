package com.tresfellas.queenbee.ui.main.ui.myprofile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyProfileAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MyProfileFragment()
            else -> MySecretProfileFragment()
        }
    }
}