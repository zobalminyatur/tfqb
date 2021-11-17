package com.tresfellas.queenbee.api.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.ui.main.MainActivity


class FirebaseMessagingService : FirebaseMessagingService() {

    var CHANNEL_ID = "chat_message"
    lateinit var notificationCompat: NotificationCompat.Builder
    lateinit var notificationManagerCompat: NotificationManagerCompat

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("PushingPushing", p0)
    }

    fun createNotificationChannel(
        context: Context, importance: Int, showBadge: Boolean,
        name: String
    ) {
        notificationCompat = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo_with_name)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.setShowBadge(showBadge)
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }



    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        if (p0.notification != null) {
            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$p0")
            val noti = p0.notification!!
            val nickName = noti.title
            val data = p0.data
            val message = noti.body
            val userId = data["fromUserId"] as String


            val intent = Intent(baseContext, MainActivity::class.java)
            val bundle = bundleOf("userId" to userId, "nickName" to nickName)
            intent.putExtras(bundle)
//            intent.putExtra("userId", userId)
//            intent.putExtra("nickName",nickName)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val fullScreenPendingIntent = PendingIntent.getActivity(
                baseContext, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            notificationManagerCompat = NotificationManagerCompat.from(this)
            notificationCompat.setContentTitle(nickName)
            notificationCompat.setContentText(message)
            notificationCompat.setContentIntent(fullScreenPendingIntent)
            notificationCompat.setFullScreenIntent(fullScreenPendingIntent, true)

            notificationManagerCompat.notify(0, notificationCompat.build())

        }





    }

}