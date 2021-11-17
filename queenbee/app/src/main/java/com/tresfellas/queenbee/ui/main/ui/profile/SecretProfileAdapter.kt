package com.tresfellas.queenbee.ui.main.ui.profile

import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.databinding.ItemSecretProfileRowBinding
import com.tresfellas.queenbee.databinding.ItemUserListRowBinding
import com.tresfellas.queenbee.ui.main.ui.home.UserListAdapter
import kotlin.math.roundToInt

class SecretProfileAdapter (private var item : UserDTO)
    : RecyclerView.Adapter<SecretProfileAdapter.SecretProfileViewHolder>() {

    class SecretProfileViewHolder(val binding : ItemSecretProfileRowBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener{
        fun onRowClicked(item : UserDTO)
        fun onChatButtonClicked(item : UserDTO)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecretProfileViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_secret_profile_row,parent,false)

        return SecretProfileViewHolder(ItemSecretProfileRowBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return item.boldProfile.size
    }

    override fun onBindViewHolder(holder: SecretProfileViewHolder, position: Int) {
        holder.binding.boldProfileDTO = item.boldProfile[position]
    }

    fun setItem(userDTO: UserDTO){
        this.item = userDTO
        notifyDataSetChanged()
    }

}