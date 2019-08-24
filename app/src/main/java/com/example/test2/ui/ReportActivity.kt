package com.example.test2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.R
import com.example.test2.adapter.ReportAdapter
import com.example.test2.common.INTENT_REPORT_TYPE
import com.example.test2.common.REPORT_TYPE_DAY
import com.example.test2.common.REPORT_TYPE_HOUR
import com.example.test2.common.REPORT_TYPE_WEEK
import com.example.test2.dataservice.ReportTask
import com.example.test2.model.Report
import kotlinx.android.synthetic.main.activity_report.*

class ReportActivity : AppCompatActivity(), ReportTask.ReportTaskResponse {
    override fun onProcessFinish(result: List<Report>?) {
        if (result == null) {
            return
        }
        reports.clear()
        reports.addAll(result)
        adapter?.notifyDataSetChanged()
    }

    private var adapter: ReportAdapter? = null
    private var reports = ArrayList<Report>()

    var reportType = REPORT_TYPE_HOUR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        reportType = intent.getIntExtra(INTENT_REPORT_TYPE, REPORT_TYPE_HOUR)
        title = when (reportType) {
            REPORT_TYPE_HOUR -> {
                getString(R.string.show_hour_report)
            }
            REPORT_TYPE_DAY -> {
                getString(R.string.show_day_report)
            }
            REPORT_TYPE_WEEK -> {
                getString(R.string.show_week_report)
            }
            else -> {
                ""
            }
        }

        adapter = ReportAdapter(reportType, reports)
        listReport.layoutManager = LinearLayoutManager(this)
        listReport.adapter = adapter

        ReportTask(this, this, reportType).execute()
    }
}
