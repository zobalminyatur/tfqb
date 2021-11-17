package com.tresfellas.queenbee.ui.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.databinding.FragmentHomeBinding
import com.tresfellas.queenbee.ui.main.ui.profile.ProfilePagerFragment
import com.tresfellas.queenbee.ui.main.ui.shop.ShopFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var currentTab = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val fragment = UserListFragment()

        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.home_fragment_container, fragment)
            .commit()

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        home_tab_item1.setOnClickListener {
            toggleTab1()
        }
        home_tab_item2.setOnClickListener {
            toggleTab2()
        }
        home_appbar_shop.setOnClickListener {
            val fragment = ShopFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
//                .setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom, R.anim.enter_from_bottom, R.anim.exit_to_bottom)
                .addToBackStack(null)
                .add(R.id.frameLayout_main, fragment)
                .commit()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun toggleTab1(){
        if(currentTab==1){
            currentTab = 0
            imageView_home_tab_item2.setImageResource(R.drawable.ic_wing_gray)
            home_tab_item1.setTextColor(resources.getColor(R.color.black))
            textView_home_tab_item2.setTextColor(resources.getColor(R.color.gray))

            val fragment = UserListFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.home_fragment_container, fragment)
                .commit()
        }
    }
    private fun toggleTab2(){
        if(currentTab==0){
            currentTab = 1
            imageView_home_tab_item2.setImageResource(R.drawable.ic_wing)
            home_tab_item1.setTextColor(resources.getColor(R.color.gray))
            textView_home_tab_item2.setTextColor(resources.getColor(R.color.black))
            val fragment = RightNowFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.home_fragment_container, fragment)
                .commit()
        }
    }

}