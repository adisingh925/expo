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


class CalenderWidgetSmall : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        Log.d("CalenderWidgetSmall", "onUpdate")
        appWidgetIds?.forEach { appWidgetId ->
        val views = RemoteViews(context?.packageName, R.layout.calender_widget_small)
        views.setTextViewText(R.id.round, "working")
        appWidgetManager?.updateAppWidget(appWidgetId, views)  
        }
    }
}