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

class CalenderWidgetLarge : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        SharedPreferences.init(context!!)
        Log.d("CalenderWidgetLarge", "onUpdate")
        appWidgetIds?.forEach { appWidgetId ->
            val views = RemoteViews(context?.packageName, R.layout.calender_widget_large)
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
                val parsedApiData = JSONObject(apiData)
                val championshipArray =
                    parsedApiData.getJSONArray(SharedPreferences.read(appWidgetId.toString(), ""))
                var startDay = ""
                var endDay = ""
                var month = ""
                for (i in 0 until championshipArray.length()) {
                    var x = 0
                    val country = championshipArray.getJSONObject(i)
                    val datesArray = country.getJSONArray("dates")
                    for (date in 0 until datesArray.length()) {
                        val dateObject = datesArray.getJSONObject(date)
                        val date1 = dateObject.getString("date")
                        val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date1)
                        val dayOfMonth = SimpleDateFormat("dd", Locale.getDefault()).format(parsedDate)
                        val time24Hour = SimpleDateFormat("HH:mm", Locale.getDefault()).format(parsedDate)

                        val locale = Locale("it", "IT")
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale)
                        val parsedDate1 = dateFormat.parse(date1)
                        val formattedDate = SimpleDateFormat("EEE", locale).format(parsedDate1).toUpperCase(Locale.getDefault())

                        if (date == 0) {
                            views.setImageViewBitmap(
                                R.id.next1_date,
                                buildUpdate(
                                    dayOfMonth.toString(),
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#4972b4"),
                                    Align.LEFT,
                                    0f,
                                    300
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.next1_other_name,
                                buildUpdate(
                                    dateObject.getString("name"),
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    color,
                                    Align.RIGHT,
                                    200f,
                                    210
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.next1_name,
                                buildUpdate(
                                    formattedDate,
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#4972b4"),
                                    Align.LEFT,
                                    0f,
                                    300
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.next1_time,
                                buildUpdate(
                                    time24Hour,
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#4972b4"),
                                    Align.RIGHT,
                                    200f,
                                    210
                                )
                            )
                        }

                        if (date == 1) {
                            views.setImageViewBitmap(
                                R.id.next2_date,
                                buildUpdate(
                                    dayOfMonth.toString(),
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#4972b4"),
                                    Align.LEFT,
                                    0f,
                                    300
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.next2_other_name,
                                buildUpdate(
                                    dateObject.getString("name"),
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    color,
                                    Align.RIGHT,
                                    200f,
                                    210
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.next2_name,
                                buildUpdate(
                                    formattedDate,
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#4972b4"),
                                    Align.LEFT,
                                    0f,
                                    300
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.next2_time,
                                buildUpdate(
                                    time24Hour,
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#4972b4"),
                                    Align.RIGHT,
                                    200f,
                                    210
                                )
                            )
                        }

                        if (date == 2) {
                            views.setImageViewBitmap(
                                R.id.next3_date,
                                buildUpdate(
                                    dayOfMonth.toString(),
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#4972b4"),
                                    Align.LEFT,
                                    0f,
                                    300
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.next3_other_name,
                                buildUpdate(
                                    dateObject.getString("name"),
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    color,
                                    Align.RIGHT,
                                    200f,
                                    210
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.next3_name,
                                buildUpdate(
                                    formattedDate,
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#4972b4"),
                                    Align.LEFT,
                                    0f,
                                    300
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.next3_time,
                                buildUpdate(
                                    time24Hour,
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#4972b4"),
                                    Align.RIGHT,
                                    200f,
                                    210
                                )
                            )
                        }

                        if (date == 3) {
                            views.setImageViewBitmap(
                                R.id.next4_date,
                                buildUpdate(
                                    dayOfMonth.toString(),
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#4972b4"),
                                    Align.LEFT,
                                    0f,
                                    300
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.next4_other_name,
                                buildUpdate(
                                    dateObject.getString("name"),
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    color,
                                    Align.RIGHT,
                                    200f,
                                    210
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.next4_name,
                                buildUpdate(
                                    formattedDate,
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#4972b4"),
                                    Align.LEFT,
                                    0f,
                                    300
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.next4_time,
                                buildUpdate(
                                    time24Hour,
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#4972b4"),
                                    Align.RIGHT,
                                    200f,
                                    210
                                )
                            )
                        }

                        if (parsedDate.after(Date())) {
                            color = Color.parseColor(country.getString("color"))
                            views.setImageViewBitmap(
                                R.id.country,
                                buildUpdate(
                                    country.getString("name"),
                                    context,
                                    45f + fontSizeOffset.toFloat(),
                                    font,
                                    color,
                                    Align.LEFT,
                                    0f,
                                    300
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

                            val shortMonthName = SimpleDateFormat("MMM", Locale.getDefault()).format(parsedDate)
                            month = shortMonthName
                            views.setImageViewBitmap(
                                R.id.current_date,
                                buildUpdate(
                                    "$dayOfMonth $shortMonthName",
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#ffffff"),
                                    Align.RIGHT,
                                    200f,
                                    210
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.time,
                                buildUpdate(
                                    time24Hour,
                                    context,
                                    35f + fontSizeOffset.toFloat(),
                                    font,
                                    color,
                                    Align.RIGHT,
                                    700f,
                                    705
                                )
                            )

                            val duration = Duration.between(
                                ZonedDateTime.now(ZoneId.of("UTC")),
                                ZonedDateTime.ofInstant(parsedDate.toInstant(), ZoneId.of("UTC"))
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
                                    45f + fontSizeOffset.toFloat(),
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
                                    45f + fontSizeOffset.toFloat(),
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
                                    45f + fontSizeOffset.toFloat(),
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