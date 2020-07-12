package com.dscepointblank.pointblank.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dscepointblank.pointblank.R


/**
 * A Wrapper class for creating and displaying notifications
 * (Notification channels are created in the {@link utilityClasses.App} class
 *
 *
 * @param context used to create the notification object
 */
class MyNotifications(private var context: Context) {

    private var manager:NotificationManagerCompat = NotificationManagerCompat.from(context)


    /**
     * This method creates the notification object and notifies the user
     *
     * @param title Represent the title of the Notification
     * @param message Represents the description of the Notification
     *
     * @methods "priority" and "setLights" are used for backward functionality post Android OREO these are
     * controlled by the Notification channels
     */
    fun createNotification(title:String,message :String)
    {
        val notification  = NotificationCompat.Builder(context,NotificationConstants.GENERAL_CHANNEL_ID)
        notification.setContentTitle(title)
        notification.setContentText(message)
        notification.setSmallIcon(R.drawable.glow)
        notification.priority = NotificationManagerCompat.IMPORTANCE_DEFAULT
        notification.setLights(0xFFff0000.toInt(),100,100)
        manager.notify(System.currentTimeMillis().toInt(),notification.build())
    }

    fun createNotification(title:String,message :String,intent: PendingIntent)
    {
        val notification  = NotificationCompat.Builder(context,NotificationConstants.GENERAL_CHANNEL_ID)
        notification.setContentTitle(title)
        notification.setContentText(message)
        notification.setSmallIcon(R.drawable.glow)
        notification.setContentIntent(intent)
        notification.priority = NotificationManagerCompat.IMPORTANCE_DEFAULT
        notification.setLights(0xFFff0000.toInt(),100,100)
        manager.notify(System.currentTimeMillis().toInt(),notification.build())
    }
}