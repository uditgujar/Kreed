package com.uditpatidar.kreed.basic.validation

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.TextView

import com.placementadda.vestorgrow.basic.validation.ValidationModel
import com.uditpatidar.kreed.R
import com.uditpatidar.kreed.basic.utils.Utils.E

import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import io.michaelrocks.libphonenumber.android.Phonenumber.PhoneNumber
import java.util.regex.Pattern

class Validation {
    var EditTextPointer: EditText? = null
    var errorMessage: String? = null
    var phoneNumberUtil: PhoneNumberUtil? = null

    /**
     * Check Validation
     *
     * @param context               Page Reference
     * @param errorValidationModels List of the Field On which we have to check the error
     * @return ResultReturn  return the Data of the Validation
     */
    fun CheckValidation(
        context: Context,
        errorValidationModels: List<ValidationModel>
    ): ResultReturn {
        var validationCheck = false
        phoneNumberUtil = PhoneNumberUtil.createInstance(context)
        var type: Type? = null
        var errorMessage: String? = null
        var parameter: String? = null
        var errorTextView: TextView? = null
        for (validationModel in errorValidationModels) {
            type = validationModel.type
            errorMessage = validationModel.errorMessage
            parameter = validationModel.Parameter
            errorTextView = validationModel.errorTextView
            if (errorTextView != null) {
                errorTextView.visibility = View.GONE
            }
            when (validationModel.type) {
                Type.Phone -> validationCheck =
                    isValidPhoneNumber(context, validationModel.editText)
                Type.Email -> validationCheck = isEmailValid(context, validationModel.editText)
                Type.EmptyString -> validationCheck = isEmptyString(context, validationModel.field)
                Type.Amount -> validationCheck = isValidAmount(context, validationModel.editText)
                Type.AadhaarNumber -> validationCheck =
                    isValidAadhaarNumber(context, validationModel.editText)
                Type.PasswordMatch -> validationCheck =

                    isPasswordMatch(context, validationModel.editText, validationModel.editText1)
                Type.PasswordLength -> validationCheck =
                    isPasswordLengthValid(context, validationModel.editText)
                Type.PasswordStrong -> validationCheck =
                    isPasswordStrong(context, validationModel.editText)
                Type.PAN -> validationCheck = isValidPAN(context, validationModel.editText)
                Type.GST -> validationCheck = isValidGstin(context, validationModel.editText)
                Type.IFSC -> validationCheck = isValidIFSC(context, validationModel.editText)
                Type.Empty -> validationCheck = isEmpty(context, validationModel.editText)
                Type.AccountNumber -> validationCheck =
                    isValidAccountNumber(context, validationModel.editText)

                else -> {}
            }
            if (!validationCheck) {
                break
            }
        }
        return ResultReturn(type, validationCheck, errorMessage, parameter, errorTextView)
    }

