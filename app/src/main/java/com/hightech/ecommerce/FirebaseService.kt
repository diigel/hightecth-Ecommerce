package com.hightech.ecommerce

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hightech.ecommerce.ui.activity.MainActivity

class FirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]
        val millis = System.currentTimeMillis()

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(this, "HT-ECOMMERCE")
            .setSmallIcon(R.drawable.ic_trolli)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("type", "broadcast")
        }

        val pendingIntentNotification =
            PendingIntent.getActivity(
                this,
                millis.toInt(),
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

        builder.setContentIntent(pendingIntentNotification)
        notificationManager.notify(millis.toInt(), builder.build())
    }
}