package com.dscepointblank.pointblank.notifications


/**
 * Used to Store constants which are used in Notification channel creation and
 * showing notifications
 */


interface NotificationConstants
{
   companion object{
       const val GENERAL_CHANNEL_ID: String = "generalNotifications"
       const val GENERAL_NOTIFICATION_NAME = "General Notifications"
       const val GENERAL_NOTIFICATION_DESCRIPTION = "All notifications occur here disabling this will prevent the app from showing notifications"
   }
}