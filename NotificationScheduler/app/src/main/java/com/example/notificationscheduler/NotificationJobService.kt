package com.example.notificationscheduler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat

class NotificationJobService : JobService() {

    lateinit var notifyManager: NotificationManager

    companion object {
        const val PRIMARY_CHANNEL_ID = "com.example.notificationscheduler.PRIMARY_CHANNEL_ID"
    }

    /**
     * launches your app's MainActivity. This intent is the content intent for your notification.
     * This method is running in main thread
     * */
    override fun onStartJob(params: JobParameters?): Boolean {
        createNotificationChannel()
        // Set up the notification content intent to launch the app when clicked
        val contentPendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat
            .Builder(this, PRIMARY_CHANNEL_ID)
            .setContentTitle("Job Service")
            .setContentText("Your job ran to completion!")
            .setContentIntent(contentPendingIntent)
            .setSmallIcon(R.drawable.ic_autorenew_black_24dp)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)

        notifyManager.notify(0, builder.build())
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        // Make sure that onStopJob() returns true, because if the job fails,
        // you want the job to be rescheduled instead of dropped.
        return true
    }

    private fun createNotificationChannel() {
        notifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Job Service Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notifications from Job Service"

            notifyManager.createNotificationChannel(notificationChannel)
        }
    }
}