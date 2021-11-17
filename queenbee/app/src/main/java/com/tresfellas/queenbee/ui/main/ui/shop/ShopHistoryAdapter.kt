package com.tresfellas.queenbee.ui.main.ui.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.data.model.RoyalJellyPurchase
import com.tresfellas.queenbee.data.model.ShopHistoryDTO
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.databinding.ItemShopHistoryRowBinding

class ShopHistoryAdapter (
    private var items: List<RoyalJellyPurchase>
) : RecyclerView.Adapter<ShopHistoryAdapter.ShopHistoryViewHolder>() {

    class ShopHistoryViewHolder(val binding: ItemShopHistoryRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shop_history_row, parent, false)

        return ShopHistoryViewHolder(ItemShopHistoryRowBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ShopHistoryViewHolder, position: Int) {
        holder.binding.shopHistoryItem = items[position]
    }

    fun setItem(items: List<RoyalJellyPurchase>) {
        this.items = items
        notifyDataSetChanged()
    }
}