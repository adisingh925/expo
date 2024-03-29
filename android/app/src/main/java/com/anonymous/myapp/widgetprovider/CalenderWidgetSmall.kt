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
import androidx.core.content.res.ResourcesCompat
import java.time.Instant
import java.util.TimeZone
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.LocalDateTime
import android.view.View

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
            if (championship == "") {
                SharedPreferences.write(appWidgetId.toString(), "f1")
            }

            var font = -1
            var color = -1
            var fontSizeOffset = 0

            when (SharedPreferences.read(appWidgetId.toString(), "f1")) {
                "f1" -> {
                    font = R.font.f1_bold
                }

                "motogp" -> {
                    font = R.font.motogp_bold
                    fontSizeOffset += 10
                }

                "wec" -> {
                    font = R.font.wec_bold
                }

                "f2" -> {
                    font = R.font.f2_bold
                }

                "fe" -> {
                    font = R.font.fe_bold
                }

                "f1-academy" -> {
                    font = R.font.tt_bold
                    fontSizeOffset += 10
                }

                else -> {
                    font = R.font.f1_bold
                }
            }

            val apiData = SharedPreferences.read("championshipData", "")
            if (apiData != "") {
                views.setViewVisibility(R.id.sync_data, View.GONE)
                val parsedApiData = JSONObject(apiData)
                val championshipArray = parsedApiData.getJSONArray(SharedPreferences.read(appWidgetId.toString(), ""))
                var startDay = ""
                var endDay = ""
                var month = ""
                for (i in 0 until championshipArray.length()) {
                    var x = 0
                    val country = championshipArray.getJSONObject(i)
                    val datesArray = country.getJSONArray("dates")
                    for (date in 0 until datesArray.length()) {
                        val dateObject = datesArray.getJSONObject(date)
                        val date = dateObject.getString("date")

                        val utcInstant = Instant.parse(date)
                        val defaultTimeZone = TimeZone.getDefault().toZoneId()
                        val localTime = utcInstant.atZone(defaultTimeZone).toLocalDateTime()

                        val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date)

                        if (localTime.isAfter(LocalDateTime.now())) {
                            color = Color.parseColor(country.getString("color"))
                            views.setImageViewBitmap(
                                R.id.country,
                                buildUpdate(
                                    country.getString("name"),
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    color,
                                    Align.LEFT,
                                    0f,
                                    350
                                )
                            )
                            views.setImageViewBitmap(
                                R.id.round,
                                buildUpdate(
                                    "ROUND ${country.getString("round")}",
                                    context,
                                    25f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#ffffff"),
                                    Align.LEFT,
                                    0f,
                                    160
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.other_name,
                                buildUpdate(
                                    dateObject.getString("name"),
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#ffffff"),
                                    Align.LEFT,
                                    0f,
                                    200
                                )
                            )

                            val dayOfMonth = localTime.dayOfMonth
                            val shortMonthName = localTime.month.getDisplayName(
                                TextStyle.SHORT,
                                Locale.getDefault()
                            )
                            month = shortMonthName
                            views.setImageViewBitmap(
                                R.id.current_date,
                                buildUpdate(
                                    "$dayOfMonth $shortMonthName",
                                    context,
                                    30f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#ffffff"),
                                    Align.RIGHT,
                                    200f,
                                    210
                                )
                            )
                            val time24Hour = "${localTime.hour.toString().padStart(2, '0')}:${localTime.minute.toString().padStart(2, '0')}"
                            views.setImageViewBitmap(
                                R.id.time,
                                buildUpdate(
                                    time24Hour,
                                    context,
                                    30f + fontSizeOffset.toFloat(),
                                    font,
                                    color,
                                    Align.RIGHT,
                                    170f,
                                    175
                                )
                            )

                            val duration = Duration.between(
                                ZonedDateTime.now(),
                                ZonedDateTime.of(localTime, ZoneId.systemDefault())
                            )

                            // Extract the days, hours, and minutes from the duration
                            val days = duration.toDays()
                            val hours = duration.toHours() % 24
                            val minutes = duration.toMinutes() % 60

                            views.setImageViewBitmap(
                                R.id.days,
                                buildUpdate(
                                    days.toString(),
                                    context,
                                    40f + fontSizeOffset.toFloat(),
                                    font,
                                    color,
                                    Align.CENTER,
                                    50f,
                                    95
                                )
                            )
                            views.setImageViewBitmap(
                                R.id.hours,
                                buildUpdate(
                                    hours.toString(),
                                    context,
                                    40f + fontSizeOffset.toFloat(),
                                    font,
                                    color,
                                    Align.CENTER,
                                    50f,
                                    95
                                )
                            )
                            views.setImageViewBitmap(
                                R.id.mins,
                                buildUpdate(
                                    minutes.toString(),
                                    context,
                                    40f + fontSizeOffset.toFloat(),
                                    font,
                                    color,
                                    Align.CENTER,
                                    50f,
                                    95
                                )
                            )
                            views.setImageViewBitmap(
                                R.id.mins_heading,
                                buildUpdate(
                                    "MINUTI",
                                    context,
                                    25f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#ffffff"),
                                    Align.CENTER,
                                    70f,
                                    135
                                )
                            )
                            views.setImageViewBitmap(
                                R.id.hours_heading,
                                buildUpdate(
                                    "ORE",
                                    context,
                                    25f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#ffffff"),
                                    Align.CENTER,
                                    50f,
                                    95
                                )
                            )
                            views.setImageViewBitmap(
                                R.id.days_heading,
                                buildUpdate(
                                    "GIORNI",
                                    context,
                                    25f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#ffffff"),
                                    Align.CENTER,
                                    60f,
                                    120
                                )
                            )
                            x = 1
                        }

                        if (x == 1) {
                            startDay = SimpleDateFormat("dd", Locale.getDefault()).format(
                                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(
                                    datesArray.getJSONObject(
                                        0
                                    ).getString("date")
                                )
                            )
                            endDay = SimpleDateFormat("dd", Locale.getDefault()).format(
                                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(
                                    datesArray.getJSONObject(
                                        datesArray.length() - 1
                                    ).getString("date")
                                )
                            )
                            break
                        }
                    }

                    if (x == 1) {
                        views.setImageViewBitmap(
                            R.id.date_range,
                            buildUpdate(
                                "$startDay - $endDay $month",
                                context,
                                25f + fontSizeOffset.toFloat(),
                                font,
                                Color.parseColor("#ffffff"),
                                Align.RIGHT,
                                210f,
                                210
                            )
                        )
                        break
                    }
                }
            }else{
                views.setViewVisibility(R.id.sync_data, View.VISIBLE)
            }
            appWidgetManager?.updateAppWidget(appWidgetId, views)
        }
    }

    fun buildUpdate(
        time: String?,
        context: Context,
        textSize: Float,
        font: Int,
        color: Int,
        alignment: Paint.Align,
        x: Float,
        width: Int
    ): Bitmap {
        val myBitmap = Bitmap.createBitmap(width, 44, Bitmap.Config.ARGB_8888)
        val myCanvas = Canvas(myBitmap)
        val paint = Paint()
        val clock = ResourcesCompat.getFont(context, font);
        paint.isAntiAlias = true
        paint.isSubpixelText = true
        paint.setTypeface(clock)
        paint.style = Paint.Style.FILL
        paint.setColor(color)
        paint.textSize = textSize
        paint.textAlign = alignment
        myCanvas.drawText(time!!, x, 40f, paint)
        return myBitmap
    }
}