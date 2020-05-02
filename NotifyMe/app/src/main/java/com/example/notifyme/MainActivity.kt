package com.example.notifyme

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    private lateinit var button_notify: Button
    private lateinit var button_cancel: Button
    private lateinit var button_update: Button

    companion object {
        // Every notification channel must be associated with an ID that is unique
        // within your package. You use this channel ID later, to post your notifications.
        val PRIMARY_CHANNEL_ID = "com.example.notifyme.PRIMARY_CHANNEL_ID"

        // update or cancel the notification in the future
        val NOTIFICATION_ID = 0

        val ACTION_UPDATE_NOTIFICATION = "com.example.notifyme.ACTION_UPDATE_NOTIFICATION"
    }

    private lateinit var notificationManager: NotificationManager

    private lateinit var receiver: NotificationReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_notify = findViewById(R.id.notify)
        button_notify.setOnClickListener {
            sendNotification()
        }

        button_update = findViewById(R.id.update)
        button_update.setOnClickListener {
            updateNotification()
        }

        button_cancel = findViewById(R.id.cancel)
        button_cancel.setOnClickListener {
            cancelNotification()
        }

        createNotificationChannel()
        receiver = NotificationReceiver()
        registerReceiver(receiver, IntentFilter(ACTION_UPDATE_NOTIFICATION))
    }

    private fun sendNotification() {
        val notificationBuilder = getNotificationBuilder()

        // these are for notification option
        val updateIntent = Intent(ACTION_UPDATE_NOTIFICATION)
        val updatePendingIntent = PendingIntent.getBroadcast(
                this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT
        )
        notificationBuilder.addAction(
                R.drawable.ic_update_black_24dp, "Update Notification", updatePendingIntent
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                    PRIMARY_CHANNEL_ID,
                    "Notification",
                    NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notification from NotifyMe"
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun getNotificationBuilder(): NotificationCompat.Builder {
        // use notification to start another activity
        val notificationIntent = Intent(this, AnotherActivity::class.java)
        // By using a PendingIntent to communicate with another app, you are telling that app to
        // execute some predefined code at some point in the future. It's like the other app can
        // perform an action on behalf of your app.
        val notificationPendingIntent = PendingIntent.getActivity(
                this,
                NOTIFICATION_ID,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_notifications_paused_black_24dp) // required
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
    }

    private fun updateNotification() {
        val notificationBuilder = getNotificationBuilder()
        val androidImage = BitmapFactory.decodeResource(resources, R.drawable.ic_palette_black_24dp)
        notificationBuilder.setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Notification Updated!")
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    inner class NotificationReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent?) {
            // this is only called when the user clicked notification action
            updateNotification()
            Toast.makeText(context, "Received Notification", Toast.LENGTH_LONG).show()
        }
    }
}
