package com.dscepointblank.pointblank.utilityClasses

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.dscepointblank.pointblank.notifications.NotificationConstants


/**
 * Class is executed each time the app runs it is used to create the notification channels
 * (Needed in Android O) at the start of the application
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    /**
     * creates a Notification Channel name "General Notifications"
     * this is the default channel to publish all notifications from the app
     */

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (manager.getNotificationChannel(NotificationConstants.GENERAL_CHANNEL_ID) == null) {
                val generalNotificationChannel = NotificationChannel(
                    NotificationConstants.GENERAL_CHANNEL_ID,
                    NotificationConstants.GENERAL_NOTIFICATION_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )

                generalNotificationChannel.description =
                    NotificationConstants.GENERAL_NOTIFICATION_DESCRIPTION
                generalNotificationChannel.enableLights(true)
                generalNotificationChannel.enableVibration(true)
                manager.createNotificationChannel(generalNotificationChannel)
            }
        }
    }
}