    /**
     * Password Strong Validation Method
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private fun isPasswordStrong(context: Context, editText: EditText?): Boolean {
        return if (editText?.text == null || TextUtils.isEmpty(editText.text)) {
            errorMessage = context.getString(R.string.empty_error)
            EditTextPointer = editText
            false
        } else {
            val p = Pattern.compile(
                "^" +
                        "(?=.*[0-9])" +  //at least 1 digit
                        "(?=.*[a-z])" +  //at least 1 lower case letter
                        "(?=.*[A-Z])" +  //at least 1 upper case letter
                        "(?=.*[a-zA-Z])" +  //any letter
                        "(?=.*[@#$%^&+=])" +  //at least 1 special character
                        "(?=\\S+$)" +  //no white spaces
                        ".{6,}" +  //at least 8 characters
                        "$"
            )
            val s = editText.text.toString().trim { it <= ' ' }
            val m = p.matcher(s.trim { it <= ' ' })
            if (m.matches()) {
                true
            } else {
                EditTextPointer = editText
                errorMessage = context.getString(R.string.passwordStrong)
                false
            }
        }
    }

    /**
     * Password Length Validation Method (atleast 6 Char)
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private fun isPasswordLengthValid(context: Context, editText: EditText?): Boolean {
        return if (editText?.text == null || TextUtils.isEmpty(editText.text)) {
            errorMessage = context.getString(R.string.empty_error)
            EditTextPointer = editText
            false
        } else {
            if (editText.text!!.trim().length > 6) {
                true
            } else {
                EditTextPointer = editText
                errorMessage = context.getString(R.string.passwordLength)
                false
            }
        }
    }

    /**
     * Email All Type Validation
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private fun isEmailValid(context: Context, editText: EditText?): Boolean {
        //add your own logic
        return if (TextUtils.isEmpty(editText?.text.toString().trim { it <= ' ' })) {
            EditTextPointer = editText
            errorMessage = context.getString(R.string.empty_error)
            false
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(editText?.text.toString().trim { it <= ' ' })
                    .matches()
            ) {
                true
            } else {
                EditTextPointer = editText
                errorMessage = context.getString(R.string.invalid_email)
                false
            }
        }
    }

    /**
     * is String Empty
     *
     * @param context Page Reference
     * @param string  string To Check
     * @return true/false
     */
    private fun isEmptyString(context: Context, string: String?): Boolean {
        //add your own logic
        return if (string == null || TextUtils.isEmpty(string.trim { it <= ' ' })) {
            errorMessage = context.getString(R.string.empty_error)
            false
        } else {
            true
        }
    }

    /**
     * Mobile All Type Validation
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */



    private fun isValidPhoneNumber(context: Context, editText: EditText?): Boolean {
        return if (editText?.text.isNullOrEmpty()) {
            // If the edit text is empty
            errorMessage = context.getString(R.string.empty_error)
            EditTextPointer = editText
            false
        } else {
            // Regular expression to match Indian phone numbers
            val phoneNumberPattern = "^[789]\\d{9}$"
            val phoneNumber = editText?.text.toString()

            return if (phoneNumber.matches(phoneNumberPattern.toRegex())) {
                true
            } else {
                // If phone number doesn't match the pattern
                EditTextPointer = editText
                errorMessage = context.getString(R.string.enter_a_valid_number)
                false
            }
        }
    }



    /**
     * Check Is Empty
     *
     * @param context Page Reference
     * @param arg     Multiple Edit Text To Check
     * @return true/false
     */
    private fun isEmpty(context: Context, vararg arg: EditText?): Boolean {
        for (editText in arg) {
            if (editText?.text.toString().trim { it <= ' ' }.length <= 0) {
                EditTextPointer = editText
                EditTextPointer!!.requestFocus()
                errorMessage = context.getString(R.string.empty_error)
                return false
            }
        }
        return true
    }

    /**
     * Check Is PasswordMatch
     *
     * @param context      Page Reference
     * @param pass         Edit Text To Check
     * @param confirm_pass Edit Text To Check
     * @return true/false
     */
    private fun isPasswordMatch(context: Context, pass: EditText?, confirm_pass: EditText?): Boolean {
        return if (pass?.text == null || TextUtils.isEmpty(pass.text)) {
            errorMessage = context.getString(R.string.empty_error)
            EditTextPointer = pass
            false
        } else if (confirm_pass?.text == null || TextUtils.isEmpty(confirm_pass.text)) {
            errorMessage = context.getString(R.string.empty_error)
            EditTextPointer = confirm_pass
            false
        } else {
            if (pass.text.toString() != confirm_pass.text.toString()) {
                EditTextPointer = confirm_pass
                errorMessage = context.getString(R.string.password_not_match)
                return false
            }
            true
        }
    }

