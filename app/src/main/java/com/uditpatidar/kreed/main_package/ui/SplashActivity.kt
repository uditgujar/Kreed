package com.uditpatidar.kreed.main_package.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.uditpatidar.kreed.R

import android.content.Intent
import android.os.CountDownTimer
import android.widget.ProgressBar
import androidx.core.content.ContextCompat

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        // Reference the ProgressBar
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        progressBar.max = 100 // Set progress bar max value to 100

        // 5-second countdown (5000 milliseconds)
        val totalTime = 5000L // Total time in milliseconds
        val interval = 50L // Update every 50 milliseconds for smoother progress

        val timer = object : CountDownTimer(totalTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                // Calculate the progress percentage
                val progress = ((totalTime - millisUntilFinished).toFloat() / totalTime * 100).toInt()
                progressBar.progress = progress // Update progress bar
            }

            override fun onFinish() {
                progressBar.progress = 100 // Ensure the progress is complete
                // Navigate to the next activity
                val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        timer.start()
    }
}

