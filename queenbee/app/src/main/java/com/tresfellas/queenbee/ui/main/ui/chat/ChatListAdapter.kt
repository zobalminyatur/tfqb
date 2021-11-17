package com.tresfellas.queenbee.ui.main.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.data.model.ChatRoomsDTO
import com.tresfellas.queenbee.data.model.ChatsDTO
import com.tresfellas.queenbee.databinding.ItemChatRoomRowBinding

class ChatListAdapter(private val listener : OnAdapterListener) : RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {

    interface OnAdapterListener {
        fun onChatRowClicked(chatRooms : ChatRoomsDTO.ChatRooms)
    }

    class ChatListViewHolder(val binding: ItemChatRoomRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    var items: ArrayList<ChatRoomsDTO.ChatRooms> = arrayListOf()

    fun addChatrooms(chatrooms: ChatRoomsDTO.ChatRooms) {
        items.add(0,chatrooms)
        notifyItemInserted(0)
    }

    fun deleteChatRooms(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_room_row, parent, false)

        return ChatListViewHolder(
            ItemChatRoomRowBinding.bind(view)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun setItem(items: List<ChatRoomsDTO.ChatRooms>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.binding.chatRooms = items[position]
        holder.binding.layoutChatList.setOnClickListener {
            listener.onChatRowClicked(items[position])
        }
    }
}