package com.uditpatidar.kreed.basic.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog

import android.content.Context
import android.content.Intent

import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle

import android.provider.OpenableColumns

import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.uditpatidar.kreed.R


import java.io.ByteArrayOutputStream

import java.util.*


object Utils {
    /*
        @JvmStatic
        fun GetSession(): User {
            return UserDataHelper.instance.list[0]
        }

        @JvmStatic
        fun IS_LOGIN(): Boolean {
            return UserDataHelper.instance.list.size > 0
        }*/

    fun I(cx: Context, startActivity: Class<*>?, data: Bundle?) {
        val i = Intent(cx, startActivity)
        if (data != null) i.putExtras(data)
        cx.startActivity(i)
    }

    @JvmStatic
    val deviceName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else {
                capitalize(manufacturer) + " " + model
            }
        }

    @SuppressLint("Range")
    fun getFileName(uri: Uri, c: Context): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = c.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                if (cursor != null) {
                    cursor.close()
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    fun getWindowWidth(): Int {
        // Calculate window height for fullscreen use

        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun capitalize(s: String?): String {
        if (s == null || s.length == 0) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            first.uppercaseChar().toString() + s.substring(1)
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    fun setWebView(webView: WebView, data: String?) {
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.loadData(data!!, "text/html; charset=utf-8", "UTF-8")
    }



    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }












    fun I_finish(cx: Context, startActivity: Class<*>?, data: Bundle?) {
        val i = Intent(cx, startActivity)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        if (data != null) i.putExtras(data)
        cx.startActivity(i)
    }

    fun I_clear(cx: Context, startActivity: Class<*>?, data: Bundle?) {
        val i = Intent(cx, startActivity)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        if (data != null) i.putExtras(data)
        cx.startActivity(i)
    }

    @JvmStatic
    fun E(msg: String) {

        Log.e("Log.E", msg)
    }







    @SuppressLint("InflateParams")
    fun T(c: Context?, msg: String?) {
        //  val toast = Toast(c)
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show()
        /*   val view = LayoutInflater.from(c).inflate(R.layout.custom_toast, null)
           val textView = view.findViewById<TextView>(R.id.tvText)
           textView.text = msg
           toast.view = view
           toast.duration = Toast.LENGTH_SHORT
           toast.show()*/
    }



    fun getFileDataFromDrawable(drawable: Drawable): ByteArray {
        val bitmap = (drawable as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 12, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

}

