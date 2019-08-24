package com.example.test2.model


data class LogEntry(val week: Int, val date: Int, val hour: Int, val time: Long, val name: String, val action: String) {
    companion object {
        const val ENTER = "ENTER"
        const val LEAVE = "LEAVE"
    }
}
