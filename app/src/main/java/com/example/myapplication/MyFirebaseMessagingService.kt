package com.example.myapplication

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    //이 클래스는 Data message 방식일때 동작하게 된다.
    val TAG = "Service"

    //아이디값 즉 토큰의 변동사항이 있으면 실행
    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.d(TAG, s)
        //필요하다면 이 토큰을 자신의 앱서버에 보내서 저장하고 추가 적인 작업을 할 수가 있다.
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage!!.from)
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.notification?.body!!)
        Log.d(TAG, remoteMessage.data["body"].toString())

        sendNotification(remoteMessage)
    }

    private fun sendNotification(remoteMessage: RemoteMessage?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
            //.setContentText(remoteMessage?.notification?.body)
            .setContentText(remoteMessage!!.data["body"])
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(1000,1000,1000,1000,1000))
            .setLights(Color.RED,3000,3000)

        val notificatioManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificatioManager.notify(0, notificationBuilder.build())
    }
}