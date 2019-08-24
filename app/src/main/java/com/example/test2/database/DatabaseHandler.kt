package com.example.test2.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.test2.model.LogEntry
import com.example.test2.model.Report

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        val sql = String.format(
                "CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT, %s TEXT)",
                TABLE_NAME,
                KEY_ID,
                KEY_WEEK,
                KEY_DATE,
                KEY_HOUR,
                KEY_TIME,
                KEY_NAME,
                KEY_ACTION
        )
        sqLiteDatabase.execSQL(sql)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME)
        sqLiteDatabase.execSQL(sql)
        onCreate(sqLiteDatabase)
    }

    fun insertListLog(logs: List<LogEntry>?): Boolean {
        if (logs.isNullOrEmpty()) {
            return false
        }
        val db = this.writableDatabase
        db.beginTransaction()
        return try {
            val contentValues = ContentValues()
            for (log in logs) {
                contentValues.put(KEY_WEEK, log.week)
                contentValues.put(KEY_DATE, log.date)
                contentValues.put(KEY_HOUR, log.hour)
                contentValues.put(KEY_TIME, log.time)
                contentValues.put(KEY_NAME, log.name)
                contentValues.put(KEY_ACTION, log.action)
                db.insert(TABLE_NAME, null, contentValues)
            }
            db.setTransactionSuccessful()
            db.endTransaction()
            true
        } catch (exc: Exception) {
            db.endTransaction()
            false
        }
    }

    fun getAllLogByHour(): ArrayList<Report> {
        val listLog = ArrayList<Report>()
        val db = this.readableDatabase

        val getContactSql =
                String.format(
                        "select *, count(id) as number from %s WHERE %s = 'ENTER' GROUP BY %s, %s;",
                        TABLE_NAME,
                        KEY_ACTION,
                        KEY_DATE,
                        KEY_HOUR
                )
        val res = db.rawQuery(getContactSql, null)
        res.moveToFirst()
        while (!res.isAfterLast) {
            listLog.add(
                    Report(
                            res.getInt(res.getColumnIndex(KEY_WEEK)),
                            res.getInt(res.getColumnIndex(KEY_DATE)),
                            res.getInt(res.getColumnIndex(KEY_HOUR)),
                            res.getLong(res.getColumnIndex(KEY_TIME)),
                            res.getInt(res.getColumnIndex(KEY_NUMBER))
                    )
            )
            res.moveToNext()
        }
        res.close()
        return listLog
    }

    fun getAllLogByDay(): ArrayList<Report> {
        val listLog = ArrayList<Report>()
        val db = this.readableDatabase

        val getContactSql =
                String.format(
                        "select *, count(id) as number from %s WHERE %s = 'ENTER' GROUP BY %s;",
                        TABLE_NAME,
                        KEY_ACTION,
                        KEY_DATE
                )
        val res = db.rawQuery(getContactSql, null)
        res.moveToFirst()
        while (!res.isAfterLast) {
            listLog.add(
                    Report(
                            res.getInt(res.getColumnIndex(KEY_WEEK)),
                            res.getInt(res.getColumnIndex(KEY_DATE)),
                            res.getInt(res.getColumnIndex(KEY_HOUR)),
                            res.getLong(res.getColumnIndex(KEY_TIME)),
                            res.getInt(res.getColumnIndex(KEY_NUMBER))
                    )
            )
            res.moveToNext()
        }
        res.close()
        return listLog
    }

    fun getAllLogByWeek(): ArrayList<Report> {
        val listLog = ArrayList<Report>()
        val db = this.readableDatabase

        val getContactSql =
                String.format(
                        "select *, count(id) as number from %s WHERE %s = 'ENTER' GROUP BY %s;",
                        TABLE_NAME,
                        KEY_ACTION,
                        KEY_WEEK
                )
        val res = db.rawQuery(getContactSql, null)
        res.moveToFirst()
        while (!res.isAfterLast) {
            listLog.add(
                    Report(
                            res.getInt(res.getColumnIndex(KEY_WEEK)),
                            res.getInt(res.getColumnIndex(KEY_DATE)),
                            res.getInt(res.getColumnIndex(KEY_HOUR)),
                            res.getLong(res.getColumnIndex(KEY_TIME)),
                            res.getInt(res.getColumnIndex(KEY_NUMBER))
                    )
            )
            res.moveToNext()
        }
        res.close()
        return listLog
    }

    companion object {
        private const val DATABASE_NAME = "logData"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "logs"

        private const val KEY_ID = "id"
        private const val KEY_WEEK = "week"
        private const val KEY_DATE = "date"
        private const val KEY_HOUR = "hour"
        private const val KEY_TIME = "time"
        private const val KEY_NAME = "name"
        private const val KEY_ACTION = "action"
        private const val KEY_NUMBER = "number"
    }
}
