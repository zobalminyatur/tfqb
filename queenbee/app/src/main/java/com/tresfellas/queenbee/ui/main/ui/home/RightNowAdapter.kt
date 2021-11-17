package com.tresfellas.queenbee.ui.main.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.data.model.RightNowDTO
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.databinding.ItemRightNowRowBinding
import com.tresfellas.queenbee.databinding.ItemUserListRowBinding

class RightNowAdapter(private var item : List<UserDTO>, private val listener : OnAdapterListener)
    : RecyclerView.Adapter<RightNowAdapter.RightNowViewHolder>() {

    class RightNowViewHolder(val binding: ItemRightNowRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener {
        fun onRowClicked(item: UserDTO)
        fun onChatButtonClicked(item: UserDTO)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RightNowViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_right_now_row, parent, false)

        return RightNowViewHolder(ItemRightNowRowBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: RightNowViewHolder, position: Int) {
        holder.binding.userNowDTO = item[position]
        holder.binding.layoutRightNowRow.setOnClickListener {
            listener.onRowClicked(item[position])
        }
        holder.binding.buttonUserRowChat.setOnClickListener {
            listener.onChatButtonClicked(item[position])
        }
    }

    fun setItem(items: List<UserDTO>) {
        this.item = items
        notifyDataSetChanged()
    }
}