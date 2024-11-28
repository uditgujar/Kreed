package com.uditpatidar.kreed.main_package.ui


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.placementadda.vestorgrow.basic.validation.ValidationModel
import com.uditpatidar.kreed.R
import com.uditpatidar.kreed.basic.BaseActivity
import com.uditpatidar.kreed.basic.utils.Utils
import com.uditpatidar.kreed.basic.validation.ResultReturn
import com.uditpatidar.kreed.basic.validation.Validation
import com.uditpatidar.kreed.databinding.ActivityLogInBinding

class LoginActivity : BaseActivity(), View.OnClickListener {
    private var errorValidationModels: MutableList<ValidationModel> = ArrayList()
    private val activity: Activity = this@LoginActivity
    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        binding.mcvRequestOtp.setOnClickListener(this)
        binding.llDontHaveAccount.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view == binding.mcvRequestOtp) {
            checkValidationTask()
        }else if (view == binding.llDontHaveAccount){

             finish()
        }
    }


    private fun checkValidationTask() {
        errorValidationModels.clear()

        // Add email validation
        errorValidationModels.add(
            ValidationModel(
                Validation.Type.Email,
                binding.etEmail,
                binding.tvErrorEmail
            )
        )

        // Validate inputs
        val validation: Validation? = Validation.instance
        val resultReturn: ResultReturn? =
            validation?.CheckValidation(activity, errorValidationModels)

        if (resultReturn?.aBoolean == true) {

            val otp = (100000..999999).random()

            Utils.T(activity, "OTP is $otp")

            val intent = Intent(activity, OtpVerificationActivity::class.java)
            intent.putExtra("otp", otp.toString())

            startActivity(intent)
            finish()
        } else {
            // Show validation error
            resultReturn?.errorTextView?.visibility = View.VISIBLE
            resultReturn?.errorTextView?.text =
                resultReturn?.errorMessage ?: validation?.errorMessage
            val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.top_to_bottom)
            resultReturn?.errorTextView?.startAnimation(animation)
            validation?.EditTextPointer?.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(validation?.EditTextPointer, InputMethodManager.SHOW_IMPLICIT)
        }
    }


}



