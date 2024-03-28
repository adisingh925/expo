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
                views.setViewVisibility(R.id.main_layout, View.VISIBLE)
                views.setViewVisibility(R.id.sync_data, View.GONE)
                views.setViewVisibility(R.id.l1, View.GONE)
                views.setViewVisibility(R.id.l2, View.GONE)
                views.setViewVisibility(R.id.l3, View.GONE)
                views.setViewVisibility(R.id.l4, View.GONE)
                views.setViewVisibility(R.id.l5, View.GONE)

                views.setViewVisibility(R.id.rl11, View.GONE)
                views.setViewVisibility(R.id.rl12, View.GONE)
                views.setViewVisibility(R.id.rl21, View.GONE)
                views.setViewVisibility(R.id.rl22, View.GONE)
                views.setViewVisibility(R.id.rl31, View.GONE)
                views.setViewVisibility(R.id.rl32, View.GONE)
                views.setViewVisibility(R.id.rl41, View.GONE)
                views.setViewVisibility(R.id.rl42, View.GONE)
                views.setViewVisibility(R.id.rl51, View.GONE)
                views.setViewVisibility(R.id.rl52, View.GONE)

                val parsedApiData = JSONObject(apiData)
                val championshipArray = parsedApiData.getJSONArray(SharedPreferences.read(appWidgetId.toString(), ""))

                var startDay = ""
                var endDay = ""
                var month = ""
                for (i in 0 until championshipArray.length()) {
                    var x = -1
                    val country = championshipArray.getJSONObject(i)
                    val datesArray = country.getJSONArray("dates")
                    for (date in 0 until datesArray.length()) {
                        val dateObject = datesArray.getJSONObject(date)
                        val date1 = dateObject.getString("date")

                        val utcInstant = Instant.parse(date1)
                        val defaultTimeZone = TimeZone.getDefault().toZoneId()
                        val localTime = utcInstant.atZone(defaultTimeZone).toLocalDateTime()

                        val dayOfMonth = localTime.dayOfMonth
                        val time24Hour = "${
                            localTime.hour.toString().padStart(2, '0')
                        }:${localTime.minute.toString().padStart(2, '0')}"

                        val dayOfWeek = localTime.dayOfWeek
                        val dayOfWeekShort =
                            dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                        val timeZoneLocale = Locale.getDefault(Locale.Category.FORMAT)
                        val formattedDate = dayOfWeekShort.toUpperCase(timeZoneLocale)

                        color = Color.parseColor(country.getString("color"))
                        views.setInt(R.id.l1, "setBackgroundColor", color);
                        views.setInt(R.id.l2, "setBackgroundColor", color);
                        views.setInt(R.id.l3, "setBackgroundColor", color);
                        views.setInt(R.id.l4, "setBackgroundColor", color);
                        views.setInt(R.id.l5, "setBackgroundColor", color);

                        if (localTime.isAfter(LocalDateTime.now())) {
                            if (x == -1) {
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
                                        600
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

                                x = date
                            } else {
                                if ((date - x) == 1) {
                                    views.setViewVisibility(R.id.l1, View.VISIBLE)

                                    views.setViewVisibility(R.id.rl11, View.VISIBLE)
                                    views.setViewVisibility(R.id.rl12, View.VISIBLE)

                                    views.setImageViewBitmap(
                                        R.id.next1_date,
                                        buildUpdate(
                                            dayOfMonth.toString(),
                                            context,
                                            35f + fontSizeOffset.toFloat(),
                                            font,
                                            Color.parseColor("#808080"),
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
                                            Color.parseColor("#ffffff"),
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
                                            Color.parseColor("#808080"),
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
                                            Color.parseColor("#808080"),
                                            Align.RIGHT,
                                            200f,
                                            210
                                        )
                                    )
                                }

                                if ((date - x) == 2) {
                                    views.setViewVisibility(R.id.rl21, View.VISIBLE)
                                    views.setViewVisibility(R.id.rl22, View.VISIBLE)
                                    views.setViewVisibility(R.id.l2, View.VISIBLE)

                                    views.setImageViewBitmap(
                                        R.id.next2_date,
                                        buildUpdate(
                                            dayOfMonth.toString(),
                                            context,
                                            35f + fontSizeOffset.toFloat(),
                                            font,
                                            Color.parseColor("#808080"),
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
                                            Color.parseColor("#ffffff"),
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
                                            Color.parseColor("#808080"),
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
                                            Color.parseColor("#808080"),
                                            Align.RIGHT,
                                            200f,
                                            210
                                        )
                                    )
                                }

                                if ((date - x) == 3) {
                                    views.setViewVisibility(R.id.rl31, View.VISIBLE)
                                    views.setViewVisibility(R.id.rl32, View.VISIBLE)
                                    views.setViewVisibility(R.id.l3, View.VISIBLE)

                                    views.setImageViewBitmap(
                                        R.id.next3_date,
                                        buildUpdate(
                                            dayOfMonth.toString(),
                                            context,
                                            35f + fontSizeOffset.toFloat(),
                                            font,
                                            Color.parseColor("#808080"),
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
                                            Color.parseColor("#ffffff"),
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
                                            Color.parseColor("#808080"),
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
                                            Color.parseColor("#808080"),
                                            Align.RIGHT,
                                            200f,
                                            210
                                        )
                                    )
                                }

                                if ((date - x) == 4) {
                                    views.setViewVisibility(R.id.rl41, View.VISIBLE)
                                    views.setViewVisibility(R.id.rl42, View.VISIBLE)
                                    views.setViewVisibility(R.id.l4, View.VISIBLE)

                                    views.setImageViewBitmap(
                                        R.id.next4_date,
                                        buildUpdate(
                                            dayOfMonth.toString(),
                                            context,
                                            35f + fontSizeOffset.toFloat(),
                                            font,
                                            Color.parseColor("#808080"),
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
                                            Color.parseColor("#ffffff"),
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
                                            Color.parseColor("#808080"),
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
                                            Color.parseColor("#808080"),
                                            Align.RIGHT,
                                            200f,
                                            210
                                        )
                                    )
                                }

                                if ((date - x) == 5) {
                                    views.setViewVisibility(R.id.rl51, View.VISIBLE)
                                    views.setViewVisibility(R.id.rl52, View.VISIBLE)
                                    views.setViewVisibility(R.id.l5, View.VISIBLE)

                                    views.setImageViewBitmap(
                                        R.id.next5_date,
                                        buildUpdate(
                                            dayOfMonth.toString(),
                                            context,
                                            35f + fontSizeOffset.toFloat(),
                                            font,
                                            Color.parseColor("#808080"),
                                            Align.LEFT,
                                            0f,
                                            300
                                        )
                                    )

                                    views.setImageViewBitmap(
                                        R.id.next5_other_name,
                                        buildUpdate(
                                            dateObject.getString("name"),
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
                                        R.id.next5_name,
                                        buildUpdate(
                                            formattedDate,
                                            context,
                                            35f + fontSizeOffset.toFloat(),
                                            font,
                                            Color.parseColor("#808080"),
                                            Align.LEFT,
                                            0f,
                                            300
                                        )
                                    )

                                    views.setImageViewBitmap(
                                        R.id.next5_time,
                                        buildUpdate(
                                            time24Hour,
                                            context,
                                            35f + fontSizeOffset.toFloat(),
                                            font,
                                            Color.parseColor("#808080"),
                                            Align.RIGHT,
                                            200f,
                                            210
                                        )
                                    )
                                }
                            }
                        }
                    }

                    if (x != -1) {
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
            } else {
                views.setViewVisibility(R.id.main_layout, View.GONE)
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