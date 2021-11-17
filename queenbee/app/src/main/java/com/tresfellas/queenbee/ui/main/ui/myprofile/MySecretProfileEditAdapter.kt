package com.tresfellas.queenbee.ui.main.ui.myprofile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.databinding.ItemSecretProfileRowBinding

class MySecretProfileEditAdapter (private var item : ArrayList<UserDTO.BoldProfileDTO>, private val listener : OnAdapterListener)
    : RecyclerView.Adapter<MySecretProfileEditAdapter.MySecretProfileEditViewHolder>() {

    class MySecretProfileEditViewHolder(val binding : ItemSecretProfileRowBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener{
        fun onRowClicked(item : UserDTO.BoldProfileDTO,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySecretProfileEditViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_secret_profile_row,parent,false)

        return MySecretProfileEditViewHolder(ItemSecretProfileRowBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: MySecretProfileEditViewHolder, position: Int) {
        holder.binding.boldProfileDTO = item[position]
        holder.binding.layoutSecretProfileRow.setOnClickListener {
            listener.onRowClicked(item[position],position)
        }
    }

    fun setItem(list: ArrayList<UserDTO.BoldProfileDTO>){
        this.item = list
        notifyDataSetChanged()
    }

}