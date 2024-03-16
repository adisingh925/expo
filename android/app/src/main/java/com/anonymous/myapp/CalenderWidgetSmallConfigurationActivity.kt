package com.anonymous.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.Window
import android.appwidget.AppWidgetManager
import android.widget.RemoteViews
import android.content.Intent
import android.app.Activity
import android.widget.TextView


class CalenderWidgetSmallConfigurationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val window = getWindow()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.setDimAmount(0f) // Disable dimming effect

        // Hide title
        // Hide title
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_main)

        val formula1 = findViewById<TextView>(R.id.formula1)
        val motogp = findViewById<TextView>(R.id.motogp)
        val wec = findViewById<TextView>(R.id.wec)
        val formula2 = findViewById<TextView>(R.id.formula2)
        val formulae = findViewById<TextView>(R.id.formulae)
        val f1academy = findViewById<TextView>(R.id.f1academy)

        val appWidgetId = intent?.extras?.getInt(
        AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        val appWidgetManager = AppWidgetManager.getInstance(this)
        val views = RemoteViews(this.packageName, R.layout.calender_widget_small)

        formula1.setOnClickListener {
            views.setTextViewText(R.id.round, "formula 1")
            appWidgetManager.updateAppWidget(appWidgetId, views)   

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
        }

        motogp.setOnClickListener {
            views.setTextViewText(R.id.round, "moto gp")
            appWidgetManager.updateAppWidget(appWidgetId, views)   

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
        }

        wec.setOnClickListener {
            views.setTextViewText(R.id.round, "wec")
            appWidgetManager.updateAppWidget(appWidgetId, views)   

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
        }

        formula2.setOnClickListener {
            views.setTextViewText(R.id.round, "formula 2")
            appWidgetManager.updateAppWidget(appWidgetId, views)   

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
        }

        formulae.setOnClickListener {
            views.setTextViewText(R.id.round, "formula e")
            appWidgetManager.updateAppWidget(appWidgetId, views)   

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
        }

        f1academy.setOnClickListener {
            views.setTextViewText(R.id.round, "f1 academy")
            appWidgetManager.updateAppWidget(appWidgetId, views)   

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
        }
    }
}
