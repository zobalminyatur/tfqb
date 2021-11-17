package com.tresfellas.queenbee.ui.main.ui.editsecretprofile

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.data.model.QuestionnaireItem
import com.tresfellas.queenbee.data.model.RightNowDTO
import com.tresfellas.queenbee.data.model.SecretProfileDTO
import com.tresfellas.queenbee.databinding.ItemEditSecretProfileMultipleRowBinding
import com.tresfellas.queenbee.databinding.ItemRightNowRowBinding
import com.tresfellas.queenbee.ui.main.ui.home.RightNowAdapter

class EditSecretMultipleAdapter(private var item : List<QuestionnaireItem.QuestionChoices>, private val listener : OnAdapterListener)
    : RecyclerView.Adapter<EditSecretMultipleAdapter.EditSecretMultipleViewHolder>() {

    var lastSelectedPosition = -1
    var currentSelectedItemPosition = -1

    class EditSecretMultipleViewHolder(val binding: ItemEditSecretProfileMultipleRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener {
        fun onRowClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditSecretMultipleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_edit_secret_profile_multiple_row, parent, false)

        return EditSecretMultipleViewHolder(ItemEditSecretProfileMultipleRowBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: EditSecretMultipleViewHolder, position: Int) {
        holder.binding.questionChoices = item[position]
        holder.binding.layoutEditSecretProfileMultipleChoice.setOnClickListener {
            listener.onRowClicked(position)
        }
        if(currentSelectedItemPosition==position){
            holder.binding.layoutEditSecretProfileMultipleChoice.setBackgroundResource(R.drawable.round_corner_10_dark_brown)
            holder.binding.textViewEditSecretProfileMultipleChoice.setTextColor(Color.parseColor("#c69f3b"))
        }else{
            holder.binding.layoutEditSecretProfileMultipleChoice.setBackgroundResource(R.drawable.round_corner_10)
            holder.binding.textViewEditSecretProfileMultipleChoice.setTextColor(Color.parseColor("#FF000000"))
        }
    }

    fun setItem(items: List<QuestionnaireItem.QuestionChoices>) {
        this.item = items
        notifyDataSetChanged()
    }

    fun onItemSelectedChanged(){
        notifyItemChanged(lastSelectedPosition)
        notifyItemChanged(currentSelectedItemPosition)
    }
}