    /**
     * is Valid Aadhaar Number
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private fun isValidAmount(context: Context, editText: EditText?): Boolean {
        return if (editText?.text == null || TextUtils.isEmpty(editText.text)) {
            errorMessage = context.getString(R.string.empty_error)
            EditTextPointer = editText
            false
        } else {
            val p = Pattern.compile("^(\\d*\\.)?\\d+$")
            val s = editText.text.toString().trim { it <= ' ' }
            val m = p.matcher(s.trim { it <= ' ' })
            if (m.matches()) {
                true
            } else {
                EditTextPointer = editText
                errorMessage = context.getString(R.string.amount_valid)
                false
            }
        }
    }

    /**
     * is Valid Aadhaar Number
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private fun isValidAadhaarNumber(context: Context, editText: EditText?): Boolean {
        return if (editText?.text == null || TextUtils.isEmpty(editText.text)) {
            errorMessage = context.getString(R.string.empty_error)
            EditTextPointer = editText
            false
        } else {
            val p = Pattern.compile("^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$")
            val s = editText.text.toString().replace("....".toRegex(), "$0 ")
            val m = p.matcher(s.trim { it <= ' ' })
            if (m.matches()) {
                true
            } else {
                EditTextPointer = editText
                errorMessage = context.getString(R.string.aadhaar_valid)
                false
            }
        }
    }

    /**
     * is Valid PAN Number
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private fun isValidPAN(context: Context, editText: EditText?): Boolean {
        return if (editText?.text == null || TextUtils.isEmpty(editText.text)) {
            errorMessage = context.getString(R.string.empty_error)
            EditTextPointer = editText
            false
        } else {
            val p = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")
            val m = p.matcher(editText.text.toString())
            if (m.matches()) {
                true
            } else {
                EditTextPointer = editText
                errorMessage = context.getString(R.string.pan_card_valid)
                false
            }
        }
    }

    /**
     * is Valid GSTIN Number
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private fun isValidGstin(context: Context, editText: EditText?): Boolean {
        return if (editText?.text == null || TextUtils.isEmpty(editText.text)) {
            errorMessage = context.getString(R.string.empty_error)
            EditTextPointer = editText
            false
        } else {
            val p = Pattern.compile("\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}")
            val m = p.matcher(editText.text.toString())
            if (m.matches()) {
                true
            } else {
                EditTextPointer = editText
                errorMessage = context.getString(R.string.gstin_number_valid)
                false
            }
        }
    }

    /**
     * is Valid IFSC Code
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private fun isValidIFSC(context: Context, editText: EditText?): Boolean {
        return if (editText?.text == null || TextUtils.isEmpty(editText.text)) {
            errorMessage = context.getString(R.string.empty_error)
            EditTextPointer = editText
            false
        } else {
            val p = Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$")
            val m = p.matcher(editText.text.toString())
            if (m.matches()) {
                true
            } else {
                EditTextPointer = editText
                errorMessage = context.getString(R.string.ifsc_valid)
                false
            }
        }
    }

    /**
     * is Valid Account Number
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private fun isValidAccountNumber(context: Context, editText: EditText?): Boolean {
        return if (editText?.text == null || TextUtils.isEmpty(editText.text)) {
            errorMessage = context.getString(R.string.empty_error)
            EditTextPointer = editText
            false
        } else {
            val p = Pattern.compile("[0-9]{9,18}")
            val m = p.matcher(editText.text.toString())
            if (m.matches()) {
                true

            } else {
                EditTextPointer = editText

                errorMessage = context.getString(R.string.account_valid)

                false
            }
        }
    }

/*
    fun validateMobileNumber(phoneNo: String): Boolean {
        val phonenumber: PhoneNumber

        val regionalCode = getCountryKey()

        E("regionalCode::$regionalCode")
        val NationalPhoneNumber: String
        try {
            phonenumber = phoneNumberUtil!!.parse(phoneNo, regionalCode)
            NationalPhoneNumber = phonenumber.nationalNumber.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return if (NationalPhoneNumber == phoneNo) {
            phoneNumberUtil!!.isValidNumber(phonenumber)
        } else {
            false
        }
    }
*/

    /**
     * Enum of the Type of error we have
     */
    enum class Type(var label: String) {
        Email(""), Phone(""), EmptyString(""), Amount(""), AadhaarNumber(""), PasswordMatch(""), PasswordStrong(
            ""
        ),
        PAN(""), IFSC(""), Empty(""), AccountNumber(""),GST(""), PasswordLength("");
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var validation: Validation? = null

        /**
         * Singleton Object of the Class to access
         */
        val instance: Validation?
            get() {
                if (validation == null) validation = Validation()
                return validation
            }
    }
}