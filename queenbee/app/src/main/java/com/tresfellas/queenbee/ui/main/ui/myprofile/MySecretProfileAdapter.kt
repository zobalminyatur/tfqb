package com.tresfellas.queenbee.ui.main.ui.myprofile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.databinding.ItemSecretProfileRowBinding
import com.tresfellas.queenbee.ui.main.ui.profile.SecretProfileAdapter

class MySecretProfileAdapter (private var item : ArrayList<UserDTO.BoldProfileDTO>)
    : RecyclerView.Adapter<MySecretProfileAdapter.MySecretProfileViewHolder>() {

    class MySecretProfileViewHolder(val binding : ItemSecretProfileRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySecretProfileViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_secret_profile_row,parent,false)

        return MySecretProfileViewHolder(ItemSecretProfileRowBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: MySecretProfileViewHolder, position: Int) {
        holder.binding.boldProfileDTO = item[position]

    }

    fun setItem(list: ArrayList<UserDTO.BoldProfileDTO>){
        this.item = list
        notifyDataSetChanged()
    }

}