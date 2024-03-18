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

class CalenderWidgetSmall : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        SharedPreferences.init(context!!)
        Log.d("CalenderWidgetSmall", "onUpdate")
        appWidgetIds?.forEach { appWidgetId ->
            val isExist = SharedPreferences.read(appWidgetId.toString(), "")
            if(isExist == ""){
                SharedPreferences.write(appWidgetId.toString(), "f1")
            }

            val apiData = SharedPreferences.read("championshipData", "")
            if(apiData != ""){
                val parsedApiData = JSONObject(apiData)
            }
        val views = RemoteViews(context?.packageName, R.layout.calender_widget_small)
        views.setTextViewText(R.id.round, "working")
        appWidgetManager?.updateAppWidget(appWidgetId, views)  
        }
    }
}