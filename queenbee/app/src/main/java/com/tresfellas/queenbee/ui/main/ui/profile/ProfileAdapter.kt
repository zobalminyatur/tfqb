package com.tresfellas.queenbee.ui.main.ui.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tresfellas.queenbee.data.model.UserDTO

class ProfileAdapter(fragment : Fragment, val userDTO: UserDTO) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ProfileFragment.newInstance(userDTO)
            else -> SecretProfileFragment.newInstance(userDTO)
        }
    }
}