package com.tresfellas.queenbee.ui.register

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.databinding.ItemSelfCheckReportRowBinding
import com.tresfellas.queenbee.databinding.LayoutSelfCheckReportAddRowBinding

class SelfCheckReportAdapter(private val listener: OnAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SelfCheckReportViewHolder(val binding: ItemSelfCheckReportRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    class SelfCheckReportAddViewHolder(val binding: LayoutSelfCheckReportAddRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener {
        fun onAddButtonClicked()
        fun onSearchButtonClicked(position: Int)
        fun onDeleteButtonClicked(position: Int)
    }

    var items: ArrayList<Any> = arrayListOf("")


    private var selectedPosition = -1

    fun addReport(bitmap:Bitmap){
        items.add(bitmap)
        notifyDataSetChanged()
    }
    fun deleteReport(position: Int){
        items.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.layout_self_check_report_add_row
            else -> R.layout.item_self_check_report_row
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return when (viewType) {
            R.layout.layout_self_check_report_add_row -> SelfCheckReportAddViewHolder(binding as LayoutSelfCheckReportAddRowBinding)
            else -> SelfCheckReportViewHolder(binding as ItemSelfCheckReportRowBinding)
        }
//        val view = if (viewType == VIEW_BUTTON) {
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.layout_self_check_report_add_row, parent, false)
//        }else{
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_self_check_report_row, parent, false)
//        }
//        return SelfCheckReportViewHolder(ItemSelfCheckReportRowBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return items.size
    }


//        fun setItem(items: List<RightNowDTO>){
//            this.item = items
//            notifyDataSetChanged()
//        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SelfCheckReportAddViewHolder
            -> holder.binding.layoutProfileSelfCheckReportUpload.setOnClickListener {
                listener.onAddButtonClicked()
            }
            is SelfCheckReportViewHolder
            -> {
                holder.binding.imageViewSelfCheckReportRow.setImageBitmap(items[position] as Bitmap)
                if(position == selectedPosition){
                    holder.binding.layoutSelfCheckReportButtons.visibility = View.VISIBLE
                }else{
                    holder.binding.layoutSelfCheckReportButtons.visibility = View.GONE
                }

                holder.binding.imageViewSelfCheckReportRow.setOnClickListener {
                    selectedPosition = position
                    notifyDataSetChanged()
                }
                holder.binding.imageViewSelfCheckReportSearch.setOnClickListener {
                    listener.onSearchButtonClicked(position-1)
                }
                holder.binding.imageViewSelfCheckReportDelete.setOnClickListener {
                    selectedPosition = -1
                    this.deleteReport(position)
                    listener.onDeleteButtonClicked(position-1)
                }
            }
        }
    }
}


