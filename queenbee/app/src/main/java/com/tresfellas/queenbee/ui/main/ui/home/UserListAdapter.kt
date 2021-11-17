package com.tresfellas.queenbee.ui.main.ui.home

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.databinding.ItemUserListRowBinding
import kotlin.math.roundToInt

class UserListAdapter(private var item : List<UserDTO>, private val listener : OnAdapterListener)
: RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    class UserListViewHolder(val binding : ItemUserListRowBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener{
        fun onRowClicked(item : UserDTO)
        fun onChatButtonClicked(item : UserDTO)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_list_row,parent,false)

        return UserListViewHolder(ItemUserListRowBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.binding.userDTO = item[position]
        if(item[position].moment==null){
            holder.binding.imageViewUserRowWing.visibility = View.GONE
        }
        val results = FloatArray(3)
        Location.distanceBetween(item[position].location.coordinates[1], item[position].location.coordinates[0], CurrentUserManager.currentUser.location.coordinates[1], CurrentUserManager.currentUser.location.coordinates[0], results)
        meterToDistance(holder.binding.textViewUserRowDistance, results[0])
        holder.binding.layoutUserListRow.setOnClickListener {
            listener.onRowClicked(item[position])
        }
        holder.binding.buttonUserRowChat.setOnClickListener {
            listener.onChatButtonClicked(item[position])
        }
    }

    fun setItem(items: List<UserDTO>){
        this.item = items
        notifyDataSetChanged()
    }

    fun meterToDistance(textView: TextView, distanceMeter: Float?) {
        if (distanceMeter == null) {
            textView.text = "알 수 없음"
            return
        }

        val distanceKm = String.format("%.1f", distanceMeter / 1000).toDouble()
        return if (distanceKm < 1) {
            textView.text = (distanceMeter.roundToInt().toString() + "m")
        } else {
            textView.text = (distanceKm.toString() + "km")
        }
    }
}