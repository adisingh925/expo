package com.anonymous.myapp.widgetprovider

import android.appwidget.AppWidgetProvider
import android.content.Context
import android.appwidget.AppWidgetManager
import android.widget.RemoteViews
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.Typeface
import android.util.Log
import com.anonymous.myapp.R
import com.anonymous.myapp.sharedpreferences.SharedPreferences
import org.json.JSONObject
import java.io.InputStream
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date
import java.util.Locale

class CalenderWidgetSmall : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        SharedPreferences.init(context!!)
        Log.d("CalenderWidgetSmall", "onUpdate")
        appWidgetIds?.forEach { appWidgetId ->
            val views = RemoteViews(context?.packageName, R.layout.calender_widget_small)
            val championship = SharedPreferences.read(appWidgetId.toString(), "")
            if(championship == ""){
                SharedPreferences.write(appWidgetId.toString(), "f1")
            }

            val apiData = SharedPreferences.read("championshipData", "")
            if(apiData != ""){
                val parsedApiData = JSONObject(apiData)
                val championshipArray = parsedApiData.getJSONArray(SharedPreferences.read(appWidgetId.toString(), ""))
                var startDay = ""
                var endDay = ""
                var month = ""
                for(i in 0 until championshipArray.length()){
                    var x = 0
                    val country = championshipArray.getJSONObject(i)
                    val datesArray = country.getJSONArray("dates")
                    for(date in 0 until datesArray.length()) {
                        val dateObject = datesArray.getJSONObject(date)
                        val date = dateObject.getString("date")
                        val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date)

                        if(parsedDate.after(Date())){
                            views.setTextViewText(R.id.country, country.getString("name"))
                            views.setTextViewText(R.id.round, "ROUND ${country.getString("round")}")
                            views.setTextViewText(R.id.other_name, dateObject.getString("name"))
                            val dayOfMonth = SimpleDateFormat("dd", Locale.getDefault()).format(parsedDate)
                            val shortMonthName = SimpleDateFormat("MMM", Locale.getDefault()).format(parsedDate)
                            month = shortMonthName
                            views.setTextViewText(R.id.current_date, "$dayOfMonth $shortMonthName")
                            val time24Hour = SimpleDateFormat("HH:mm", Locale.getDefault()).format(parsedDate)
                            views.setTextViewText(R.id.time, time24Hour)
                            val duration = Duration.between(ZonedDateTime.now(ZoneId.of("UTC")), ZonedDateTime.ofInstant(parsedDate.toInstant(), ZoneId.of("UTC")))

                            // Extract the days, hours, and minutes from the duration
                            val days = duration.toDays()
                            val hours = duration.toHours() % 24
                            val minutes = duration.toMinutes() % 60

                            views.setTextViewText(R.id.days, days.toString())
                            views.setTextViewText(R.id.hours, hours.toString())
                            views.setTextViewText(R.id.mins, minutes.toString())
                            x = 1
                        }

                        if(x == 1){
                            startDay = SimpleDateFormat("dd", Locale.getDefault()).format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(datesArray.getJSONObject(0).getString("date")))
                            endDay = SimpleDateFormat("dd", Locale.getDefault()).format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(datesArray.getJSONObject(datesArray.length() - 1).getString("date")))
                            break
                        }
                    }

                    if(x == 1){
                        views.setTextViewText(R.id.date_range, "$startDay - $endDay $month")
                        break
                    }
                }
            }
            appWidgetManager?.updateAppWidget(appWidgetId, views)
        }
    }
}