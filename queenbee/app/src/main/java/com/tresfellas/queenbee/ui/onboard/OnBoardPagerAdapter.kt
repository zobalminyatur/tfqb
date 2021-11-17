package com.tresfellas.queenbee.ui.onboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private val description: Array<String>
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return description.size
    }

    override fun createFragment(position: Int): Fragment {
        return OnBoardFragment.newInstance(description[position],position)
    }
}