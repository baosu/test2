package com.example.test2.dataservice

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AlertDialog
import com.example.test2.database.DatabaseHandler
import com.example.test2.model.LogEntry
import com.example.test2.utils.*
import java.util.*

class DataDummyTask(private val ctx: Context, private val delegate: TaskResponse) : AsyncTask<String, String, Boolean>() {
    private var progressDialog: AlertDialog? = null

    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog = ctx.createProgressDialog("Insert Dummy Data")
        progressDialog?.show()
    }

    override fun doInBackground(vararg args: String): Boolean {
        try {
            val startTime = convertStringToTimeStamp("2016/01/01 00:00:00")
            val endTime = convertStringToTimeStamp("2016/01/31 23:59:59")
            val myDBHelper = DatabaseHandler(ctx)
            val listLog = ArrayList<LogEntry>()
            for (time in startTime..endTime step 60 * 1000) {
                val randomNumber = Random().nextInt()
                listLog.add(
                        LogEntry(
                                getWeekOfMonth(time),
                                getDateOfMonth(time),
                                getHourOfDay(time),
                                time,
                                time.toString(),
                                if (randomNumber % 2 == 0) LogEntry.ENTER else LogEntry.LEAVE
                        )
                )
            }
            return myDBHelper.insertListLog(listLog)
        } catch (e: InterruptedException) {
        }

        return false
    }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
        progressDialog?.dismiss()
        delegate.processFinish(result)
    }

    interface TaskResponse {
        fun processFinish(result: Boolean?)
    }
}
