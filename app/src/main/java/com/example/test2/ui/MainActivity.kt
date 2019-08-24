package com.example.test2.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.R
import com.example.test2.common.INTENT_REPORT_TYPE
import com.example.test2.common.REPORT_TYPE_DAY
import com.example.test2.common.REPORT_TYPE_HOUR
import com.example.test2.common.REPORT_TYPE_WEEK
import com.example.test2.dataservice.DataDummyTask
import com.example.test2.utils.PREF_IS_FIRST_LAUNCH
import com.example.test2.utils.getPref
import com.example.test2.utils.putPref
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataDummyTask.TaskResponse {
    override fun processFinish(result: Boolean?) {
        if (result == true) {
            putPref(PREF_IS_FIRST_LAUNCH, true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (getPref<Boolean>(PREF_IS_FIRST_LAUNCH) == false) {
            DataDummyTask(this, this).execute()
        }
        btnShowHourReport.setOnClickListener {
            showReportScreen(REPORT_TYPE_HOUR)
        }
        btnShowDayReport.setOnClickListener {
            showReportScreen(REPORT_TYPE_DAY)
        }
        btnShowWeekReport.setOnClickListener {
            showReportScreen(REPORT_TYPE_WEEK)
        }
        btnReImport.setOnClickListener {
            DataDummyTask(this, this).execute()
        }
    }

    private fun showReportScreen(reportType: Int) {
        val intent = Intent(this, ReportActivity::class.java)
        intent.putExtra(INTENT_REPORT_TYPE, reportType)
        startActivity(intent)
    }
}
