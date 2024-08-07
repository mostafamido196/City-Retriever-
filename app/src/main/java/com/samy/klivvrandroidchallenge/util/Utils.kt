package com.samy.klivvrandroidchallenge.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.content.Context.MODE_PRIVATE
import android.content.Intent.*
import android.content.res.Configuration
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.Html
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import java.math.RoundingMode
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import kotlin.math.pow


object Utils {

    public fun myLog( value:String) {
            Log.d("hamoly", "$value")

    }

    fun toast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }



}