package com.tresfellas.queenbee.ui.main.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tresfellas.queenbee.databinding.FragmentShopPurchaseBinding

class ShopPurchaseFragment : Fragment() {

    private lateinit var shopViewModel: ShopViewModel
    private var _binding: FragmentShopPurchaseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shopViewModel =
            ViewModelProvider(requireParentFragment()).get(ShopViewModel::class.java)

        _binding = FragmentShopPurchaseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonShopRefillRowPurchase1.setOnClickListener {
            //결제후
            shopViewModel.purchaseRoyalJelly(1000,1000,"purchase")
            println("@@@@@@@JELYYPURCHAsED@@@@@@@@@@@@@")
        }
        binding.buttonShopRefillRowPurchase2.setOnClickListener {
            //결제후
//            shopViewModel.purchaseRoyalJelly(4000)
        }
        binding.buttonShopRefillRowPurchase3.setOnClickListener {
            //결제후
//            shopViewModel.purchaseRoyalJelly(10000)
        }
        shopViewModel.errorStatus.observe(viewLifecycleOwner,Observer{
            Toast.makeText(requireContext(), this.resources.getString(it), Toast.LENGTH_SHORT).show()
        })
        shopViewModel.isProcessBar.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.progressBarShopPurchase.layoutProgressBar.visibility = View.VISIBLE
            }else{
                binding.progressBarShopPurchase.layoutProgressBar.visibility = View.GONE
            }

        })
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}