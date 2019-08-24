package com.example.test2.dataservice

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AlertDialog
import com.example.test2.common.REPORT_TYPE_DAY
import com.example.test2.common.REPORT_TYPE_HOUR
import com.example.test2.common.REPORT_TYPE_WEEK
import com.example.test2.database.DatabaseHandler
import com.example.test2.model.Report
import com.example.test2.utils.createProgressDialog
import com.example.test2.utils.getTimeNoData

class ReportTask(private val ctx: Context, private val delegate: ReportTaskResponse, private val reportType: Int) : AsyncTask<String, String, List<Report>>() {
    private var progressDialog: AlertDialog? = null

    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog = ctx.createProgressDialog("Generate Report")
        progressDialog?.show()
    }

    override fun doInBackground(vararg p0: String?): List<Report>? {
        return when (reportType) {
            REPORT_TYPE_HOUR -> {
                getReportDataByHour()
            }
            REPORT_TYPE_DAY -> {
                getReportDataByDay()
            }
            REPORT_TYPE_WEEK -> {
                getReportDataByWeek()
            }
            else -> {
                null
            }
        }
    }

    override fun onPostExecute(result: List<Report>?) {
        super.onPostExecute(result)
        delegate.onProcessFinish(result)
        progressDialog?.dismiss()
    }

    private fun getReportDataByHour(): List<Report> {
        val myDBHelper = DatabaseHandler(ctx)
        val listData = myDBHelper.getAllLogByHour()
        for (day in 1..31) {
            for (hour in 0..23) {
                var isExist = false
                for (report in listData) {
                    if (report.date == day && report.hour == hour) {
                        isExist = true
                        break
                    }
                }
                if (!isExist) {
                    listData.add(Report(0, day, hour, getTimeNoData(day, hour), 0))
                }
            }
        }
        return listData
    }

    private fun getReportDataByDay(): List<Report> {
        val myDBHelper = DatabaseHandler(ctx)
        val listData = myDBHelper.getAllLogByDay()
        for (day in 1..31) {
            var isExist = false
            for (report in listData) {
                if (report.date == day) {
                    isExist = true
                    break
                }
            }
            if (!isExist) {
                listData.add(Report(0, day, 0, getTimeNoData(day, null), 0))
            }
        }
        return listData
    }

    private fun getReportDataByWeek(): List<Report> {
        val myDBHelper = DatabaseHandler(ctx)
        val listData = myDBHelper.getAllLogByWeek()
        for (week in 1..5) {
            var isExist = false
            for (report in listData) {
                if (report.week == week) {
                    isExist = true
                    break
                }
            }
            if (!isExist) {
                listData.add(Report(week, 0, 0, getTimeNoData(null, null), 0))
            }
        }
        return listData
    }

    interface ReportTaskResponse {
        fun onProcessFinish(result: List<Report>?)
    }
}
