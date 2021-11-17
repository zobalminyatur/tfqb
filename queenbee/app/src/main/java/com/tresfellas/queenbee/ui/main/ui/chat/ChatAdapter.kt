package com.tresfellas.queenbee.ui.main.ui.chat

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.data.model.ChatsDTO
import com.tresfellas.queenbee.databinding.ItemMyChatRowBinding
import com.tresfellas.queenbee.databinding.ItemPartnerChatRowBinding
import com.tresfellas.queenbee.databinding.ItemSelfCheckReportRowBinding
import com.tresfellas.queenbee.databinding.LayoutSelfCheckReportAddRowBinding

class ChatAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class MyChatViewHolder(val binding: ItemMyChatRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    class PartnerChatViewHolder(val binding: ItemPartnerChatRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    var items: ArrayList<ChatsDTO.Messages> = arrayListOf()

    fun addMessage(messages: ChatsDTO.Messages) {
        items.add(0,messages)
        notifyItemInserted(0)
    }

    fun deleteMessage(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].from == CurrentUserManager.currentUser._id) {
            R.layout.item_my_chat_row
        } else R.layout.item_partner_chat_row
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return when (viewType) {
            R.layout.item_my_chat_row -> MyChatViewHolder(binding as ItemMyChatRowBinding)
            else -> PartnerChatViewHolder(binding as ItemPartnerChatRowBinding)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun setItem(items: List<ChatsDTO.Messages>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyChatViewHolder
            -> {
                holder.binding.message = items[position]
            }
            is PartnerChatViewHolder
            -> {
                holder.binding.message = items[position]
            }
        }
    }
}