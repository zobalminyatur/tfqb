package com.tresfellas.queenbee.ui.main.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tresfellas.queenbee.R
import kotlinx.android.synthetic.main.dialog_chat_more.*

class ChatMoreDialog: BottomSheetDialogFragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_chat_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_chat_more_dialog_turn_off_alarm.setOnClickListener(this)
        layout_chat_more_dialog_block.setOnClickListener(this)
        layout_chat_more_dialog_report.setOnClickListener(this)
        layout_chat_more_dialog_leave_room.setOnClickListener(this)
    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        (parentFragment as VideoFullFragment).playVideo()
//    }

    override fun onClick(view: View) {
        val roomId = requireArguments().getString("roomId")!!
        val userId = requireArguments().getString("userId")!!

        when (view.id) {
            R.id.layout_chat_more_dialog_turn_off_alarm -> {

            }
            R.id.layout_chat_more_dialog_block -> {

            }
            R.id.layout_chat_more_dialog_report -> {

            }
            R.id.layout_chat_more_dialog_leave_room -> {

            }
            else -> {
                this.dismiss()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(roomId: String, userId: String) =
            ChatMoreDialog().apply {
                arguments = Bundle().apply {
                    putString("roomId", roomId)
                    putString("userId", userId)
                }
            }
    }
}