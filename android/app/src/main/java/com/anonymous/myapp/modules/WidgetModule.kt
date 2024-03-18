package com.anonymous.myapp.modules

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.util.Log
import com.anonymous.myapp.widgetprovider.CalenderWidgetSmall
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import android.content.ComponentName
import com.anonymous.myapp.sharedpreferences.SharedPreferences

class WidgetModule(private var reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "WidgetModule"
    }

    @ReactMethod
    fun updateSmallCalenderWidget(name: String, location: String, apiData : String) {
        Log.d("CalendarModule", "Create event called with name: $name and location: $location and jsondata: $apiData")
        SharedPreferences.write("championshipData", apiData)
        val intent: Intent = Intent(reactContext, CalenderWidgetSmall::class.java)
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        val ids: IntArray? = AppWidgetManager.getInstance(reactContext).getAppWidgetIds(ComponentName(reactContext, CalenderWidgetSmall::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        reactContext.sendBroadcast(intent)
    }
}
