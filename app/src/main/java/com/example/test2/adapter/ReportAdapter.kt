package com.example.test2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.R
import com.example.test2.common.REPORT_TYPE_DAY
import com.example.test2.common.REPORT_TYPE_HOUR
import com.example.test2.common.REPORT_TYPE_WEEK
import com.example.test2.model.Report
import com.example.test2.utils.YYYY_MM_DD_FORMAT
import com.example.test2.utils.YYYY_MM_FORMAT
import com.example.test2.utils.convertTimeStampToString
import kotlinx.android.synthetic.main.item_report.view.*

class ReportAdapter(private val reportType: Int, private val data: List<Report>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_ITEM = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false)
        return when (viewType) {
            TYPE_HEADER -> {
                HeaderViewHolder(view)
            }
            TYPE_ITEM -> {
                ItemViewHolder(view)
            }
            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> {
                TYPE_HEADER
            }
            else -> {
                TYPE_ITEM
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemData = if (position >= 1 && position <= data.size) data[position - 1] else null
        when (holder) {
            is HeaderViewHolder -> {
                holder.bindItem(reportType)
            }
            is ItemViewHolder -> {
                when (reportType) {
                    REPORT_TYPE_HOUR -> {
                        holder.bindHourItem(itemData)
                    }
                    REPORT_TYPE_DAY -> {
                        holder.bindDayItem(itemData)
                    }
                    REPORT_TYPE_WEEK -> {
                        holder.bindWeekItem(itemData)
                    }
                    else -> throw IllegalArgumentException()
                }

            }
            else -> throw IllegalArgumentException()
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(reportType: Int) {
            val ctx = itemView.context
            when (reportType) {
                REPORT_TYPE_HOUR -> {
                    itemView.tv03.visibility = View.VISIBLE
                    itemView.tv01.text = ctx.getString(R.string.time)
                    itemView.tv02.text = ctx.getString(R.string.hour)
                    itemView.tv03.text = ctx.getString(R.string.number_of_enter)
                }
                REPORT_TYPE_DAY -> {
                    itemView.tv03.visibility = View.INVISIBLE
                    itemView.tv01.text = ctx.getString(R.string.date)
                    itemView.tv02.text = ctx.getString(R.string.number_of_enter)
                }
                REPORT_TYPE_WEEK -> {
                    itemView.tv03.visibility = View.VISIBLE
                    itemView.tv01.text = ctx.getString(R.string.month)
                    itemView.tv02.text = ctx.getString(R.string.week)
                    itemView.tv03.text = ctx.getString(R.string.number_of_enter)
                }
                else -> {
                    itemView.tv03.visibility = View.INVISIBLE
                }
            }
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindHourItem(report: Report?) {
            val ctx = itemView.context
            itemView.tv01.visibility = View.VISIBLE
            itemView.tv02.visibility = View.VISIBLE
            itemView.tv03.visibility = View.VISIBLE
            if (report == null) {
                return
            }
            itemView.tv02.text = getHourRange(ctx, report)
            itemView.tv03.text = report.count.toString()
            itemView.tv01.text = convertTimeStampToString(report.time, YYYY_MM_DD_FORMAT)
        }

        fun bindWeekItem(report: Report?) {
            itemView.tv01.visibility = View.VISIBLE
            itemView.tv02.visibility = View.VISIBLE
            itemView.tv03.visibility = View.VISIBLE
            if (report == null) {
                return
            }
            itemView.tv02.text = report.week.toString()
            itemView.tv03.text = report.count.toString()
            itemView.tv01.text = convertTimeStampToString(report.time, YYYY_MM_FORMAT)
        }

        fun bindDayItem(report: Report?) {
            itemView.tv01.visibility = View.VISIBLE
            itemView.tv02.visibility = View.VISIBLE
            itemView.tv03.visibility = View.INVISIBLE
            if (report == null) {
                return
            }
            itemView.tv02.text = report.count.toString()
            itemView.tv01.text = convertTimeStampToString(report.time, YYYY_MM_DD_FORMAT)
        }

        private fun getHourRange(ctx: Context, report: Report): String {
            val startTime = if (report.hour < 10) "0" + report.hour else report.hour.toString()
            return ctx.getString(R.string.report_hour, startTime, startTime)
        }
    }
}
