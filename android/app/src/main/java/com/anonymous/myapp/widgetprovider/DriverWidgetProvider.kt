package com.anonymous.myapp.widgetprovider

import android.appwidget.AppWidgetProvider
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

class DriverWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        SharedPreferences.init(context!!)
        Log.d("TeamWidgetProvider", "onUpdate")
        appWidgetIds?.forEach { appWidgetId ->
            val views = RemoteViews(context?.packageName, R.layout.driver_widget)
            val response: JSONObject = JSONObject()
            CoroutineScope(Dispatchers.IO).launch {
                val url = URL("https://rcqdwlyzxtexhdlchxhj.supabase.co/rest/v1/rpc/get_driver_details_and_position?driver_season_team_id=9e41928d-f87f-48c9-9b9f-ba4969cd6ae2")
                val connection = url.openConnection() as HttpURLConnection

                // Set request method
                connection.requestMethod = "GET"

                // Set request headers
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty(
                    "apikey",
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJjcWR3bHl6eHRleGhkbGNoeGhqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDI2NDI3MzMsImV4cCI6MjAxODIxODczM30.3iDaj744eR1JwzIGkEj50WkOpkMyFLKUjY6NsmGP8kY"
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
                    views.setTextViewText(R.id.name, parsedApiData.getString("name"))
                    views.setTextViewText(R.id.surname, parsedApiData.getString("surname"))
                    views.setTextViewText(R.id.number, parsedApiData.getString("number"))
                    views.setTextViewText(R.id.team, parsedApiData.getString("team"))
                    views.setTextViewText(R.id.position, parsedApiData.getString("position"))
                    views.setTextViewText(R.id.points, parsedApiData.getString("points"))
                    views.setTextViewText(R.id.pole, parsedApiData.getString("pole"))
                    views.setTextViewText(R.id.podi, parsedApiData.getString("podium"))
                    views.setTextViewText(R.id.wins, parsedApiData.getString("win"))
                    appWidgetManager?.updateAppWidget(appWidgetId, views)
                } else {
                    println("HTTP request failed with response code: $responseCode")
                }

                connection.disconnect()
            }
        }
    }
}