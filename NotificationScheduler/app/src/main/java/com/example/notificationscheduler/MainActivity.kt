package com.example.notificationscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var jobScheduler: JobScheduler

    lateinit var seekBar: SeekBar

    companion object {
        const val JOB_ID = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar = findViewById(R.id.seekBar)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun scheduleJob(view: View) {
        val netWorkOptions: RadioGroup = findViewById(R.id.networkOptions)
        val selectedNetworkID = netWorkOptions.checkedRadioButtonId
        var selectedNetWorkOption = JobInfo.NETWORK_TYPE_NONE

        when(selectedNetworkID) {
            R.id.noNetwork -> selectedNetWorkOption = JobInfo.NETWORK_TYPE_NONE
            R.id.anyNetwork -> selectedNetWorkOption = JobInfo.NETWORK_TYPE_ANY
            R.id.wifiNetwork -> selectedNetWorkOption = JobInfo.NETWORK_TYPE_UNMETERED
        }

        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val serviceName = ComponentName(packageName, NotificationJobService::class.java.name)
        val builder = JobInfo.Builder(JOB_ID, serviceName).setRequiredNetworkType(selectedNetWorkOption)

        // seek bar
        val seekBarInt = seekBar.progress
        val seekBarSet = seekBarInt > 0
        if (seekBarSet) {
            builder.setMinimumLatency((seekBarInt * 500).toLong())
        }

        val jobInfo = builder.build()
        jobScheduler.schedule(jobInfo)
    }

    fun cancelJobs(view: View) {
        if (jobScheduler != null) {
            jobScheduler.cancelAll()
        }
    }
}
