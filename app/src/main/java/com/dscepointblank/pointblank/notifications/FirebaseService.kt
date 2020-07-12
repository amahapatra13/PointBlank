package com.dscepointblank.pointblank.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notification = MyNotifications(baseContext.applicationContext)
        notification.createNotification(message.data["title"]!!,message.data["message"]!!)
    }
}