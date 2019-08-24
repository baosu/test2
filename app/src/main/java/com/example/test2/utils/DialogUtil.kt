package com.example.test2.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.test2.R


fun Context.createProgressDialog(message: String?): AlertDialog {
    val builder = AlertDialog.Builder(this)
    builder.setCancelable(false)
    val inflater = LayoutInflater.from(this)
    val dialogView = inflater.inflate(R.layout.dialog_progress, null)
    builder.setView(dialogView)
    val tvMessage = dialogView.findViewById<TextView>(R.id.tvMessage)
    if (message.isNullOrBlank()) {
        tvMessage?.visibility = View.GONE
    } else {
        tvMessage?.visibility = View.VISIBLE
        tvMessage?.text = message
    }
    return builder.create()
}