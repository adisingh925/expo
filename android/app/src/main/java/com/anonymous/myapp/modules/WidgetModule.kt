package com.anonymous.myapp.modules

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.util.Log
import com.anonymous.myapp.widgetprovider.CalenderWidgetSmall
import com.anonymous.myapp.widgetprovider.TeamWidgetProvider
import com.anonymous.myapp.widgetprovider.DriverWidgetProvider
import com.anonymous.myapp.widgetprovider.CalenderWidgetLarge
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import android.content.ComponentName
import com.anonymous.myapp.sharedpreferences.SharedPreferences

class WidgetModule(private var reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    init {
        SharedPreferences.init(reactContext)
    }

    override fun getName(): String {
        return "WidgetModule"
    }

    @ReactMethod
    fun storeApiData(apiData: String) {
        SharedPreferences.write("championshipData", apiData)
        updateSmallCalenderWidgets()
        updateLargeCalenderWidgets()
    }

    @ReactMethod
    fun updateSmallCalenderWidgets() {
        val intent: Intent = Intent(reactContext, CalenderWidgetSmall::class.java)
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        val ids: IntArray? = AppWidgetManager.getInstance(reactContext).getAppWidgetIds(ComponentName(reactContext, CalenderWidgetSmall::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        reactContext.sendBroadcast(intent)
    }

    @ReactMethod
    fun updateLargeCalenderWidgets() {
        val intent: Intent = Intent(reactContext, CalenderWidgetLarge::class.java)
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        val ids: IntArray? = AppWidgetManager.getInstance(reactContext).getAppWidgetIds(ComponentName(reactContext, CalenderWidgetLarge::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        reactContext.sendBroadcast(intent)
    }

    @ReactMethod
    fun storeDriverWidgetUUIDAndApiKey(uuid: String, apiKey: String) {
        SharedPreferences.write("driver_uuid", uuid)
        SharedPreferences.write("driver_apiKey", apiKey)
        updateDriverWidget()
    }

    @ReactMethod
    fun storeTeamWidgetUUIDAndApiKey(uuid: String, apiKey: String) {
        SharedPreferences.write("team_uuid", uuid)
        SharedPreferences.write("team_apiKey", apiKey)
        updateTeamWidget()
    }

    @ReactMethod
    fun updateDriverWidget() {
        val intent: Intent = Intent(reactContext, DriverWidgetProvider::class.java)
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        val ids: IntArray? = AppWidgetManager.getInstance(reactContext).getAppWidgetIds(ComponentName(reactContext, DriverWidgetProvider::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        reactContext.sendBroadcast(intent)
    }

    @ReactMethod
    fun updateTeamWidget() {
        val intent: Intent = Intent(reactContext, TeamWidgetProvider::class.java)
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        val ids: IntArray? = AppWidgetManager.getInstance(reactContext).getAppWidgetIds(ComponentName(reactContext, TeamWidgetProvider::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        reactContext.sendBroadcast(intent)
    }
}
