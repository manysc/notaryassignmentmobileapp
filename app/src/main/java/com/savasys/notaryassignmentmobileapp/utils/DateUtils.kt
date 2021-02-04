package com.savasys.notaryassignmentmobileapp.utils

import java.util.*

class DateUtils {

    companion object {
        private fun getNumberedMonth(month: String): String {
            val numberedMonth: String
            when (month) {
                "Jan" -> {
                    numberedMonth = "01"
                }
                "Feb" -> {
                    numberedMonth = "02"
                }
                "Mar" -> {
                    numberedMonth = "03"
                }
                "Apr" -> {
                    numberedMonth = "04"
                }
                "May" -> {
                    numberedMonth = "05"
                }
                "Jun" -> {
                    numberedMonth = "06"
                }
                "Jul" -> {
                    numberedMonth = "07"
                }
                "Aug" -> {
                    numberedMonth = "08"
                }
                "Sep" -> {
                    numberedMonth = "09"
                }
                "Oct" -> {
                    numberedMonth = "10"
                }
                "Nov" -> {
                    numberedMonth = "11"
                }
                "Dec" -> {
                    numberedMonth = "12"
                }
                else -> {
                    throw Exception("Unsupported month: $month")
                }
            }

            return numberedMonth
        }

        fun getDateTimeFromLocalDateFormat(date: Date): String {
            // Parse Local Date, i.e. Fri Oct 30 07:02:03 MST 2020
            val dateToArray = date.toString().split(' ')
            val month = getNumberedMonth(dateToArray[1])
            val day = dateToArray[2]
            val year = dateToArray[5]
            val timeToArray = dateToArray[3].split(':')
            val hour = timeToArray[0]
            val minute = timeToArray[1]
            //MM/dd/yyyy hh:mm
            return "$month/${day}/$year $hour:$minute"
        }
    }
}