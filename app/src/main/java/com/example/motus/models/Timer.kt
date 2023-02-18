package com.example.motus.models

import android.os.Handler
import android.os.SystemClock
import android.widget.TextView

class Timer {
    private var startTime: Long = 0
    private var timeInMilliseconds: Long = 0
    private var timerHandler = Handler()
    private var t: TextView? = null

    private val updateTimer = object : Runnable {
        override fun run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime
            val secs = (timeInMilliseconds / 1000).toInt()
            t!!.text = String.format("%02d:%02d", secs / 60, secs % 60)
            timerHandler.postDelayed(this, 0)
        }
    }

    fun start(view: TextView) {
        t = view
        startTime = SystemClock.uptimeMillis()
        timerHandler.postDelayed(updateTimer, 0)
    }

    fun stop() {
        timerHandler.removeCallbacks(updateTimer)
    }
}