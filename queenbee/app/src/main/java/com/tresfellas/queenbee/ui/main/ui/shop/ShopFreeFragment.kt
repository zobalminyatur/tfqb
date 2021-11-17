package com.tresfellas.queenbee.ui.main.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.databinding.FragmentShopFreeBinding

class ShopFreeFragment : Fragment() {

    private lateinit var shopViewModel: ShopViewModel
    private lateinit var binding: FragmentShopFreeBinding

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        shopViewModel =
            ViewModelProvider(requireParentFragment()).get(ShopViewModel::class.java)

        shopViewModel.getMyRoyalJellyPromotions()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_free, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        shopViewModel.royalJellyPromotionsDTO.observe(viewLifecycleOwner, Observer {
            binding.royalJellyPromotionsDTO = it
        })

        binding.buttonShopFreeDaily1.setOnClickListener {
            println("ShopFreeTest1")
            shopViewModel.purchaseRoyalJelly(50,0,"dailyLogin")
        }
        binding.buttonShopFreeDaily2.setOnClickListener {
            println("ShopFreeTest1")
            shopViewModel.purchaseRoyalJelly(50,0,"dailyMoment")
        }
        binding.buttonShopFreeFirst1.setOnClickListener {
            println("ShopFreeTest1")
            shopViewModel.purchaseRoyalJelly(400,0,"boldProfile")
        }
        binding.buttonShopFreeFirst2.setOnClickListener {
            println("ShopFreeTest1")
            shopViewModel.purchaseRoyalJelly(300,0,"firstMoment")
        }
        binding.buttonShopFreeCertify1.setOnClickListener {
            println("ShopFreeTest1")
            shopViewModel.purchaseRoyalJelly(15000,0,"std")
        }
        binding.buttonShopFreeCertify2.setOnClickListener {
            println("ShopFreeTest1")
            shopViewModel.purchaseRoyalJelly(15000,0,"hpv")
        }
        binding.buttonShopFreeCertify3.setOnClickListener {
            println("ShopFreeTest1")
            shopViewModel.purchaseRoyalJelly(5000,0,"covid")
        }

        shopViewModel.isProcessBar.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.progressBarShopFree.layoutProgressBar.visibility = View.VISIBLE
            }else{
                binding.progressBarShopFree.layoutProgressBar.visibility = View.GONE
            }

        })
    }

}