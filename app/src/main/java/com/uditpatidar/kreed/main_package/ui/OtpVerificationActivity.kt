package com.uditpatidar.kreed.main_package.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.uditpatidar.kreed.R
import com.uditpatidar.kreed.basic.BaseActivity
import com.uditpatidar.kreed.basic.utils.Utils
import com.uditpatidar.kreed.databinding.ActivityOtpVerificationBinding


class OtpVerificationActivity : BaseActivity() {

    private lateinit var resendOtpTimer: CountDownTimer
    private var isOtpSent = false // To track whether OTP is generated and timer is running
    private lateinit var receivedOtp: String  // To store the received OTP

    private lateinit var binding: ActivityOtpVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOtpVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example OTP received through Intent (for demonstration)
        receivedOtp = intent.getStringExtra("otp").toString()

        // OTP Verify button click listener
        binding.McvSignIn.setOnClickListener {
            validateOtp()
        }

        // Resend OTP button click listener
        binding.tvResendCode.setOnClickListener {
            if (!isOtpSent) {
                // Generate a new OTP
                generateNewOtp()
                startOtpResendTimer()
                isOtpSent = true
            } else {

                Utils.T(this@OtpVerificationActivity,"Please wait before resending the OTP.")


            }
        }
    }

    // Function to generate a new random OTP
    private fun generateNewOtp() {
        // Generate a random 6-digit OTP
        val newOtp = (100000..999999).random().toString()

        Utils.T(this@OtpVerificationActivity,"New OTP generated: $newOtp")

        // Show a toast with the new OTP (you can replace this with your actual OTP generation logic)

        // Store the generated OTP for later verification
        receivedOtp = newOtp
    }

    // Function to start the 30-second countdown timer
    private fun startOtpResendTimer() {
        resendOtpTimer = object : CountDownTimer(30000, 1000) { // 30 seconds timer
            override fun onTick(millisUntilFinished: Long) {
                // Disable the "Resend Code" TextView and update the text with remaining time
                binding.tvResendCode.text = "Resend Code in ${millisUntilFinished / 1000}s"
                binding.tvResendCode.isEnabled = false
            }

            override fun onFinish() {
                // Re-enable the "Resend Code" TextView after 30 seconds
                binding.tvResendCode.text = "Resend Code"
                binding.tvResendCode.isEnabled = true
                isOtpSent = false // Reset OTP sent status
            }
        }.start()
    }

    // Function to validate the OTP entered by the user
    private fun validateOtp() {
        val enteredOtp = binding.PinView.text.toString()

        // Check if OTP field is empty
        if (enteredOtp.isEmpty()) {
            binding.tvErrorPin.visibility = View.VISIBLE
            binding.tvErrorPin.text = getString(R.string.empty_error)
        }
        else if (enteredOtp.length < 6) {
            binding.tvErrorPin.visibility = View.VISIBLE
            binding.tvErrorPin.text = getString(R.string.invalid_pin_length_error)
        } else {
            // Check if the entered OTP matches the received OTP
            if (enteredOtp == receivedOtp) {
                // OTP matches
                binding.tvErrorPin.visibility = View.GONE
  Utils.T(this@OtpVerificationActivity,"OTP Verified Successfully")


            } else {

                binding.tvErrorPin.visibility = View.GONE

                Utils.T(this@OtpVerificationActivity,"Invalid OTP. Please try again.")

            }
        }
    }

    // If you want to stop the timer when the activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
        resendOtpTimer.cancel() // Cancel the timer if activity is destroyed
    }
}
