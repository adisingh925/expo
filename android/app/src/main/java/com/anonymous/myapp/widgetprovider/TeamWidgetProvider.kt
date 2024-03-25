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
import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.content.res.ResourcesCompat

class TeamWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        SharedPreferences.init(context!!)
        Log.d("TeamWidgetProvider", "onUpdate")
        appWidgetIds?.forEach { appWidgetId ->
            val views = RemoteViews(context?.packageName, R.layout.team_widget)
            val response: JSONObject = JSONObject()
            val uuid = SharedPreferences.read("team_uuid", "")
            val apiKey = SharedPreferences.read("team_apiKey", "")
            CoroutineScope(Dispatchers.IO).launch {
                val url = URL("https://rcqdwlyzxtexhdlchxhj.supabase.co/rest/v1/rpc/get_team_details_and_stats?team_season_driver_id=$uuid")
                val connection = url.openConnection() as HttpURLConnection

                // Set request method
                connection.requestMethod = "GET"

                // Set request headers
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty(
                    "apikey",
                    apiKey
                )

                // Set connection timeouts
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()

                    println("Response: ${response.toString()}")
                    val parsedApiData = JSONObject(response.toString())
                    val driverArray = parsedApiData.getJSONArray("drivers")
                    var drivers = ""
                    for(i in 0 until driverArray.length()){
                        val driver = driverArray.getString(i)
                        drivers += if (i == 0) {
                            driver // Append the first driver name without the middle dot
                        } else {
                            " · $driver" // Append subsequent driver names with a middle dot separator
                        }
                    }

                    var font = -1
                    var fontSizeOffset = 0
                    var color = Color.parseColor(parsedApiData.getString("color"))

                    when (parsedApiData.getString("championship_code")) {
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

                    views.setImageViewBitmap(
                        R.id.round,
                        buildUpdate(
                            parsedApiData.getString("name").split(" ")[0],
                            context,
                            40f + fontSizeOffset.toFloat(),
                            font,
                            Color.parseColor("#ffffff"),
                            Align.LEFT,
                            0f,
                            300
                        )
                    )

                    views.setImageViewBitmap(
                        R.id.round1,
                        buildUpdate(
                            parsedApiData.getString("name").split(" ")[1],
                            context,
                            40f + fontSizeOffset.toFloat(),
                            font,
                            Color.parseColor("#ffffff"),
                            Align.LEFT,
                            0f,
                            300
                        )
                    )

                    views.setImageViewBitmap(
                        R.id.driver_name,
                        buildUpdate(
                            drivers,
                            context,
                            30f + fontSizeOffset.toFloat(),
                            font,
                            color,
                            Align.LEFT,
                            0f,
                            300
                        )
                    )

                    views.setImageViewBitmap(
                                R.id.position,
                                buildUpdate(
                                    parsedApiData.getString("position"),
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
                                R.id.points,
                                buildUpdate(
                                    parsedApiData.getString("points"),
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
                                R.id.position_text,
                                buildUpdate(
                                    "POS",
                                    context,
                                    20f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#808080"),
                                    Align.CENTER,
                                    50f,
                                    95
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.points_text,
                                buildUpdate(
                                    "PT",
                                    context,
                                    20f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#808080"),
                                    Align.CENTER,
                                    50f,
                                    95
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.pole,
                                buildUpdate(
                                    parsedApiData.getString("pole"),
                                    context,
                                    40f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#ffffff"),
                                    Align.CENTER,
                                    50f,
                                    95
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.podi,
                                buildUpdate(
                                    parsedApiData.getString("podium"),
                                    context,
                                    40f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#ffffff"),
                                    Align.CENTER,
                                    50f,
                                    95
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.wins,
                                buildUpdate(
                                    parsedApiData.getString("win"),
                                    context,
                                    40f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#ffffff"),
                                    Align.CENTER,
                                    50f,
                                    95
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.wins_text,
                                buildUpdate(
                                    "Vittorie",
                                    context,
                                    20f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#808080"),
                                    Align.CENTER,
                                    50f,
                                    95
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.podi_text,
                                buildUpdate(
                                    "Podi",
                                    context,
                                    20f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#808080"),
                                    Align.CENTER,
                                    50f,
                                    95
                                )
                            )

                            views.setImageViewBitmap(
                                R.id.pole_text,
                                buildUpdate(
                                    "Pole",
                                    context,
                                    20f + fontSizeOffset.toFloat(),
                                    font,
                                    Color.parseColor("#808080"),
                                    Align.CENTER,
                                    50f,
                                    95
                                )
                            )

                    
                    appWidgetManager?.updateAppWidget(appWidgetId, views)
                } else {
                    println("HTTP request failed with response code: $responseCode")
                }

                connection.disconnect()
            }
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