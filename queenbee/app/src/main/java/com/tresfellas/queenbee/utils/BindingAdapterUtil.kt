package com.tresfellas.queenbee.utils

import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.data.model.ChatRoomsDTO
import com.tresfellas.queenbee.data.model.RoyalJellyPromotionsDTO
import com.tresfellas.queenbee.data.model.UserDTO
import org.w3c.dom.Text
import kotlin.math.roundToInt

object BindingAdapterUtil {

    @JvmStatic
    @BindingAdapter("button_state")
    fun setButtonState(appCompatButton: AppCompatButton, content: RoyalJellyPromotionsDTO.AllRoyalJellyPromotions.RoyalJellyPromotion?) {
        println("@@@@@$content")
        if(content==null){
            println("@@@@@CONTENTFALSE")
            appCompatButton.isEnabled = false
        }else{
            println("@@@@@@@@FUCKCONTENTS")
            when(content.state){
                "activated" -> {
                    appCompatButton.isEnabled = true
                    appCompatButton.setText(R.string.receive_free_royal_jelly)
                    appCompatButton.setTextColor(Color.parseColor("#c69f3b"))
                }
                "deactivated" -> {
                    appCompatButton.isEnabled = false
                    appCompatButton.setText(R.string.receive_free_royal_jelly)
                    appCompatButton.setTextColor(Color.parseColor("#80c69f3b"))
                }
                "completed" -> {
                    appCompatButton.isEnabled = false
                    appCompatButton.setText(R.string.received_free_royal_jelly)
                    appCompatButton.setTextColor(Color.parseColor("#80c69f3b"))
                }
            }
        }

    }
    @JvmStatic
    @BindingAdapter("text_state")
    fun setTextViewState(textView: TextView, state: String) {
        when(state){
            "activated" -> {
                textView.setTextColor(Color.parseColor("#000000"))
            }
            "deactivated" -> {
                textView.setTextColor(Color.parseColor("#000000"))
            }
            "completed" -> {
                textView.setTextColor(Color.parseColor("#c1c1c1"))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("set_age")
    fun setAge(textView: TextView, content: Int){
        textView.text = content.toString() + "세"
    }

    @JvmStatic
    @BindingAdapter("check_last_message")
    fun checkLastMessage(
        textView: TextView, chatRooms: ChatRoomsDTO.ChatRooms
    ) {
        if (chatRooms.lastMessage != null) {
            textView.text = chatRooms.lastMessage
        }else{
            if(chatRooms.participant1==CurrentUserManager.currentUser._id){
                textView.text = "아직 채팅을 시작하지 않았습니다."
            }else{
                textView.text = chatRooms.nickName+"님이 채팅방을 열었습니다."
            }
        }
    }

    @JvmStatic
    @BindingAdapter("check_message_unread")
    fun checkMessageUnread(textView: TextView, isUnreadMessage: Boolean){
        if(isUnreadMessage){
            textView.visibility = View.VISIBLE
        }else{
            textView.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("chatTimeMarker")
    fun setAmPmMarker(
        textView: TextView, createdTime: String
    ) {
            val lastReceivedTime = TimeUtil.getTimeFormat(createdTime)
            textView.text = lastReceivedTime
    }

    @JvmStatic
    @BindingAdapter("createdTime")
    fun createdTimeFromNow(
        textView: TextView, createdTime: String?
    ) {
        if (createdTime != null) {
            val timeAgo = TimeUtil.timeAgo(textView.context, createdTime)
            textView.text = timeAgo
        }
    }

    @JvmStatic
    @BindingAdapter("meterToDistance")
    fun meterToDistance(textView: TextView, distanceKm: Double?) {
        if (distanceKm == null) {
            textView.text = "알 수 없음"
            return
        }
        return if (distanceKm < 1) {
            val distanceMeter = String.format("%.1f", distanceKm * 1000).toDouble()
            textView.text = (distanceMeter.roundToInt().toString() + "m")
        } else {
            val distanceSecondDecimal = String.format("%.1f", distanceKm).toDouble()
            textView.text = (distanceSecondDecimal.toString() + "km")
        }
    }

    @JvmStatic
    @BindingAdapter("safetyCheck")
    fun checkSafety(textView: TextView, safetyProfileDTO: ArrayList<UserDTO.SafetyProfileDTO>){
        val std = safetyProfileDTO.find{
            it.title == "std"
        }
        if(std==null){
            textView.text = "검사를 완료하지 않았습니다."

        }else{
            if(std.status == "approved"){
                textView.text = std.createdAt
            }

        }

    }

    @JvmStatic
    @BindingAdapter("setCompatibilityPercent")
    fun setCompatibilityPercent(textView: TextView, compatibilityIndex: Double) {
        val roundToInt = compatibilityIndex.toInt()
        textView.text = "$roundToInt%"
    }

    @JvmStatic
    @BindingAdapter("setProgressFromDouble")
    fun setProgressFromDouble(progressBar: ProgressBar, compatibilityIndex: Double) {
        val roundToInt = compatibilityIndex.toInt()
        progressBar.progress = roundToInt
    }

    @JvmStatic
    @BindingAdapter("setRoyalJellyAmount")
    fun setRoyalJellyAmount(textView: TextView, amount: Int) {
        val amountWithCommas = "%,d".format(amount)
        textView.text = "${amountWithCommas}ml"
    }

    @JvmStatic
    @BindingAdapter("setRoyalJellyCost")
    fun setRoyalJellyCost(textView: TextView, cost: Int) {
        val costWithCommas = "%,d".format(cost)
        textView.text = "₩$costWithCommas"
    }

    @JvmStatic
    @BindingAdapter("purchaseHistoryDate")
    fun purchaseHistoryDate(
        textView: TextView, createdTime: String
    ) {
        val purchaseHistoryDate = TimeUtil.getDayFormat(createdTime)
        textView.text = purchaseHistoryDate
    }

}