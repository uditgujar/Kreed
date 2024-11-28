package com.uditpatidar.kreed.main_package.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.placementadda.vestorgrow.basic.validation.ValidationModel
import com.uditpatidar.kreed.R
import com.uditpatidar.kreed.basic.BaseActivity
import com.uditpatidar.kreed.basic.utils.Utils
import com.uditpatidar.kreed.basic.validation.ResultReturn
import com.uditpatidar.kreed.basic.validation.Validation
import com.uditpatidar.kreed.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : BaseActivity(), View.OnClickListener {
    private var errorValidationModels: MutableList<ValidationModel> = ArrayList()
    private val activity: Activity = this@CreateAccountActivity

    lateinit var binding: ActivityCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.McvCreateAccount.setOnClickListener(this)
        binding.tvSignin.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        if (view == binding.McvCreateAccount) {
            checkValidationTask()
        }else if (view == binding.tvSignin){
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun checkValidationTask() {
        errorValidationModels.clear()

        // Add email validation
        errorValidationModels.add(
            ValidationModel(
                Validation.Type.Empty,
                binding.etName,
                binding.tvErrorName
            )
        )

        errorValidationModels.add(
            ValidationModel(
                Validation.Type.Email,
                binding.etEmail,
                binding.tvErrorEmail
            )
        )

        errorValidationModels.add(
            ValidationModel(
                Validation.Type.Phone,
                binding.etPhoneNum,
                binding.tvErrorPhoneNum
            )
        )

        // Validate inputs
        val validation: Validation? = Validation.instance
        val resultReturn: ResultReturn? = validation?.CheckValidation(activity, errorValidationModels)

        if (resultReturn?.aBoolean == true) {


            Toast.makeText(this, "Signup Successfully", Toast.LENGTH_SHORT).show()



        } else {
            // Show validation error
            resultReturn?.errorTextView?.visibility = View.VISIBLE
            resultReturn?.errorTextView?.text = resultReturn?.errorMessage ?: validation?.errorMessage
            val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.top_to_bottom)
            resultReturn?.errorTextView?.startAnimation(animation)
            validation?.EditTextPointer?.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(validation?.EditTextPointer, InputMethodManager.SHOW_IMPLICIT)
        }
    }

}