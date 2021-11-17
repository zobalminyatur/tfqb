package com.tresfellas.queenbee.ui.main.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tresfellas.queenbee.data.model.ShopHistoryDTO
import com.tresfellas.queenbee.databinding.FragmentShopHistoryBinding

class ShopHistoryFragment : Fragment() {

    private lateinit var shopViewModel: ShopViewModel
    private lateinit var shopHistoryItems: List<ShopHistoryDTO>
    private lateinit var shopHistoryAdapter: ShopHistoryAdapter
    private var _binding: FragmentShopHistoryBinding? = null

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

        shopViewModel.getRoyalJellyPurchaseHistory(1000,1)

        _binding = FragmentShopHistoryBinding.inflate(inflater, container, false)

        val root: View = binding.root

        initRecyclerView()

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println("@@@@@@@ONVIEWCREATED")
        super.onViewCreated(view, savedInstanceState)
        shopViewModel.royalJellyPurchaseHistoryDTO.observe(viewLifecycleOwner, Observer {
            if(it.purchaseHistory.isEmpty()){
                binding.shopHistoryEmpty.visibility = View.VISIBLE
            }else{
                binding.shopHistoryEmpty.visibility = View.GONE
                shopHistoryAdapter.setItem(it.purchaseHistory)
            }

        })

        shopViewModel.isProcessBar.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.progressBarShopHistory.layoutProgressBar.visibility = View.VISIBLE
            }else{
                binding.progressBarShopHistory.layoutProgressBar.visibility = View.GONE
            }

        })
    }

    override fun onResume() {
        println("@@@@@@@ONRESUME")
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        shopHistoryAdapter = ShopHistoryAdapter(listOf())
        binding.shopHistoryRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = shopHistoryAdapter
        }
    }
}